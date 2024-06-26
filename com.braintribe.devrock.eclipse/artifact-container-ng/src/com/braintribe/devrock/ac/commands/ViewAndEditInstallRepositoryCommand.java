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
package com.braintribe.devrock.ac.commands;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.braintribe.devrock.ac.container.plugin.ArtifactContainerPlugin;
import com.braintribe.devrock.ac.container.plugin.ArtifactContainerStatus;
import com.braintribe.devrock.ac.container.repository.FileRepositoryCollectingScanner;
import com.braintribe.devrock.ac.container.repository.viewer.InstallRepositoryViewer;
import com.braintribe.devrock.ac.container.updater.WorkspaceUpdater;
import com.braintribe.devrock.mc.api.repository.configuration.RepositoryReflection;
import com.braintribe.devrock.model.repository.MavenFileSystemRepository;
import com.braintribe.devrock.model.repository.Repository;
import com.braintribe.devrock.model.repository.RepositoryConfiguration;
import com.braintribe.devrock.plugin.DevrockPlugin;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.artifact.analysis.AnalysisArtifact;

/**
 * reads the install repository, transposes the contents and calls up a viewer
 * 
 * @author pit
 *
 */
public class ViewAndEditInstallRepositoryCommand extends AbstractHandler {
	

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {	
		
		Repository installRepository;
		long before = System.nanoTime();
		Maybe<RepositoryReflection> repositoryReflectionMaybe = DevrockPlugin.mcBridge().reflectRepositoryConfiguration();
		long after = System.nanoTime();
		double lastProcessingTime = (after - before) / 1E6;	

		if (repositoryReflectionMaybe.isSatisfied()) {
			RepositoryReflection reflection = repositoryReflectionMaybe.get();
			RepositoryConfiguration repositoryConfiguration = reflection.getRepositoryConfiguration();
			installRepository = repositoryConfiguration.getInstallRepository();
			if (installRepository == null) {
				ArtifactContainerStatus status = new ArtifactContainerStatus( "no install repository configured", IStatus.INFO);
				ArtifactContainerPlugin.instance().log(status);
				return null;
			}
			System.out.println("getting rcfg took: " + lastProcessingTime + " ms");
		}
		else {
			Reason reason = repositoryReflectionMaybe.whyUnsatisfied();
			ArtifactContainerStatus status = new ArtifactContainerStatus( "cannot access install repository:", reason);
			ArtifactContainerPlugin.instance().log(status);
			return null;
		}
		
		if (installRepository instanceof MavenFileSystemRepository == false) {
			ArtifactContainerStatus status = new ArtifactContainerStatus( "install repository isn't supported: " + installRepository.getName(), IStatus.INFO);
			ArtifactContainerPlugin.instance().log(status);
			return null;
		}
		MavenFileSystemRepository mavenRepository = (MavenFileSystemRepository) installRepository;

		Shell shell = new Shell(PlatformUI.getWorkbench().getDisplay());

		// enumerate install repository, turn into nodes 
		InstallRepositoryViewer viewer = new InstallRepositoryViewer(shell);
		viewer.setRepository( mavenRepository);
		
		BusyIndicator.showWhile(shell.getDisplay(), () -> {	
			Map<File, AnalysisArtifact> scanResult = FileRepositoryCollectingScanner.scanRepository(mavenRepository, null);		
			Collection<AnalysisArtifact> population = scanResult.values();				
			viewer.setInitialPopulation( population);
			viewer.primeViewer();
		});
		
		
		viewer.open();
		if (viewer.hasPurged()) {					
			WorkspaceUpdater wu = new WorkspaceUpdater();
			wu.runAsJob();
		}
		
		return null;
	}

	
}
