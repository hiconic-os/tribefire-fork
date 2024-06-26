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
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public interface GcpBinaryProcessTemplateContextBuilder {

	GcpBinaryProcessTemplateContextBuilder setIdPrefix(String idPrefix);

	GcpBinaryProcessTemplateContextBuilder setJsonCredentials(String jsonCredentials);

	GcpBinaryProcessTemplateContextBuilder setPrivateKeyId(String privateKeyId);

	GcpBinaryProcessTemplateContextBuilder setPrivateKey(String privateKey);

	GcpBinaryProcessTemplateContextBuilder setClientId(String clientId);

	GcpBinaryProcessTemplateContextBuilder setClientEmail(String clientEmail);

	GcpBinaryProcessTemplateContextBuilder setTokenServerUri(String tokenServerUri);

	GcpBinaryProcessTemplateContextBuilder setProjectId(String projectId);

	GcpBinaryProcessTemplateContextBuilder setBucketName(String bucketName);

	GcpBinaryProcessTemplateContextBuilder setPathPrefix(String pathPrefix);

	GcpBinaryProcessTemplateContextBuilder setEntityFactory(Function<EntityType<?>, GenericEntity> entityFactory);

	GcpBinaryProcessTemplateContextBuilder setGcpCartridge(Cartridge gcpCartridge);

	GcpBinaryProcessTemplateContextBuilder setGcpModule(com.braintribe.model.deployment.Module gcpModule);

	GcpBinaryProcessTemplateContextBuilder setLookupFunction(Function<String,? extends GenericEntity> lookupFunction);

	GcpBinaryProcessTemplateContext build();
}