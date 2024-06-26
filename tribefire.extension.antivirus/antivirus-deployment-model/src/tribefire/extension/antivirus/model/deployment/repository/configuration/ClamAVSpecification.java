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
package tribefire.extension.antivirus.model.deployment.repository.configuration;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ClamAVSpecification extends ProviderSpecification {

	EntityType<ClamAVSpecification> T = EntityTypes.T(ClamAVSpecification.class);

	@Mandatory
	@Name("URL")
	@Description("URL endpoint")
	String getURL();
	void setURL(String url);

	@Mandatory
	@Name("Port")
	@Description("Port (default: 3310)")
	@Initializer("3310")
	int getPort();
	void setPort(int port);

	@Mandatory
	@Name("Port")
	@Description("Timeout in milliseconds (default: 20 s)")
	@Initializer("20000")
	int getTimeout();
	void setTimeout(int timeout);
}
