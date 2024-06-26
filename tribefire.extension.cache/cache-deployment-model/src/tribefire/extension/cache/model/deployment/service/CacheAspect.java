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
package tribefire.extension.cache.model.deployment.service;

import com.braintribe.model.extensiondeployment.ServiceAroundProcessor;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface CacheAspect extends ServiceAroundProcessor {

	final EntityType<CacheAspect> T = EntityTypes.T(CacheAspect.class);

	String configuration = "configuration";
	String useCache = "useCache";
	String hashAlgorithm = "hashAlgorithm";

	@Mandatory
	CacheAspectConfiguration getConfiguration();
	void setConfiguration(CacheAspectConfiguration configuration);

	@Initializer("true")
	@Mandatory
	boolean getUseCache();
	void setUseCache(boolean useCache);

	@Mandatory
	@Initializer("enum(tribefire.extension.cache.model.deployment.service.HashAlgorithm,SHA256)")
	HashAlgorithm getHashAlgorithm();
	void setHashAlgorithm(HashAlgorithm hashAlgorithm);

}
