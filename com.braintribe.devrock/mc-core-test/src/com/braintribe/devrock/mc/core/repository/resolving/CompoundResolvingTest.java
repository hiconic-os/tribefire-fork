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
import java.util.Arrays;
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

public class CompoundResolvingTest  extends AbstractLocalRepositoryCachingArtifactResolverTest implements LauncherTrait {	
	protected Launcher launcher;
	
	private static final String REPOSITORY_ID_A = "a";
	private static final String REPOSITORY_ID_B = "b";
	private static final String REPOSITORY_ID_LOCAL = "local";
	
	private File resolverRepositoryA = new File( input, "remoteRepoA");
	private File resolverRepositoryB = new File( input, "remoteRepoB");
	private File preparedInitialRepository = new File( input, "initial");	

	{
		launcher = Launcher.build()
					.repolet()
						.name("archiveA")
						.filesystem()
							.filesystem(resolverRepositoryA)
						.close()
					.close()
					.repolet()
						.name("archiveB")
						.filesystem()
							.filesystem( resolverRepositoryB)
						.close()
					.close()
				.done();

	}
	private List<CompiledArtifactIdentification> cais;
	{
		cais = new ArrayList<>();
		cais.add( CompiledArtifactIdentification.parse("com.braintribe.devrock.test:artifact#1.0"));
		cais.add( CompiledArtifactIdentification.parse("com.braintribe.devrock.test:artifact#2.0"));
		cais.add( CompiledArtifactIdentification.parse("com.braintribe.devrock.test:artifact#3.0"));
	}
	
	private Repository repositoryA;
	private Repository repositoryB;
	private Repository repositoryLocal;
	{
		repositoryA = MavenHttpRepository.T.create();
		repositoryA.setName(REPOSITORY_ID_A);
		
		repositoryB = MavenHttpRepository.T.create();
		repositoryB.setName( REPOSITORY_ID_B);
		
		repositoryLocal = MavenHttpRepository.T.create();
		repositoryLocal.setName(REPOSITORY_ID_LOCAL);
	}
		
	@Before
	public void before() {
		runBefore(launcher);
		TestUtils.ensure(repo);
		TestUtils.copy( preparedInitialRepository, repo);		
	}
	
	@After
	public void after() {
		runAfter(launcher);
	}
	
	
	
	@Override
	public void log(String message) {
		System.out.println( message);		
	}

	@Override
	protected String getRoot() {	
		return "compoundArtifactPartResolving";
	}		
	
	private LocalRepositoryCachingArtifactResolver prepareResolver() {
		HttpRepositoryArtifactDataResolver dataResolverA = new HttpRepositoryArtifactDataResolver();
		dataResolverA.setRepositoryId(REPOSITORY_ID_A);
		dataResolverA.setRoot( launcher.getLaunchedRepolets().get( "archiveA"));
		dataResolverA.setHttpClient(HttpClientFactory.get());
		
		HttpRepositoryArtifactDataResolver dataResolverB = new HttpRepositoryArtifactDataResolver();
		dataResolverB.setRepositoryId(REPOSITORY_ID_B);
		dataResolverB.setRoot( launcher.getLaunchedRepolets().get("archiveB"));
		dataResolverB.setHttpClient(HttpClientFactory.get());
		
		
		BasicArtifactPartResolverPersistenceDelegate delegateA = new BasicArtifactPartResolverPersistenceDelegate();
		delegateA.setResolver(dataResolverA);
		delegateA.setRepository(repositoryA);
		delegateA.setArtifactFilter( AllMatchingArtifactFilterExpert.instance);
		
		BasicArtifactPartResolverPersistenceDelegate delegateB = new BasicArtifactPartResolverPersistenceDelegate();
		delegateB.setResolver(dataResolverB);
		delegateB.setRepository( repositoryB);
		delegateB.setArtifactFilter( AllMatchingArtifactFilterExpert.instance);
		
		LocalRepositoryCachingArtifactResolver cachingResolver = setup( Arrays.asList( delegateA, delegateB));
		return cachingResolver;
	}
	
	
	@Test
	public void testVersionResolving() {	
		LocalRepositoryCachingArtifactResolver cachingResolver = prepareResolver();
		
		List<VersionInfo> expected = new ArrayList<>();
	
		expected.add( new BasicVersionInfo( cais.get(0).getVersion(), Collections.singletonList( REPOSITORY_ID_A)));
		expected.add( new BasicVersionInfo( cais.get(1).getVersion(), Collections.singletonList( REPOSITORY_ID_B)));
		expected.add( new BasicVersionInfo( cais.get(2).getVersion(), Collections.singletonList( REPOSITORY_ID_LOCAL)));
		
		testVersionInfoResolving( cachingResolver, cais.get(0), expected);
		
	}

	@Test
	public void testPartResolving() {
		LocalRepositoryCachingArtifactResolver cachingResolver = prepareResolver();
		testPartResolving(cachingResolver, cais.get(0), standardParts);
		testPartResolving(cachingResolver, cais.get(1), standardParts);
		testPartResolving(cachingResolver, cais.get(2), standardParts);
	}
	
	
}

