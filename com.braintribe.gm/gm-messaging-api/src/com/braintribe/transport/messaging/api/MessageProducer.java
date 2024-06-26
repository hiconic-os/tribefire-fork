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
package com.braintribe.transport.messaging.api;

import com.braintribe.model.messaging.Destination;
import com.braintribe.model.messaging.Message;

/**
 * <p>
 * A message producer is capable of sending {@link Message}(s) to a {@link Destination}.
 * 
 * <p>
 * Producers can be identified or unidentified.
 * 
 * <p>
 * An identified producer is assigned to a destination at creation time (created through
 * {@link MessagingSession#createMessageProducer(Destination)}) whereas an unidentified producer is not (created through
 * {@link MessagingSession#createMessageProducer()} or {@link MessagingSession#createMessageProducer(Destination)} with
 * a {@code null} argument).
 * 
 * <p>
 * Identified producers can send messages via {@link #sendMessage(Message)} and
 * {@link #sendMessage(Message, Destination)}, while unidentified producers must always be given a destination when
 * sending a message, through {@link #sendMessage(Message, Destination)}.
 * 
 */
public interface MessageProducer {

	/**
	 * <p>
	 * Sends the given {@link Message} to the {@link Destination} defined at the MessageProducer's creation (
	 * {@link MessagingSession#createMessageProducer(Destination)}).
	 * 
	 * @param message
	 *            The message to be sent
	 * @throws MessagingException
	 *             If the message fails to be delivered to the message broker
	 * @throws UnsupportedOperationException
	 *             if this method is invoked on an unidentified producer
	 */
	void sendMessage(Message message) throws MessagingException;

	/**
	 * <p>
	 * Sends the given {@link Message} to the given {@link Destination}.
	 * 
	 * <p>
	 * This method ignores the {@link Destination} possibly defined at the MessageProducer's creation (
	 * {@link MessagingSession#createMessageProducer(Destination)}).
	 * 
	 * @param message
	 *            The message to be sent
	 * @param destination
	 *            The target destination of the given message
	 * @throws MessagingException
	 *             If the message fails to be delivered to the message broker
	 */
	void sendMessage(Message message, Destination destination) throws MessagingException;

	/**
	 * <p>
	 * Gets the {@link Destination} set to this producer during its creation.
	 * 
	 * <p>
	 * This is the {@link Destination} used for deliveries whenever {@link #sendMessage(Message)} is used.
	 * 
	 * @return The {@link Destination} set to this producer during its creation.
	 */
	Destination getDestination();

	/**
	 * <p>
	 * Gets the length of time in milliseconds that a message produced by this producer should be retained by the
	 * message broker.
	 * 
	 * <p>
	 * {@code null} or {@code 0} means that the messages are retained indefinitely.
	 * 
	 * @return The length of time in milliseconds that a message produced by this producer should be retained by the
	 *         messaging broker.
	 */
	Long getTimeToLive();

	/**
	 * <p>
	 * Sets the length of time in milliseconds that a message produced by this producer should be retained by the
	 * message broker.
	 * 
	 * <p>
	 * If set at message level, the time to live from the message will be used.
	 * 
	 * <p>
	 * {@code null} or {@code 0} means that the messages are retained indefinitely.
	 * 
	 * @param timeToLive
	 *            The length of time in milliseconds that a message produced by this producer should be retained by the
	 *            message broker
	 */
	void setTimeToLive(Long timeToLive);

	/**
	 * <p>
	 * Gets the priority of messages produced by this producer.
	 * 
	 * @return The priority of messages produced by this producer.
	 */
	Integer getPriority();

	/**
	 * <p>
	 * Sets the priority of messages produced by this producer.
	 * 
	 * @param priority
	 *            The priority of messages produced by this producer.
	 */
	void setPriority(Integer priority);

	/**
	 * <p>
	 * Closes this message producer.
	 * 
	 * @throws MessagingException
	 *             In case of failures while closing this producer
	 */
	void close() throws MessagingException;

}
