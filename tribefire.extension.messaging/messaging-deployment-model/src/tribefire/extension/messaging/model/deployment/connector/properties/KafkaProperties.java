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

import java.util.Optional;
import java.util.Properties;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface KafkaProperties extends CommonMessagingProperties {
	EntityType<KafkaProperties> T = EntityTypes.T(KafkaProperties.class);

	String aks = "aks";
	String retries = "retries";
	String batchSize = "batchSize";
	String lingerMs = "lingerMs";
	String bufferMemory = "bufferMemory";
	String kafkaGroupId = "kafkaGroupId";
	String reconnectTimeout = "reconnectTimeout";
	String groupInstanceId = "groupInstanceId";
	String maxPollIntervalMs = "maxPollIntervalMs";
	String pollDuration = "pollDuration";
	String sessionTimout = "sessionTimout";
	String sslKeyPassword = "sslKeyPassword";
	String sslKeystoreCertificateChain = "sslKeystoreCertificateChain";
	String sslKeystoreKey = "sslKeystoreKey";
	String sslKeystoreLocation = "sslKeystoreLocation";
	String sslKeystorePassword = "sslKeystorePassword";
	String sslTruststoreCertificates = "sslTruststoreCertificates";
	String sslTruststoreLocation = "sslTruststoreLocation";
	String sslTrustStorePassword = "sslTrustStorePassword";

	@Initializer("'all'")
	String getAks();
	void setAks(String aks);

	@Initializer("100")
	Integer getRetries();
	void setRetries(Integer retries);

	@Initializer("16384")
	Integer getBatchSize();
	void setBatchSize(Integer batchSize);

	@Initializer("1")
	Integer getLingerMs();
	void setLingerMs(Integer lingerMs);

	@Initializer("33554432")
	Integer getBufferMemory();
	void setBufferMemory(Integer bufferMemory);

	@Initializer("'groupId'")
	String getKafkaGroupId();
	void setKafkaGroupId(String groupId);

	@Initializer("100")
	Integer getReconnectTimeout();
	void setReconnectTimeout(Integer reconnectionTimeout);

	String getGroupInstanceId();
	void setGroupInstanceId(String groupInstanceId);

	Integer getMaxPollIntervalMs();
	void setMaxPollIntervalMs(Integer maxPollIntervalMs);

	@Initializer("100000")
	Integer getSessionTimout();
	void setSessionTimout(Integer sessionTimout);

	@Initializer("10")
	Integer getPollDuration();
	void setPollDuration(Integer pollDuration);

	String getSslKeyPassword();
	void setSslKeyPassword(String sslKeyPassword);

	String getSslKeystoreCertificateChain();
	void setSslKeystoreCertificateChain(String sslKeystoreCertificateChain);

	String getSslKeystoreKey();
	void setSslKeystoreKey(String sslKeystoreKey);

	String getSslKeystoreLocation();
	void setSslKeystoreLocation(String sslKeystoreLocation);

	String getSslKeystorePassword();
	void setSslKeystorePassword(String sslKeystorePassword);

	String getSslTruststoreCertificates();
	void setSslTruststoreCertificates(String sslTruststoreCertificates);

	String getSslTruststoreLocation();
	void setSslTruststoreLocation(String sslTruststoreLocation);

	String getSslTrustStorePassword();
	void setSslTrustStorePassword(String sslTrustStorePassword);

	default Properties getKafkaProperties(String externalId) {
		Properties props = new Properties();
		props.put("bootstrap.servers", String.join(",", this.getServiceUrls()));
		props.put("acks", this.getAks());

		props.put("client.id", externalId);
		props.put("group.id", this.getKafkaGroupId());
		Optional.ofNullable(this.getGroupInstanceId()).ifPresent(id -> props.put("group.instance.id", id));

		props.put("reconnect.backoff.ms", this.getReconnectTimeout());
		props.put("retries", this.getRetries());
		props.put("batch.size", this.getBatchSize());
		props.put("linger.ms", this.getLingerMs());
		props.put("buffer.memory", this.getBufferMemory());
		props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");

		Optional.ofNullable(this.getSessionTimout()).ifPresent(t -> props.put("session.timeout.ms", t));

		// SSL settings
		Optional.ofNullable(this.getSslKeyPassword()).ifPresent(p -> props.put("ssl.key.password", p)); // NA
		Optional.ofNullable(this.getSslKeystoreCertificateChain()).ifPresent(p -> props.put("ssl.keystore.certificate.chain", p)); // NA
		Optional.ofNullable(this.getSslKeystoreKey()).ifPresent(p -> props.put("ssl.keystore.key", p)); // NA
		Optional.ofNullable(this.getSslKeystoreLocation()).ifPresent(p -> props.put("ssl.keystore.location", p)); // NA
		Optional.ofNullable(this.getSslKeystorePassword()).ifPresent(p -> props.put("ssl.keystore.password", p)); // NA
		Optional.ofNullable(this.getSslTruststoreCertificates()).ifPresent(p -> props.put("ssl.truststore.certificates", p)); // NA
		Optional.ofNullable(this.getSslTruststoreLocation()).ifPresent(p -> props.put("ssl.truststore.location", p)); // NA
		Optional.ofNullable(this.getSslTrustStorePassword()).ifPresent(p -> props.put("ssl.truststore.password", p)); // NA

		return props;
	}

}
