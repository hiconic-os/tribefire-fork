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
package com.braintribe.devrock.mc.core.repository.resolving;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.braintribe.devrock.mc.api.commons.VersionInfo;
import com.braintribe.devrock.mc.core.commons.utils.TestUtils;
import com.braintribe.devrock.mc.core.filters.AllMatchingArtifactFilterExpert;
import com.braintribe.devrock.mc.core.repository.HttpClientFactory;
import com.braintribe.devrock.mc.core.repository.local.BasicArtifactPartResolverPersistenceDelegate;
import com.braintribe.devrock.mc.core.resolver.BasicVersionInfo;
import com.braintribe.devrock.mc.core.resolver.HttpRepositoryArtifactDataResolver;
import com.braintribe.devrock.mc.core.resolver.LocalRepositoryCachingArtifactResolver;
import com.braintribe.devrock.model.repository.MavenHttpRepository;
import com.braintribe.devrock.model.repository.Repository;
import com.braintribe.devrock.repolet.launcher.Launcher;
import com.braintribe.devrock.repolet.launcher.LauncherTrait;
import com.braintribe.model.artifact.compiled.CompiledArtifactIdentification;

public class HttpRemoteResolvingTest extends AbstractLocalRepositoryCachingArtifactResolverTest implements LauncherTrait{
	protected Launcher launcher;
	
	private static final String REPOSITORY_ID = "http";
	private CompiledArtifactIdentification cai = CompiledArtifactIdentification.parse("com.braintribe.devrock.test:artifact#1.0");
	private File resolverRepository = new File( input, "remoteRepo");
			
	{
		launcher = Launcher.build().repolet().name("archive").filesystem().filesystem(resolverRepository).close().close().done();										
	}
	
	private Repository repository;
	{
		repository = MavenHttpRepository.T.create();
		repository.setName(REPOSITORY_ID);
	}
		
	@Before
	public void before() {
		runBefore(launcher);
		TestUtils.ensure(repo);
	}
	
	@After
	public void after() {
		runAfter( launcher);
	}
	
	@Override
	public void log(String message) {
		System.out.println( message);		
	}
	
	@Override
	protected String getRoot() {	
		return "httpRemoteArtifactPartResolving";
	}
				
	@Test
	public void testVersionResolving() {	
		HttpRepositoryArtifactDataResolver dataResolver = new HttpRepositoryArtifactDataResolver();
		dataResolver.setRepositoryId(REPOSITORY_ID);
		dataResolver.setRoot( launcher.getLaunchedRepolets().get("archive"));
		dataResolver.setHttpClient(HttpClientFactory.get());
		
		
		BasicArtifactPartResolverPersistenceDelegate delegate = new BasicArtifactPartResolverPersistenceDelegate();
		delegate.setResolver(dataResolver);
		delegate.setRepository( repository);
		delegate.setArtifactFilter( AllMatchingArtifactFilterExpert.instance);
		LocalRepositoryCachingArtifactResolver cachingResolver = setup( Collections.singletonList( delegate));
		
		List<VersionInfo> expected = new ArrayList<>();
		expected.add( new BasicVersionInfo( cai.getVersion(), Collections.singletonList( REPOSITORY_ID)));
		
		testVersionInfoResolving( cachingResolver, cai, expected);
		
	}
	
	@Test
	public void testPartResolving() {
		HttpRepositoryArtifactDataResolver dataResolver = new HttpRepositoryArtifactDataResolver();
		dataResolver.setRepositoryId(REPOSITORY_ID);
		dataResolver.setRoot( launcher.getLaunchedRepolets().get("archive"));
		dataResolver.setHttpClient(HttpClientFactory.get());
		
		BasicArtifactPartResolverPersistenceDelegate delegate = new BasicArtifactPartResolverPersistenceDelegate();
		delegate.setResolver(dataResolver);
		delegate.setRepository( repository);
		delegate.setArtifactFilter( AllMatchingArtifactFilterExpert.instance);
		
		LocalRepositoryCachingArtifactResolver cachingResolver = setup( Collections.singletonList( delegate));
		testPartResolving(cachingResolver, cai, standardParts);	
	}
	
	
}
