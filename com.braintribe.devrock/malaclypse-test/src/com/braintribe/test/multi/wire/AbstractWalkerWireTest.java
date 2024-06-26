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
package com.braintribe.test.multi.wire;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.braintribe.artifacts.test.maven.framework.FakeLocalRepositoryProvider;
import com.braintribe.artifacts.test.maven.framework.FakeMavenSettingsPersistenceExpertImpl;
import com.braintribe.build.artifact.representations.artifact.maven.settings.OverrideableVirtualEnvironment;
import com.braintribe.build.artifacts.mc.wire.classwalk.ClasspathResolvers;
import com.braintribe.build.artifacts.mc.wire.classwalk.contract.ClasspathResolverContract;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.ClasspathResolverExternalContract;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.ConfigurableClasspathResolverExternalSpace;
import com.braintribe.build.artifacts.mc.wire.classwalk.external.contract.Scopes;
import com.braintribe.logging.Logger;
import com.braintribe.logging.LoggerInitializer;
import com.braintribe.model.malaclypse.cfg.denotations.clash.ResolvingInstant;
import com.braintribe.wire.api.context.WireContext;

/**
 * abstract test class for the wired CP walker.. 
 *  
 * @author pit
 *
 */
public abstract class AbstractWalkerWireTest {
	private static Logger log = Logger.getLogger(AbstractWalkerWireTest.class);

	
	protected WireContext<ClasspathResolverContract> getClasspathWalkContext(File settings, File localRepo, Map<String,String> overrides) {
		return getClasspathWalkContext(settings, localRepo, ResolvingInstant.adhoc, overrides);
		
	}
	
	protected WireContext<ClasspathResolverContract> getClasspathWalkContext(File settings, File localRepo, ResolvingInstant resolvingInstant) {
		return getClasspathWalkContext(settings, localRepo, resolvingInstant, new HashMap<>());
	}
	/**
	 * generate a {@link WireContext} to allow retrieving walkers 	
	 * @param settings - the settings file to use 
	 * @param localRepo - the local repository to use 
	 * @param resolvingInstant - when to resolve clashes 
	 * @return - a {@link WireContext} of the {@link ClasspathResolverContract}
	 */
	protected WireContext<ClasspathResolverContract> getClasspathWalkContext(File settings, File localRepo, ResolvingInstant resolvingInstant, Map<String,String> overrides) {
		ConfigurableClasspathResolverExternalSpace cfg = new ConfigurableClasspathResolverExternalSpace();
		
		cfg.setScopes( Scopes.compileScopes());
		cfg.setSkipOptional(false);
		
		cfg.setResolvingInstant( ResolvingInstant.adhoc);
		
		OverrideableVirtualEnvironment ove = new OverrideableVirtualEnvironment();
		ove.setEnvironmentOverrides(overrides);
		
		cfg.setVirtualEnvironment(ove);
		
		if (settings != null) {		
			FakeMavenSettingsPersistenceExpertImpl persistenceExpert = new FakeMavenSettingsPersistenceExpertImpl( settings);
			cfg.setOverrideSettingsPersistenceExpert(persistenceExpert);
		}
		
		if (localRepo != null) {
			FakeLocalRepositoryProvider localRepositoryProvider = new FakeLocalRepositoryProvider(localRepo);
			cfg.setOverrideLocalRepositoryExpert(localRepositoryProvider);
		}
		
		
		WireContext<ClasspathResolverContract> context = ClasspathResolvers.classpathResolverContext( b -> {  
			b.bindContract(ClasspathResolverExternalContract.class, cfg);	
		});
		
		return context;		
	}
	
	protected void loggingInitialize(File file) {
		LoggerInitializer loggerInitializer = new LoggerInitializer();
		try {								
			if (file.exists()) {
				loggerInitializer.setLoggerConfigUrl( file.toURI().toURL());		
				loggerInitializer.afterPropertiesSet();
			}
		} catch (Exception e) {		
			String msg = "cannot initialize logging";
			log.error(msg, e);
		}
	
	}
	
	
}
