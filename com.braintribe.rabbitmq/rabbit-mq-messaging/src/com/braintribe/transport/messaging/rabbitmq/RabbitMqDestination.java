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

import com.braintribe.model.messaging.Destination;
import com.braintribe.model.messaging.Queue;
import com.braintribe.model.messaging.Topic;

/**
 * <p>
 * Rabbit MQ representation of a {@link Destination}, composed by a pair of Strings (exchange name and a routing key)
 *
 * <p>
 * For {@link Topic}(s), the topic name is used as the exchange name and the routing key is set to the wild-card
 * character (#).
 * 
 * <p>
 * For {@link Queue}(s), the queue name is used as the routing key and the exchange name is left blank (default
 * exchange).
 * 
 */
public class RabbitMqDestination {
	
	private String id;
	private Destination destination;
	private String exchangeName;
	private String routingKey;
	
	public RabbitMqDestination(Destination destination) {
		if (destination instanceof Topic) {
			this.exchangeName = destination.getName();
			this.routingKey = "#";
			generateId("Topic");
		} else if (destination instanceof Queue) {
			this.exchangeName = "";
			this.routingKey = destination.getName();
			generateId("Queue");
		} else {
			throw new UnsupportedOperationException("Unsupported destination type: "+destination);
		}
	}
	
	public Destination getDestination() {
		return destination;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public String getRoutingKey() {
		return routingKey;
	}
	
	private void generateId(String destinationType) {
		this.id = destinationType+"[exchangeName=\""+exchangeName+"\",routingKey=\""+routingKey+"\"]";
	}
	
	@Override
	public String toString() {
		return id;
	}

}
