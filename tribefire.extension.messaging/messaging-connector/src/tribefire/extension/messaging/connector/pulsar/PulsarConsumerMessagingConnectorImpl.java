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
package tribefire.extension.messaging.connector.pulsar;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.pulsar.client.api.BatchReceivePolicy;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.ConsumerStats;
import org.apache.pulsar.client.api.Messages;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;

import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.UnsatisfiedMaybeTunneling;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;

import tribefire.extension.messaging.connector.api.AbstractConsumerMessagingConnector;
import tribefire.extension.messaging.model.deployment.connector.properties.PulsarProperties;
import tribefire.extension.messaging.model.deployment.event.EventEndpointConfiguration;
import tribefire.extension.messaging.service.reason.connection.PulsarConnectionError;
import tribefire.extension.messaging.service.reason.validation.ArgumentNotSatisfied;

public class PulsarConsumerMessagingConnectorImpl extends AbstractConsumerMessagingConnector {

	private static final Logger logger = Logger.getLogger(PulsarConsumerMessagingConnectorImpl.class);

	private ClassLoader moduleClassLoader;
	private PulsarClient pulsarClient;
	private Consumer<byte[]> pulsarConsumer;

	private PulsarProperties properties;
	private PulsarServerHealthChecker checker;

	public PulsarConsumerMessagingConnectorImpl(EventEndpointConfiguration destinationConfig, ClassLoader moduleClassLoader) {
		if (destinationConfig == null) {
			throw new UnsatisfiedMaybeTunneling(Reasons.build(ArgumentNotSatisfied.T)
					.text(format("Empty configuration is provided for %s!", "PulsarConsumerConnector")).toMaybe());
		}
		this.moduleClassLoader = moduleClassLoader;
		this.properties = PulsarProperties.T.create().apply(destinationConfig);
		this.checker = new PulsarServerHealthChecker().setModuleClassLoader(this.moduleClassLoader);
		construct();
	}

	@Override
	protected List<byte[]> consumerConsume() {
		try {
			List<byte[]> result = new ArrayList<>();
			Messages<byte[]> polled = pulsarConsumer.batchReceive();
			logger.info("Polled: " + polled.size() + " messages from: " + properties.getTopicsToListen());

			for (org.apache.pulsar.client.api.Message<byte[]> m : polled) {
				result.add(m.getData());
			}
			return result;
		} catch (PulsarClientException e) {
			throw new UnsatisfiedMaybeTunneling(Reasons.build(PulsarConnectionError.T).text("Pulsar: Error in polling messages from pulsar")
					.enrich(r -> r.addThrowableInformation(e)).toMaybe());
		}
	}

	@Override
	public void finalizeConsume() {
		if (pulsarConsumer != null) {
			try {
				this.pulsarConsumer.unsubscribe();
				this.pulsarConsumer.close();
			} catch (PulsarClientException e) {
				throw new UnsatisfiedMaybeTunneling(Reasons.build(PulsarConnectionError.T).text("Pulsar: Error in closing consumer")
						.enrich(r -> r.addThrowableInformation(e)).toMaybe());
			}
		}
		if (pulsarClient != null) {
			try {
				this.pulsarClient.close();
			} catch (PulsarClientException e) {
				throw new UnsatisfiedMaybeTunneling(Reasons.build(PulsarConnectionError.T).text("Pulsar: Error in closing client")
						.enrich(r -> r.addThrowableInformation(e)).toMaybe());
			}
		}
	}

	@Override
	public CheckResultEntry actualHealth() {
		CheckResultEntry result = checker.checkServer(properties);
		if (result.getCheckStatus() == CheckStatus.ok) {
			ConsumerStats stats = pulsarConsumer.getStats();
			String metrics = "Consumer:" + "\nNum Bytes Received: " + stats.getNumBytesReceived() + ", Num Msgs Received: "
					+ stats.getNumMsgsReceived();
			result.setDetails(result.getDetails() + "\nProvider metrics: " + metrics);
		}
		return result;
	}

	private void construct() {
		ClassLoader origClassLoader = Thread.currentThread().getContextClassLoader();
		try {
			if (this.moduleClassLoader != null) {
				Thread.currentThread().setContextClassLoader(this.moduleClassLoader);
			}
			initializeVariables();
		} catch (PulsarClientException e) {
			throw new UnsatisfiedMaybeTunneling(Reasons.build(PulsarConnectionError.T).text("Pulsar: Error in initialization...")
					.enrich(r -> r.addThrowableInformation(e)).toMaybe());
		} finally {
			Thread.currentThread().setContextClassLoader(origClassLoader);
		}
	}

	private void initializeVariables() throws PulsarClientException {
		//@formatter:off
		pulsarClient = PulsarClient.builder()
				.serviceUrl(properties.getServiceUrls().stream().findFirst().orElse(null))
				.connectionTimeout(properties.getConnectionTimeout(), TimeUnit.SECONDS)
				.operationTimeout(properties.getOperationTimeout(), TimeUnit.SECONDS)
				.build();
		pulsarConsumer = pulsarClient.newConsumer()
				.topics(properties.getTopicsToListen())
				.subscriptionType(SubscriptionType.valueOf(properties.getSubscriptionType()))
				.subscriptionName(properties.getDefaultSubscriptionName())
				.batchReceivePolicy(BatchReceivePolicy.builder()
						                    .timeout(properties.getReceiveTimeout(), TimeUnit.SECONDS)
						                    .maxNumMessages(properties.getMaxNumMessages()).build())
				.subscribe();
		//@formatter:on
	}

	@Override
	public String getExternalId() {
		return properties.getGlobalId();
	}
}
