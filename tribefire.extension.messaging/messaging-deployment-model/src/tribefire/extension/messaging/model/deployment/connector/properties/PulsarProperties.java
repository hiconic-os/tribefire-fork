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

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.messaging.model.deployment.event.EventEndpointConfiguration;
import tribefire.extension.messaging.model.deployment.event.PulsarEndpoint;

public interface PulsarProperties extends CommonMessagingProperties {

	EntityType<PulsarProperties> T = EntityTypes.T(PulsarProperties.class);

	String connectionTimeout = "connectionTimeout";
	String operationTimeout = "operationTimeout";
	String webServiceUrl = "webServiceUrl";
	String compressionType = "compressionType";
	String batchingMaxMessages = "batchingMaxMessages";
	String accessMode = "accessMode";
	String defaultSubscriptionName = "defaultSubscriptionName";
	String defaultMaxNumMessages = "defaultMaxNumMessages";
	String subscriptionType = "subscriptionType";

	@Description("A default subscription type to be used if there is none specified")
	@Initializer("'subscriptionName'")
	String getDefaultSubscriptionName();
	void setDefaultSubscriptionName(String defaultSubscriptionName);

	@Description("A default maximum number of messages to listen")
	@Initializer("100")
	Integer getMaxNumMessages();
	void setMaxNumMessages(Integer maxNumMessages);

	@Description("A default subscription type to listen. Possible values: Exclusive, Shared, Failover, Key_Shared")
	@Initializer("'Shared'")
	String getSubscriptionType();
	void setSubscriptionType(String subscriptionType);

	@Description("A default compression type to send message. Possible values: LZ4, NONE, ZLIB, ZSTD, SNAPPY")
	@Initializer("'NONE'")
	String getCompressionType();
	void setCompressionType(String compressionType);

	@Description("Set the maximum number of messages permitted in a batch")
	@Initializer("1")
	Integer getBatchingMaxMessages();
	void setBatchingMaxMessages(Integer batchingMaxMessages);

	void setAccessMode(String accessMode);
	@Description("Configure the type of access mode that the producer requires on the topic. Possible values: Shared, Exclusive, WaitForExclusive")
	@Initializer("'Shared'")
	String getAccessMode();

	@Description("Set the duration of time in seconds to wait for a connection to a broker to be established")
	@Initializer("10")
	Integer getConnectionTimeout();
	void setConnectionTimeout(Integer connectionTimeout);

	@Description("Set operation timeout in seconds")
	@Initializer("30")
	Integer getOperationTimeout();
	void setOperationTimeout(Integer operationTimeout);

	@Description("Web service url for pulsar admin")
	@Initializer("'http://localhost:8081'")
	String getWebServiceUrl();
	void setWebServiceUrl(String webServiceUrl);

	default PulsarProperties apply(EventEndpointConfiguration config) {
		PulsarEndpoint endpoint = (PulsarEndpoint) config.getEventEndpoint();
		this.setGlobalId(endpoint.getGlobalId());
		this.setServiceUrls(List.of(endpoint.getConnectionUrl()));
		this.setWebServiceUrl(endpoint.getAdminUrl());
		this.setTopicsToListen(config.getTopics().stream().toList());
		return this;
	}
}
