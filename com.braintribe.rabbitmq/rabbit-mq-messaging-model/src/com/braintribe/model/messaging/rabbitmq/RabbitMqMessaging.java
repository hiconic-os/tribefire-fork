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
package com.braintribe.model.messaging.rabbitmq;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.messaging.expert.Messaging;
import com.braintribe.model.plugin.Plugable;

/**
 * <p>
 * A {@link Plugable} denotation type holding the basic configuration properties of a RabbitMQ connection factory
 * ({@code com.rabbitmq.client.ConnectionFactory}).
 * 
 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
 */
public interface RabbitMqMessaging extends Messaging {

	final EntityType<RabbitMqMessaging> T = EntityTypes.T(RabbitMqMessaging.class);

	String getHost();
	void setHost(String host);

	String getVirtualHost();
	void setVirtualHost(String virtualHost);

	int getPort();
	void setPort(int port);

	String getUri();
	void setUri(String uri);

	String getUsername();
	void setUsername(String username);

	String getPassword();
	void setPassword(String password);

	List<String> getAddresses();
	void setAddresses(List<String> addresses);

	/**
	 * <p>
	 * Returns whether the automatic connection recovery process is enabled.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @return Whether the automatic connection recovery process is enabled.
	 */
	Boolean getAutomaticRecoveryEnabled();

	/**
	 * <p>
	 * Determines whether the automatic connection recovery process is enabled.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @param automaticRecoveryEnabled
	 *            Whether the automatic connection recovery process is enabled.
	 */
	void setAutomaticRecoveryEnabled(Boolean automaticRecoveryEnabled);

	/**
	 * <p>
	 * Returns whether the topology recovery process is enabled.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @return Whether the topology recovery process is enabled.
	 */
	Boolean getTopologyRecoveryEnabled();

	/**
	 * <p>
	 * Determines whether the topology recovery process is enabled.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @param topologyRecoveryEnabled
	 *            Whether the topology recovery process is enabled.
	 */
	void setTopologyRecoveryEnabled(Boolean topologyRecoveryEnabled);

	/**
	 * <p>
	 * Returns the connection timeout.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @return The connection timeout.
	 */
	Integer getConnectionTimeout();

	/**
	 * <p>
	 * Determines the connection timeout.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @param connectionTimeout
	 *            The connection timeout.
	 */
	void setConnectionTimeout(Integer connectionTimeout);

	/**
	 * <p>
	 * Returns the automatic connection recovery retrial time interval (in milliseconds)
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @return The automatic connection recovery retrial time interval (in milliseconds)
	 */
	Integer getNetworkRecoveryInterval();

	/**
	 * <p>
	 * Determines the automatic connection recovery retrial time interval (in milliseconds).
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @param networkRecoveryInterval
	 *            The automatic connection recovery retrial time interval (in milliseconds).
	 */
	void setNetworkRecoveryInterval(Integer networkRecoveryInterval);

	/**
	 * <p>
	 * Returns the heart beat timeout interval used for connections.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @return The heart beat timeout interval used for connections.
	 */
	Integer getRequestedHeartbeat();

	/**
	 * <p>
	 * Determines the heart beat timeout interval used for connections.
	 * 
	 * @see <a href="https://www.rabbitmq.com/api-guide.html">RabbitMQ - Java Client API Guide</a>
	 * @param requestedHeartbeat
	 *            The heart beat timeout interval used for connections.
	 */
	void setRequestedHeartbeat(Integer requestedHeartbeat);

}
