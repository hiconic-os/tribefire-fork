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
package tribefire.extension.azure.templates.api;

import java.util.function.Function;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

public interface AzureTemplateContextBuilder {

	AzureTemplateContextBuilder setIdPrefix(String idPrefix);

	AzureTemplateContextBuilder setName(String name);

	AzureTemplateContextBuilder setStorageConnectionString(String storageConnectionString);

	AzureTemplateContextBuilder setContainerName(String containerName);

	AzureTemplateContextBuilder setPathPrefix(String pathPrefix);

	AzureTemplateContextBuilder setAzureModule(com.braintribe.model.deployment.Module azureModule);

	AzureTemplateContextBuilder setEntityFactory(Function<EntityType<?>, GenericEntity> entityFactory);

	AzureTemplateContextBuilder setLookupFunction(Function<String, ? extends GenericEntity> lookupFunction);

	AzureTemplateContext build();
}