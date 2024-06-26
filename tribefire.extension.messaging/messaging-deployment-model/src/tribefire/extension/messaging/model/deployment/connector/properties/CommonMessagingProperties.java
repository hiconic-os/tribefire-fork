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
package tribefire.extension.messaging.model.deployment.connector.properties;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface CommonMessagingProperties extends GenericEntity {

	EntityType<CommonMessagingProperties> T = EntityTypes.T(CommonMessagingProperties.class);

	String serviceUrls = "serviceUrls";
	String topicsToSend = "topicsToSend";
	String topicsToListen = "topicsToListen";
	String receiveTimeout = "receiveTimeout";

	@Initializer("['localhost:29092']") // defaults: kafka: localhost:29092, pulsar: localhost:6650
	@Description("Configure the service URL")
	List<String> getServiceUrls();
	void setServiceUrls(List<String> serviceUrls);

	@Description("Topics to listen")
	@Initializer("['newTopic']")
	List<String> getTopicsToListen();
	void setTopicsToListen(List<String> topicsToListen);

	@Description("A default Receive Timeout in seconds")
	@Initializer("10")
	Integer getReceiveTimeout();
	void setReceiveTimeout(Integer receiveTimeout);
}
