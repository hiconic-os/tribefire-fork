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
package tribefire.extension.gcp.templates.api;

import java.util.function.Function;

import com.braintribe.model.deployment.Cartridge;
import com.braintribe.model.deployment.Module;
import com.braintribe.model.descriptive.HasExternalId;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.utils.StringTools;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.scope.InstanceQualification;

public class GcpBinaryProcessTemplateContextImpl implements GcpBinaryProcessTemplateContext, GcpBinaryProcessTemplateContextBuilder {

	private String idPrefix;
	private String jsonCredentials;
	private String privateKeyId;
	private String privateKey;
	private String clientId;
	private String clientEmail;
	private String tokenServerUri;
	private String projectId;
	private String bucketName;
	private String pathPrefix;
	private Cartridge gcpCartridge;

	private Function<EntityType<?>, GenericEntity> entityFactory = EntityType::create;
	private Function<String, ? extends GenericEntity> lookupFunction;
	private Module gcpModule;
	
	@Override
	public String getIdPrefix() {
		return idPrefix;
	}

	@Override
	public GcpBinaryProcessTemplateContextBuilder setIdPrefix(String idPrefix) {
		this.idPrefix = idPrefix;
		return this;
	}

	@Override
	public String getJsonCredentials() {
		return jsonCredentials;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setJsonCredentials(String jsonCredentials) {
		this.jsonCredentials = jsonCredentials;
		return this;
	}

	@Override
	public String getPrivateKeyId() {
		return privateKeyId;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setPrivateKeyId(String privateKeyId) {
		this.privateKeyId = privateKeyId;
		return this;
	}

	@Override
	public String getPrivateKey() {
		return privateKey;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		return this;
	}

	@Override
	public String getClientId() {
		return clientId;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setClientId(String clientId) {
		this.clientId = clientId;
		return this;
	}

	@Override
	public String getClientEmail() {
		return clientEmail;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
		return this;
	}

	@Override
	public String getTokenServerUri() {
		return tokenServerUri;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setTokenServerUri(String tokenServerUri) {
		this.tokenServerUri = tokenServerUri;
		return this;
	}

	@Override
	public String getProjectId() {
		return projectId;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setProjectId(String projectId) {
		this.projectId = projectId;
		return this;
	}

	@Override
	public String getBucketName() {
		return bucketName;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setBucketName(String bucketName) {
		this.bucketName = bucketName;
		return this;
	}

	@Override
	public String getPathPrefix() {
		return pathPrefix;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setPathPrefix(String pathPrefix) {
		this.pathPrefix = pathPrefix;
		return this;
	}
	
	@Override
	public GcpBinaryProcessTemplateContextBuilder setGcpModule(Module gcpModule) {
		this.gcpModule = gcpModule;
		return this;
	}

	@Override
	public Module getGcpModule() {
		return this.gcpModule;
	}


	@Override
	public int hashCode() {
		return idPrefix.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GcpBinaryProcessTemplateContext) {
			return ((GcpBinaryProcessTemplateContext) obj).getIdPrefix().equals(this.idPrefix);
		}
		return super.equals(obj);
	}

	@Override
	public GcpBinaryProcessTemplateContextBuilder setEntityFactory(Function<EntityType<?>, GenericEntity> entityFactory) {
		this.entityFactory = entityFactory;
		return this;
	}


	@Override
	public <T extends GenericEntity> T create(EntityType<T> entityType, InstanceConfiguration instanceConfiguration) {

		T entity = (T) entityFactory.apply(entityType);

		if (idPrefix == null) {
			throw new IllegalStateException("You have to specify a idPrefix.");
		}
		
		InstanceQualification qualification = instanceConfiguration.qualification();

		String globalId = "wire://" + idPrefix + "/" + qualification.space().getClass().getSimpleName() + "/" + qualification.name();

		entity.setGlobalId(globalId);

		if (entity instanceof HasExternalId) {
			HasExternalId eid = (HasExternalId) entity;

			String externalId = 
					StringTools.camelCaseToDashSeparated(entityType.getShortName()) + "." +
					StringTools.camelCaseToDashSeparated(idPrefix) + "." + 
					StringTools.camelCaseToDashSeparated(qualification.space().getClass().getSimpleName()) + "." + 
					StringTools.camelCaseToDashSeparated(qualification.name()); 

			eid.setExternalId(externalId);
		}
		
		return entity;
	}

	@Override
	public Cartridge getGcpCartridge() {
		return gcpCartridge;
	}
	@Override
	public GcpBinaryProcessTemplateContextBuilder setGcpCartridge(Cartridge gcpCartridge) {
		this.gcpCartridge = gcpCartridge;
		return this;
	}

	@Override
	public GcpBinaryProcessTemplateContext build() {
		return this;
	}

	@Override
	public GcpBinaryProcessTemplateContextBuilder setLookupFunction(Function<String, ? extends GenericEntity> lookupFunction) {
		this.lookupFunction = lookupFunction;
		return this;
	}

	@Override
	public <T extends GenericEntity> T lookup(String globalId) {
		return (T) lookupFunction.apply(globalId);
	}

}
