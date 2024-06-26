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
package com.braintribe.transport.messaging.rabbitmq;

import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.SpecialEnvironment;
import com.braintribe.transport.messaging.api.MessageProducer;
import com.braintribe.transport.messaging.api.MessagingConnection;
import com.braintribe.transport.messaging.api.MessagingConnectionProvider;
import com.braintribe.transport.messaging.api.MessagingContext;
import com.braintribe.transport.messaging.api.test.GmMessagingDeliveryTopicTest;
import com.rabbitmq.client.AMQP;

@Category(SpecialEnvironment.class)
public class RabbitMqMessagingDeliveryTopicTest extends GmMessagingDeliveryTopicTest {

	@Override
	protected MessagingConnectionProvider<? extends MessagingConnection> getMessagingConnectionProvider() {
		return RabbitMqMessagingConnectionProvider.instance.get();
	}

	@Override
	protected MessagingContext getMessagingContext() {
		return RabbitMqMessagingConnectionProvider.instance.getMessagingContext();
	}

	@BeforeClass
	public static void configure() {
		// Overriding test parameters
		multipleMessagesQty = 200;
	}

	/**
	 * <p>
	 * Produces an unmarshallable message payload to ensure the consumers behavior upon marshalling errors.
	 */
	@Override
	protected void sendUnmarshallableMessage(MessageProducer messageProducer) {

		RabbitMqMessageProducer producer = (RabbitMqMessageProducer) messageProducer;

		RabbitMqDestination destination = producer.getRabbitMqDestination();

		AMQP.BasicProperties.Builder propBuilder = new AMQP.BasicProperties.Builder();

		String mimeType = "application/octet-stream"; 
		// TODO: remove line : producer.getSession().getConnection().getMessageMarshaller().getOutgoingMessagesType();

		// deliveryMode '2' means persistent
		propBuilder.contentType(mimeType).contentEncoding("UTF-8").deliveryMode(2);

		byte[] messageBody = "This is just an example of unmarshallable body".getBytes();

		try {
			producer.getChannel().basicPublish(destination.getExchangeName(), destination.getRoutingKey(), true, propBuilder.build(), messageBody);
		} catch (Exception e) {
			throw new IllegalStateException("Test failed to send an unmarshallable message", e);
		}

	}

}
