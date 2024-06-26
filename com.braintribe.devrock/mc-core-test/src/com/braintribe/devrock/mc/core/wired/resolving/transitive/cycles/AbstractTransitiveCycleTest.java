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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.cycles;

import java.io.File;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.mc.api.classpath.ClasspathDependencyResolver;
import com.braintribe.devrock.mc.api.classpath.ClasspathResolutionContext;
import com.braintribe.devrock.mc.api.resolver.CompiledArtifactResolver;
import com.braintribe.devrock.mc.api.transitive.TransitiveDependencyResolver;
import com.braintribe.devrock.mc.api.transitive.TransitiveResolutionContext;
import com.braintribe.devrock.mc.core.commons.test.HasCommonFilesystemNode;
import com.braintribe.devrock.mc.core.commons.utils.TestUtils;
import com.braintribe.devrock.mc.core.wirings.classpath.ClasspathResolverWireModule;
import com.braintribe.devrock.mc.core.wirings.classpath.contract.ClasspathResolverContract;
import com.braintribe.devrock.mc.core.wirings.maven.configuration.MavenConfigurationWireModule;
import com.braintribe.devrock.mc.core.wirings.venv.contract.VirtualEnvironmentContract;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.devrock.repolet.generator.RepositoryGenerations;
import com.braintribe.devrock.repolet.launcher.Launcher;
import com.braintribe.devrock.repolet.launcher.LauncherTrait;
import com.braintribe.exception.Exceptions;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.analysis.ClashResolvingStrategy;
import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.compiled.CompiledDependencyIdentification;
import com.braintribe.model.artifact.compiled.CompiledTerminal;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.ve.impl.OverridingEnvironment;
import com.braintribe.ve.impl.StandardEnvironment;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;

@Category(KnownIssue.class)
public abstract class AbstractTransitiveCycleTest implements LauncherTrait, HasCommonFilesystemNode {
	protected File repo;
	protected File input;
	protected File output;
	
	{	
		Pair<File,File> pair = filesystemRoots("wired/transitive/cycles");
		input = pair.first;
		output = pair.second;
		repo = new File( output, "repo");			
	}
	
	private File settings = new File( input, "settings.xml");
	
	protected String terminal = "com.braintribe.devrock.test:t#1.0.1";
	
	protected WireContext<ClasspathResolverContract> resolverContext;
	
	protected TransitiveResolutionContext standardTransitiveResolutionContext = TransitiveResolutionContext.build().done();
	protected ClasspathResolutionContext standardClasspathResolutionContext = ClasspathResolutionContext.build().clashResolvingStrategy(ClashResolvingStrategy.highestVersion).enrichJavadoc(true).enrichSources(true).done();
			
	protected abstract File archiveInput();
	
	protected RepoletContent loadInput(File file) {		
		try {
			if (file.getName().endsWith(".yaml")) {
				return RepositoryGenerations.unmarshallConfigurationFile(file);
			}
			else {
				return RepositoryGenerations.parseConfigurationFile( file);
			}
		} catch (Exception e) {
			throw Exceptions.unchecked(e, "cannot parse file [" + file.getAbsolutePath() + "]", IllegalStateException::new);
		} 
	}	
	
	private Launcher launcher; 
	{
		launcher = Launcher.build()
				.repolet()
				.name("archive")			
					.descriptiveContent()
						.descriptiveContent( loadInput( archiveInput()))
					.close()
				.close()
			.done();
	}

	@Before
	public void runBefore() {
		TestUtils.ensure(repo); 			
		launcher.launch();
		
		resolverContext = Wire.contextBuilder( ClasspathResolverWireModule.INSTANCE, MavenConfigurationWireModule.INSTANCE)
				.bindContract(VirtualEnvironmentContract.class, () -> buildVirtualEnvironement(null))				
				.build();
	}
	
	@After
	public void runAfter() {
		launcher.shutdown();
	}
	
	protected OverridingEnvironment buildVirtualEnvironement(Map<String,String> overrides) {
		OverridingEnvironment ove = new OverridingEnvironment(StandardEnvironment.INSTANCE);
		if (overrides != null && !overrides.isEmpty()) {
			ove.setEnvs(overrides);						
		}
		ove.setEnv("M2_REPO", repo.getAbsolutePath());
		ove.setEnv("ARTIFACT_REPOSITORIES_EXCLUSIVE_SETTINGS", settings.getAbsolutePath());
		ove.setEnv( "port", Integer.toString( launcher.getAssignedPort()));
				
		return ove;		
	}

	public Pair<AnalysisArtifactResolution, Long> resolveAsDependency(String terminal, TransitiveResolutionContext resolutionContext) {
		return run(CompiledTerminal.parse(terminal), resolutionContext);
	}
	
	protected Pair<AnalysisArtifactResolution,Long> run(CompiledTerminal terminal, TransitiveResolutionContext resolutionContext) {
		try  {			
			TransitiveDependencyResolver transitiveDependencyResolver = resolverContext.contract().transitiveResolverContract().transitiveDependencyResolver();
			
			long before = System.nanoTime();
			AnalysisArtifactResolution artifactResolution = transitiveDependencyResolver.resolve( resolutionContext, terminal);
			long after = System.nanoTime();
			return Pair.of(artifactResolution, after - before);			
											
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		return null;
	}
	
	public Pair<AnalysisArtifactResolution, Long> resolveAsArtifact(String terminal, TransitiveResolutionContext resolutionContext) {
		try {			
			TransitiveDependencyResolver transitiveDependencyResolver = resolverContext.contract().transitiveResolverContract().transitiveDependencyResolver();			
			CompiledArtifactResolver artifactResolver = resolverContext.contract().transitiveResolverContract().dataResolverContract().directCompiledArtifactResolver();
			Maybe<CompiledArtifact> caOptional = artifactResolver.resolve( CompiledArtifactIdentification.parse( terminal));
			
			if (caOptional.isUnsatisfied()) {
				Assert.fail("no artifact found for [" + terminal + "]");
				return null;
			}
					
			CompiledTerminal cdi = caOptional.get();
			long before = System.nanoTime();
			AnalysisArtifactResolution artifactResolution = transitiveDependencyResolver.resolve( resolutionContext, cdi);
			long after = System.nanoTime();
			return Pair.of(artifactResolution, after - before);					
								
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		return null;
	}
	
	/**
	 * runs a CRP resolving with a dependency-based terminal 
	 * @param terminal - the condensed name of the dependency
	 * @param resolutionContext - the {@link ClasspathResolutionContext}
	 * @return
	 */
	public Pair<AnalysisArtifactResolution, Long> resolve(String terminal, ClasspathResolutionContext resolutionContext) {
		try {
			ClasspathDependencyResolver classpathResolver = resolverContext.contract().classpathResolver();
			
			CompiledTerminal cdi = CompiledTerminal.from ( CompiledDependencyIdentification.parse( terminal));
			long before = System.nanoTime();
			AnalysisArtifactResolution artifactResolution = classpathResolver.resolve( resolutionContext, cdi);
			long after = System.nanoTime();
			return Pair.of(artifactResolution, after - before);					
								
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		return null;
	}
	
	public Pair<AnalysisArtifactResolution, Long> resolveAsArtifact(String terminal, ClasspathResolutionContext resolutionContext) {
		try {
			ClasspathDependencyResolver classpathResolver = resolverContext.contract().classpathResolver();
			
			CompiledArtifactResolver artifactResolver = resolverContext.contract().transitiveResolverContract().dataResolverContract().directCompiledArtifactResolver();
			Maybe<CompiledArtifact> caOptional = artifactResolver.resolve( CompiledArtifactIdentification.parse( terminal));
			
			if (caOptional.isUnsatisfied()) {
				Assert.fail("no artifact found for [" + terminal + "]");
				return null;
			}
					
			CompiledTerminal cdi = caOptional.get();
			long before = System.nanoTime();
			AnalysisArtifactResolution artifactResolution = classpathResolver.resolve( resolutionContext, cdi);
			long after = System.nanoTime();
			return Pair.of(artifactResolution, after - before);					
								
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		return null;
	}
	
	
}
