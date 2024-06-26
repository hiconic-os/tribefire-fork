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
package com.braintribe.model.email.deployment.service;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Confidential;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Abstract
public interface HasProxy extends GenericEntity {

	final EntityType<HasProxy> T = EntityTypes.T(HasProxy.class);

	@Name("Proxy Host")
	@Description("Hostname or IP address of the proxy server.")
	String getProxyHost();
	void setProxyHost(String proxyHost);

	@Name("Proxy Port")
	@Description("The listening port of the proxy server.")
	Integer getProxyPort();
	void setProxyPort(Integer proxyPort);

	@Name("Proxy User")
	@Description("The username that should be used for authentication with the proxy server.")
	String getProxyUsername();
	void setProxyUsername(String proxyUsername);

	@Name("Proxy Password")
	@Description("The password that should be used for authentication at the proxy server.")
	@Confidential
	String getProxyPassword();
	void setProxyPassword(String proxyPassword);
}
