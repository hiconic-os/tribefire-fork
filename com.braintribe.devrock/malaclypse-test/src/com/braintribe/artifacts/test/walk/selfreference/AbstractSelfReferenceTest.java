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
package com.braintribe.artifacts.test.walk.selfreference;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.categories.Category;

import com.braintribe.build.artifact.name.NameParser;
import com.braintribe.build.artifact.representations.artifact.maven.settings.OverrideableVirtualEnvironment;
import com.braintribe.build.artifact.walk.multi.Walker;
import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.BuildDependencyResolutionContract;
import com.braintribe.build.artifacts.mc.wire.classwalk.ClasspathResolvers;
import com.braintribe.build.artifacts.mc.wire.classwalk.context.WalkerContext;
import com.braintribe.build.artifacts.mc.wire.classwalk.contract.ClasspathResolverContract;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.ClasspathResolverExternalContract;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.ConfigurableClasspathResolverExternalSpace;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.Scopes;
import com.braintribe.devrock.repolet.launcher.Launcher;
import com.braintribe.devrock.repolet.launcher.LauncherTrait;
import com.braintribe.filter.test.wire.ArtifactFilteringTestModule;
import com.braintribe.filter.test.wire.FilteringTestConfigurationSpace;
import com.braintribe.model.artifact.Dependency;
import com.braintribe.model.artifact.Solution;
import com.braintribe.model.malaclypse.cfg.denotations.clash.ResolvingInstant;
import com.braintribe.test.framework.TestUtil;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.ve.api.VirtualEnvironment;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;
import com.braintribe.wire.api.util.Lists;
@Category(KnownIssue.class)
public abstract class AbstractSelfReferenceTest implements LauncherTrait {
	protected File content = new File("res/selfReference");
	protected File output = new File( content, "output");
	protected File repo = new File( output, "repo");
	
	protected File input = new File( content, "input");
	protected File settings = new File( input, "settings/settings.xml");
	private String walkScopeId = UUID.randomUUID().toString();
	
	private Launcher launcher; 
	{
		launcher = Launcher.build()
				.repolet()
				.name("archive")
					.filesystem()
						.filesystem( getTestArchiveContent())
					.close()
				.close()
			.done();
	}

	@Before
	public void runBefore() {
		TestUtil.ensure(repo); 			
		launcher.launch();
	}
	
	@After
	public void runAfter() {
		launcher.shutdown();
	}
	
	protected abstract File getTestArchiveContent();
	
	protected OverrideableVirtualEnvironment buildVirtualEnvironement(Map<String,String> overrides) {
		OverrideableVirtualEnvironment ove = new OverrideableVirtualEnvironment();
		if (overrides != null && !overrides.isEmpty()) {
			ove.setEnvironmentOverrides(overrides);						
		}
		ove.addEnvironmentOverride("M2_REPO", repo.getAbsolutePath());
		ove.addEnvironmentOverride("ARTIFACT_REPOSITORIES_EXCLUSIVE_SETTINGS", settings.getAbsolutePath());
		ove.addEnvironmentOverride( "port", Integer.toString( launcher.getAssignedPort()));
				
		return ove;
		
	}
	
	
	protected WireContext<ClasspathResolverContract> getClasspathWalkContext(VirtualEnvironment ove) {
		ConfigurableClasspathResolverExternalSpace cfg = new ConfigurableClasspathResolverExternalSpace();
		
		cfg.setScopes( Scopes.compileScopes());
		cfg.setSkipOptional(true);
		
		cfg.setResolvingInstant( ResolvingInstant.posthoc);
		
		
		cfg.setVirtualEnvironment(ove);
		
		WireContext<ClasspathResolverContract> context = ClasspathResolvers.classpathResolverContext( b -> {  
			b.bindContract(ClasspathResolverExternalContract.class, cfg);	
		});
		
		return context;		
	}

	
	/**
	 * tests the classpath resolver 
	 */	
	public void classpathResolutionTest(String terminal, boolean execeptionExpected) {
		WireContext<ClasspathResolverContract> classpathWalkContext = getClasspathWalkContext( buildVirtualEnvironement(null));
		
		WalkerContext walkerContext = new WalkerContext();		
		Walker walker = classpathWalkContext.contract().walker( walkerContext);		

		Exception exception = null;
		try {
			Collection<Solution> resolvedSolutions = walker.walk(walkScopeId, NameParser.parseCondensedSolutionName(terminal));
			System.out.println( "found [" + resolvedSolutions.size() + "] solutions");
		} catch (Exception e) {
			exception = e; 
			System.out.println("Exception of type [" + e.getClass().getName() + "] thrown, message : [" + e.getMessage() + "]");
		}
		if (execeptionExpected) {
			Assert.assertTrue( "unexpectedly, no exception was thrown:", exception != null);
		}
		else {
			Assert.assertTrue( "unexpectedly, an exception was thrown:", exception == null);
		}
		
	}
	
	
	/**
	 * tests the parallel build resolver
	 */
	
	public void buildPathResolutionTest(String terminal, boolean execeptionExpected) {
		FilteringTestConfigurationSpace testCfgSpace = new FilteringTestConfigurationSpace();
		testCfgSpace.setOverridableVirtualEnvironment( buildVirtualEnvironement(null));		
		testCfgSpace.setLocalRepository( repo);
		
		boolean exceptionThrown = false;
		ArtifactFilteringTestModule module = new ArtifactFilteringTestModule( testCfgSpace);		
		try (
				WireContext<BuildDependencyResolutionContract> wireContext = Wire.context( module)
			){									
			List<Dependency> dependencies = Lists.list( NameParser.parseCondensedDependencyName(terminal));
			Set<Solution> resolvedSolutions = wireContext.contract().buildDependencyResolver().resolve( dependencies);
			System.out.println( "found [" + resolvedSolutions.size() + "] solutions");
		}		
		 catch (Exception e) {
			 System.out.println("Exception of type [" + e.getClass().getName() + "] thrown, message : [" + e.getMessage() + "]");
			exceptionThrown = true;
		}
		Assert.assertTrue( "excepted an exception, but nothing was thrown", execeptionExpected && exceptionThrown == true);
	}
}
