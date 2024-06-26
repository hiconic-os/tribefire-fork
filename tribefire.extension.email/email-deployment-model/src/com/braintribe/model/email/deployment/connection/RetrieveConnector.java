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
package com.braintribe.model.email.deployment.connection;

import com.braintribe.model.descriptive.HasCredentials;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Abstract
@SelectiveInformation("Retrieval Connector ${host}")
public interface RetrieveConnector extends EmailConnector, HasCredentials {

	final EntityType<RetrieveConnector> T = EntityTypes.T(RetrieveConnector.class);

	@Name("Host")
	@Description("Hostname or IP address of the mail server.")
	@Mandatory
	String getHost();
	void setHost(String host);

	@Name("Port")
	@Description("The listening port of the mail server.")
	@Mandatory
	Integer getPort();
	void setPort(Integer port);

	@Name("User")
	@Description("The username that should be used for authentication with the mail server.")
	@Override
	@Mandatory
	String getUser();

	@Name("Password")
	@Description("The password that should be used for authentication.")
	@Override
	@Mandatory
	String getPassword();

}
