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
package tribefire.extension.azure.model.deployment;

import com.braintribe.model.cache.HasCacheOptions;
import com.braintribe.model.extensiondeployment.BinaryPersistence;
import com.braintribe.model.extensiondeployment.BinaryRetrieval;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface AzureBlobBinaryProcessor extends BinaryRetrieval, BinaryPersistence, HasCacheOptions {

	EntityType<AzureBlobBinaryProcessor> T = EntityTypes.T(AzureBlobBinaryProcessor.class);

	@Name("Storage Connection String")
	@Mandatory
	String getStorageConnectionString();
	void setStorageConnectionString(String storageConnectionString);

	@Name("Container Name")
	@Mandatory
	String getContainerName();
	void setContainerName(String containerName);

	@Name("Path Prefix")
	String getPathPrefix();
	void setPathPrefix(String pathPrefix);

}
