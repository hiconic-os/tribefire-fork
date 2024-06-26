// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.devrock.ac.container.repository;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.IStatus;

import com.braintribe.devrock.api.concurrent.CustomThreadFactory;
import com.braintribe.devrock.bridge.eclipse.api.McBridge;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionScope;
import com.braintribe.devrock.mc.api.commons.PartIdentifications;
import com.braintribe.devrock.mc.api.repository.configuration.RepositoryReflection;
import com.braintribe.devrock.model.mc.reason.configuration.RepositoryConfigurationUnspecified;
import com.braintribe.devrock.model.repository.MavenFileSystemRepository;
import com.braintribe.devrock.model.repository.Repository;
import com.braintribe.devrock.model.repository.RepositoryConfiguration;
import com.braintribe.devrock.model.repository.WorkspaceRepository;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.devrock.plugin.DevrockPluginStatus;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.analysis.AnalysisTerminal;
import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.consumable.Part;
import com.braintribe.model.generic.reflection.StandardCloningContext;
import com.braintribe.model.resource.FileResource;
import com.braintribe.utils.lcd.LazyInitialized;

/**
 * a parallel scanner that scans poms and resolves their classpath
 * @author pit
 *
 */
public class FileRepositoryResolvingScanner {
	
	public static Map<File,AnalysisArtifactResolution> scanRepository(MavenFileSystemRepository repository) {
		if (!new File(repository.getRootPath()).exists()) {
			return Collections.emptyMap();
		}		
		return new ScanJob().scan( repository);							
	}
	
	
	/**
	 * @author peter/pit
	 *
	 */
	public static class ScanJob {
		
		private LazyInitialized<McBridge> customizedBridge = new LazyInitialized<>(this::getCustomizedBridge);

		// Creating a new thread-pool has almost no overhead in comparison to scanning lots of folders
		// Our tasks are extremely lightweight, so Executors.newCachedThreadPool() would impair performance due to excessive number of threads
		private final ExecutorService executorService = Executors.newFixedThreadPool(//
				Runtime.getRuntime().availableProcessors(), //
				CustomThreadFactory.create().namePrefix("file-repository-scanner"));

		private final AtomicInteger submittedJobs = new AtomicInteger(0);
		private final CountDownLatch cdl = new CountDownLatch(1);
		private final Map<File, AnalysisArtifactResolution> pomAndResolutions = new ConcurrentHashMap<>();

		public Map<File,AnalysisArtifactResolution> scan(MavenFileSystemRepository repository) {
			Map<File, AnalysisArtifactResolution> resolutions = findAllPomsIn(repository);
			return resolutions;						
		}		
		

		private Map<File,AnalysisArtifactResolution> findAllPomsIn(MavenFileSystemRepository repository) {
			File dir = new File( repository.getRootPath());
			findAllPomsInParallel(repository.getName(), dir);

			if (submittedJobs.get() > 0)
				await();

			executorService.shutdown();

			return pomAndResolutions;
		}

		private void await() {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
		

		/**
		 * @param repoId
		 * @param dir
		 */
		private void findAllPomsInParallel(String repoId, File dir) {
			// don't know how the pom's named, so find any poms here.. 
			File[] files = dir.listFiles(new FilenameFilter() {				
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith( ".pom"))
						return true;
					return false;
				}
			});
			
			if (files != null && files.length > 0) {
				File pom = files[0];			
				if (pom.exists()) {
					// analyse
					AnalysisArtifactResolution resolution = resolve(repoId, pom, ClasspathResolutionScope.compile);  					
					pomAndResolutions.put(pom.getParentFile(), resolution);					
					return;
				}
			}

			submittedJobs.incrementAndGet();
			executorService.submit(() -> {
				for (File subDir : dir.listFiles(File::isDirectory))
					findAllPomsInParallel(repoId, subDir);

				if (submittedJobs.decrementAndGet() == 0)
					cdl.countDown();
			});

		}
			
		
		/**
		 * @param repoId
		 * @param pomFile
		 * @param scope
		 * @return
		 */
		private AnalysisArtifactResolution resolve(String repoId, File pomFile, ClasspathResolutionScope scope) {
			McBridge bridge = customizedBridge.get();
			if (bridge == null) {
				AnalysisArtifactResolution resolution = AnalysisArtifactResolution.T.create();
				resolution.setFailure( Reasons.build(RepositoryConfigurationUnspecified.T).text("cannot build a custom bridge").toReason());
				return resolution;
			}
			Maybe<CompiledArtifact> caiMaybe = bridge.readPomFile( pomFile);
			if (caiMaybe.isUnsatisfied()) {
				AnalysisArtifactResolution resolution = AnalysisArtifactResolution.T.create();
				resolution.setFailure( caiMaybe.whyUnsatisfied());
				return resolution;			
			}
			
			
			CompiledArtifact compiledArtifact = caiMaybe.get();			
			Maybe<AnalysisArtifactResolution> potential = bridge.resolveClasspath( compiledArtifact, scope);
			if (potential.isSatisfied()) {
				AnalysisArtifactResolution resolution = potential.get();
				
				AnalysisTerminal analysisTerminal = resolution.getTerminals().get(0);
				
				Part part = Part.T.create();				
				part.setType( PartIdentifications.pom.asString());
				FileResource resource = FileResource.T.create();
				resource.setName(pomFile.getName());
				resource.setPath(pomFile.getAbsolutePath());
				resource.setFileSize(pomFile.length());
				part.setResource(resource);
				part.setRepositoryOrigin( repoId);
				
				AnalysisArtifact aa = (AnalysisArtifact) analysisTerminal;				
				aa.getParts().put( PartIdentifications.pom.asString(), part);
				
				return resolution;
			}
			else {
				AnalysisArtifactResolution resolution = AnalysisArtifactResolution.T.create();
				resolution.setFailure( potential.whyUnsatisfied());
				return resolution;
			}
		}

		/**
		 * gets the current repository-configuration, removes the workspace repository and parameterizes an {@link McBridge}
		 * @return - a custom {@link McBridge}
		 */
		private McBridge getCustomizedBridge() {
			Maybe<RepositoryReflection> internalRepositoryConfigurationMaybe = DevrockPlugin.mcBridge().reflectRepositoryConfiguration();
			if (internalRepositoryConfigurationMaybe.isSatisfied()) {
				RepositoryConfiguration repositoryConfiguration = internalRepositoryConfigurationMaybe.get().getRepositoryConfiguration();
				RepositoryConfiguration clonedRepositoryConfiguration = repositoryConfiguration.clone(new StandardCloningContext());
				Iterator<Repository> iterator = clonedRepositoryConfiguration.getRepositories().iterator();
				while (iterator.hasNext()) {
					Repository repository = iterator.next();
					if (repository instanceof WorkspaceRepository) {
						iterator.remove();
					}
				}
				return DevrockPlugin.mcBridge().customBridge( clonedRepositoryConfiguration);
			}
			else {
				String msg = "cannot retrieve (and patch) locally active repository configuration as " + internalRepositoryConfigurationMaybe.whyUnsatisfied().stringify();			
				DevrockPluginStatus status = new DevrockPluginStatus(msg, IStatus.ERROR);
				DevrockPlugin.instance().log(status);
				return null;
			}
		}
	}	
}
