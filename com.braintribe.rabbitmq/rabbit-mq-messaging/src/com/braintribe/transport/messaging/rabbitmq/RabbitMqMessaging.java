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

import com.braintribe.transport.messaging.api.Messaging;
import com.braintribe.transport.messaging.api.MessagingContext;

/**
 * <p>
 * Rabbit MQ implementation of the GenericModel-based messaging system.
 * 
 * @see Messaging
 */
public class RabbitMqMessaging implements Messaging<com.braintribe.model.messaging.rabbitmq.RabbitMqMessaging> {

	@Override
	public RabbitMqConnectionProvider createConnectionProvider(com.braintribe.model.messaging.rabbitmq.RabbitMqMessaging denotation,
			MessagingContext context) {

		RabbitMqConnectionProvider rabbitMqConnectionProvider = new RabbitMqConnectionProvider();
		rabbitMqConnectionProvider.setConnectionConfiguration(denotation);
		rabbitMqConnectionProvider.setMessagingContext(context);

		return rabbitMqConnectionProvider;

	}

}
