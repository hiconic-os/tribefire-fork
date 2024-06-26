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
package com.braintribe.model.gcp.deployment;

import com.braintribe.model.cache.HasCacheOptions;
import com.braintribe.model.extensiondeployment.BinaryPersistence;
import com.braintribe.model.extensiondeployment.BinaryRetrieval;
import com.braintribe.model.generic.annotation.meta.Pattern;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface GcpStorageBinaryProcessor extends BinaryPersistence, BinaryRetrieval, HasCacheOptions {

	EntityType<GcpStorageBinaryProcessor> T = EntityTypes.T(GcpStorageBinaryProcessor.class);

	final static String connector = "connector";
	final static String bucketName = "bucketName";

	void setConnector(GcpConnector connector);
	GcpConnector getConnector();

	void setBucketName(String bucketName);
	@Pattern("^[a-z0-9\\-_\\.]+$")
	String getBucketName();

	void setPathPrefix(String pathPrefix);
	String getPathPrefix();

}
