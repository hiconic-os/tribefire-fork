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
package com.braintribe.devrock.mc.core.wired.resolving.transitive.pom;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.mc.api.resolver.CompiledArtifactResolver;
import com.braintribe.devrock.mc.api.transitive.TransitiveResolutionContext;
import com.braintribe.devrock.mc.core.commons.test.HasCommonFilesystemNode;
import com.braintribe.devrock.mc.core.commons.utils.TestUtils;
import com.braintribe.devrock.mc.core.wirings.maven.configuration.MavenConfigurationWireModule;
import com.braintribe.devrock.mc.core.wirings.transitive.TransitiveResolverWireModule;
import com.braintribe.devrock.mc.core.wirings.transitive.contract.TransitiveResolverContract;
import com.braintribe.devrock.mc.core.wirings.venv.contract.VirtualEnvironmentContract;
import com.braintribe.devrock.model.repolet.content.RepoletContent;
import com.braintribe.devrock.repolet.launcher.Launcher;
import com.braintribe.devrock.repolet.launcher.LauncherTrait;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.artifact.analysis.AnalysisArtifactResolution;
import com.braintribe.model.artifact.compiled.CompiledArtifact;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;
import com.braintribe.model.artifact.compiled.CompiledDependencyIdentification;
import com.braintribe.model.artifact.compiled.CompiledTerminal;
import com.braintribe.ve.impl.OverridingEnvironment;
import com.braintribe.ve.impl.StandardEnvironment;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;


/**
 * several pom compiler tests in the context of the {@link TransitiveResolverContract}
 * @author pit
 *
 */
public abstract class AbstractTransitiveResolverPomCompilingTest implements LauncherTrait, HasCommonFilesystemNode {
	protected File repo;
	protected File input;
	protected File output;
	
	{	
		Pair<File,File> pair = filesystemRoots("wired/transitive/pom/resolved");
		input = pair.first;
		output = pair.second;
		repo = new File( output, "repo");			
	}
	
	protected File settings = new File(input, "settings.xml");
	protected File initial = new File( input, "initial");
	
	protected abstract RepoletContent archiveInput( String key);
	
	private Launcher launcher; 
	{
		launcher = Launcher.build()
				.repolet()
				.name("archive")
					.descriptiveContent()
						.descriptiveContent(archiveInput("archive"))
					.close()
				.close()
				.done();
	}
	
	private TransitiveResolutionContext lenientContext = TransitiveResolutionContext.build().lenient(true).done();
	
	protected abstract void runAdditionalBeforeSteps();


	
	@Before
	public void runBefore() {
		TestUtils.ensure(repo); 			
		launcher.launch();
	
		runAdditionalBeforeSteps();			
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
		ove.setEnv("repo", repo.getAbsolutePath());
		ove.setEnv("ARTIFACT_REPOSITORIES_EXCLUSIVE_SETTINGS", settings.getAbsolutePath());
		ove.setEnv( "port", Integer.toString( launcher.getAssignedPort()));
				
		return ove;		
	}

	protected CompiledArtifact runResolve(String terminal) {
		try (				
				WireContext<TransitiveResolverContract> resolverContext = Wire.contextBuilder( TransitiveResolverWireModule.INSTANCE, MavenConfigurationWireModule.INSTANCE)
					.bindContract(VirtualEnvironmentContract.class, () -> buildVirtualEnvironement(null))				
					.build();
			) {
						
			CompiledArtifactResolver car = resolverContext.contract().dataResolverContract().redirectAwareCompiledArtifactResolver();
			
			CompiledArtifactIdentification cai = CompiledArtifactIdentification.parse(terminal);
			Maybe<CompiledArtifact> maybe = car.resolve(cai);
			
			if (maybe.isSatisfied())
				return maybe.get();
			else
				return null;
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		return null;
	}
	
	protected TransitiveResolutionContext standardTransitiveResolutionContext = TransitiveResolutionContext.build().lenient( true).done();
	
	protected AnalysisArtifactResolution runAnalysis(String terminal) {
		try (				
				WireContext<TransitiveResolverContract> resolverContext = Wire.contextBuilder( TransitiveResolverWireModule.INSTANCE, MavenConfigurationWireModule.INSTANCE)
					.bindContract(VirtualEnvironmentContract.class, () -> buildVirtualEnvironement(null))				
					.build();
			) {
							
			CompiledDependencyIdentification cdi = CompiledDependencyIdentification.parse(terminal);
			CompiledTerminal ct = CompiledTerminal.from( cdi);
			return resolverContext.contract().transitiveDependencyResolver().resolve(lenientContext, ct);																				
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		return null;
	}
		
	protected String collate(List<String> cds) {
		return cds.stream().collect(Collectors.joining(","));
	}
	
	
}

