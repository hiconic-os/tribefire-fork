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
package com.braintribe.artifacts.codebase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.braintribe.build.artifact.codebase.reflection.CodebaseReflection;
import com.braintribe.build.artifact.codebase.reflection.TemplateBasedCodebaseReflection;
import com.braintribe.build.artifact.retrieval.multi.resolving.AbstractDependencyResolverFactoryImpl;
import com.braintribe.build.artifact.retrieval.multi.resolving.CodebaseAwareDependencyResolver;
import com.braintribe.build.artifact.retrieval.multi.resolving.DependencyResolver;
import com.braintribe.build.artifact.retrieval.multi.resolving.MultiRepositoryDependencyResolverImpl;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.panther.SourceRepository;
import com.braintribe.test.multi.realRepoWalk.Monitor;

public class CodebaseAwareDependencyResolverFactoryImpl extends AbstractDependencyResolverFactoryImpl{
	
	private Monitor monitor;
	private DependencyResolver resolver;
	private SourceRepository sourceRepository;
	private String templateStr; //= "${groupId}/${version}/${artifactId}";
	
	public CodebaseAwareDependencyResolverFactoryImpl( Monitor monitor) {
	
		this.monitor = monitor;
	}
	
	@Configurable @Required
	public void setSourceRepository(SourceRepository sourceRepository) {
		this.sourceRepository = sourceRepository;
	}
	
	@Configurable @Required
	public void setCodebaseTemplate( String template) {
		templateStr = template;
	}
	
	@Override
	public DependencyResolver get() throws RuntimeException {
		if (resolver != null)
			return resolver;

		MultiRepositoryDependencyResolverImpl remoteResolver = new MultiRepositoryDependencyResolverImpl();						
		remoteResolver.setRepositoryRegistry(repositoryRegistry);
		remoteResolver.setPomExpertFactory(pomExpertFactory);
		remoteResolver.addListener( monitor);
			
		String repoUrlAsString = sourceRepository.getRepoUrl();
		URL repoUrl;
		try {
			repoUrl = new URL( repoUrlAsString);
		} catch (MalformedURLException e) {
			throw new RuntimeException( e);
		}
		File repoRoot = new File ( repoUrl.getFile());
		
		if (templateStr == null) {
			throw new RuntimeException("no template passed");
		}
		
		CodebaseReflection codebaseReflection = new TemplateBasedCodebaseReflection( repoRoot, templateStr);
	 
		CodebaseAwareDependencyResolver wcResolver = new CodebaseAwareDependencyResolver();
		wcResolver.setCodebaseReflection(codebaseReflection);
		wcResolver.setPomExpertFactory(pomExpertFactory);
		
		wcResolver.setDelegate(remoteResolver);
			 
		resolver = wcResolver;
		resolver.addListener( monitor);
			
		return resolver;
	}


}
