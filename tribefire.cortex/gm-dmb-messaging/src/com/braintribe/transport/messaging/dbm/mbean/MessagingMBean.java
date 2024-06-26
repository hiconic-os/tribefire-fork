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
package com.braintribe.transport.messaging.dbm.mbean;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;

import javax.management.MBeanRegistration;

/**
 * <p>
 * A MBean proving access to named BlockingQueue(s) for messages exchange.
 * 
 */
public interface MessagingMBean extends MBeanRegistration {

	public static final String name = "com.braintribe.tribefire:type=MessagingMBean";

	/**
	 * <p>
	 * Establishes a connection to this MBean.
	 * 
	 * <p>
	 * Clients must call this method to ensure that the necessary resources and tasks are properly initialized.
	 * 
	 * @return A correlation connection id.
	 */
	Long connect();

	/**
	 * <p>
	 * Closes a connection to this MBean.
	 * 
	 * <p>
	 * Clients must call this method to ensure that connection specific resources are freed and unnecessary tasks
	 * terminated.
	 * 
	 * @param connectionId
	 *            The id of the connection to be closed
	 */
	void disconnect(Long connectionId);

	/**
	 * <p>
	 * Retrieves the {@link BlockingQueue} for a given destination and subscription.
	 * 
	 * @param destinationType
	 *            The type of the destination
	 * @param destinationName
	 *            The name of the destination
	 * @param subscriptionId
	 *            The id of the consumer's subscription
	 * @return A dedicated {@link BlockingQueue} for the consumer registered under the given subscription id
	 */
	BlockingQueue<? extends Function<String, Object>> getQueue(char destinationType, String destinationName, String subscriptionId);

	/**
	 * <p>
	 * Register a topic consumer.
	 * 
	 * @param destinationName
	 *            The name of the destination
	 * @param subscriptionId
	 *            The id of the consumer's subscription
	 * @return Whether the consumer subscription was successfully registered
	 */
	boolean subscribeTopicConsumer(String destinationName, String subscriptionId);

	/**
	 * <p>
	 * Unregisters a topic consumer.
	 * 
	 * @param destinationName
	 *            The name of the destination
	 * @param subscriptionId
	 *            The id of the consumer's subscription
	 * @return Whether the consumer subscription was successfully unregistered
	 */
	boolean unsubscribeTopicConsumer(String destinationName, String subscriptionId);

	/**
	 * <p>
	 * Publishes a message.
	 * 
	 * @param destinationType
	 *            The type of the destination
	 * @param destinationName
	 *            The name of the destination
	 * @param messageId
	 *            The id of the message
	 * @param message
	 *            The message payload
	 * @param priority
	 *            The message priority
	 * @param expiration
	 *            The message expiration
	 * @param headers
	 *            The message headers
	 * @param properties
	 *            The message properties
	 */
	void sendMessage(char destinationType, String destinationName, String messageId, byte[] message, int priority, long expiration,
			Map<String, Object> headers, Map<String, Object> properties);

}
