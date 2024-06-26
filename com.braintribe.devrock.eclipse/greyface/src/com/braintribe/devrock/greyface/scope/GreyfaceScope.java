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
package com.braintribe.devrock.greyface.scope;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.build.artifact.representations.artifact.maven.settings.MavenSettingsExpertFactory;
import com.braintribe.build.artifact.representations.artifact.maven.settings.MavenSettingsReader;
import com.braintribe.build.artifact.representations.artifact.pom.ArtifactPomReader;
import com.braintribe.build.artifact.representations.artifact.pom.PomExpertFactory;
import com.braintribe.build.artifact.retrieval.multi.cache.CacheFactoryImpl;
import com.braintribe.build.artifact.retrieval.multi.resolving.DependencyResolver;
import com.braintribe.build.artifact.retrieval.multi.resolving.DependencyResolverFactory;
import com.braintribe.devrock.greyface.GreyfacePlugin;
import com.braintribe.devrock.greyface.process.retrieval.AbstractDependencyResolver;
import com.braintribe.devrock.greyface.process.retrieval.CompoundDependencyResolver;
import com.braintribe.model.artifact.Part;
import com.braintribe.model.artifact.meta.MavenMetaData;
import com.braintribe.model.malaclypse.cfg.preferences.gf.GreyFacePreferences;
import com.braintribe.model.malaclypse.cfg.preferences.gf.RepositorySetting;
import com.braintribe.plugin.commons.properties.PropertyResolver;


public class GreyfaceScope implements DependencyResolverFactory {
	private static GreyfaceScope instance;
	private PomExpertFactory pomExpertFactory;	
	private MavenSettingsExpertFactory mavenSettingsFactory;
	private AbstractDependencyResolver resolver;
	private Map<String, MavenMetaData> remoteDirectoryToMetadataMap;
	
	public static GreyfaceScope getScope() {
		if (instance == null) {
			instance = new GreyfaceScope();
		}
		return instance;
	}	
	
	public GreyfaceScope() {
		instance = this;
		remoteDirectoryToMetadataMap = new HashMap<String, MavenMetaData>();
	}
	
	public void resetScope( AbstractDependencyResolver resolver){
		this.resolver = resolver;
		if (pomExpertFactory != null) {		
			pomExpertFactory.forceNewPomReaderInstance();
		}
		//remoteDirectoryToMetadataMap = new HashMap<String, MavenMetaData>();
	}
	
	public MavenMetaData getMavenMetaDataForRemoteRepositoryDirectory(String repo) {
		return remoteDirectoryToMetadataMap.get(repo);
	}
	
	public void addMavenMetaDataForRemoteRepositoryDirectory( String repo, MavenMetaData metaData) {
		remoteDirectoryToMetadataMap.put( repo, metaData);
	}
	
	public RepositorySetting getSourceForPart( Part part) {
		return ((CompoundDependencyResolver) resolver).getSourceForPart(part);
	}
	
	public ArtifactPomReader getPomReader() {

		if (pomExpertFactory !=  null) {
			return pomExpertFactory.getReader();
		}
		pomExpertFactory = new PomExpertFactory();
		pomExpertFactory.setDetectParentLoops(false);
		pomExpertFactory.setCacheFactory( new CacheFactoryImpl());
		pomExpertFactory.setDependencyResolverFactory( this);
		pomExpertFactory.setEnforceParentResolving(true);
		pomExpertFactory.setSettingsReader(getSettingsReader());
		// validate pom ? 
		GreyFacePreferences gfPreferences = GreyfacePlugin.getInstance().getGreyfacePreferences(false);
		//pomExpertFactory.setValidating( gfPreferences.getValidatePoms());
		
		return pomExpertFactory.getReader();			
	}
	
	public MavenSettingsExpertFactory getSettingsFactory() {
		if (mavenSettingsFactory != null)
			return mavenSettingsFactory;
		
		mavenSettingsFactory = new MavenSettingsExpertFactory();
		OverrideMavenSettingsPersistenceExpert persistenceExpert = new OverrideMavenSettingsPersistenceExpert();
		OverrideMavenProfileActivationExpert profileExpert = new OverrideMavenProfileActivationExpert();
		
		PropertyResolver propertyResolver = new PropertyResolver();
		persistenceExpert.setPropertyResolver(propertyResolver);
		profileExpert.setVirtualPropertyResolver(propertyResolver);
		mavenSettingsFactory.setSettingsPeristenceExpert(persistenceExpert);
		mavenSettingsFactory.setMavenProfileActivationExpert(profileExpert);
		return mavenSettingsFactory;
	}


	public MavenSettingsReader getSettingsReader() {
		mavenSettingsFactory = null;
		return getSettingsFactory().getMavenSettingsReader();
	}


	@Override
	public DependencyResolver get() throws RuntimeException {
		return resolver;
	}

	
	
}
