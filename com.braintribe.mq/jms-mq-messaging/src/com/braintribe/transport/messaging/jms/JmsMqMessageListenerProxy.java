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
package com.braintribe.transport.messaging.jms;

import com.braintribe.logging.Logger;
import com.braintribe.model.messaging.Message;
import com.braintribe.model.messaging.jms.AcknowledgeMode;
import com.braintribe.transport.messaging.api.MessageListener;
import com.braintribe.transport.messaging.api.MessagingException;

public class JmsMqMessageListenerProxy implements javax.jms.MessageListener {

	private static final Logger logger = Logger.getLogger(JmsMqMessageListenerProxy.class);

	protected MessageListener messageListener = null;
	protected JmsMqMessageConsumer messageConsumer = null;

	public JmsMqMessageListenerProxy(MessageListener messageListener, JmsMqMessageConsumer messageConsumer) {
		this.messageListener = messageListener;
		this.messageConsumer = messageConsumer;
	}

	@Override
	public void onMessage(javax.jms.Message jmsMessage) {
		if (jmsMessage == null) {
			return;
		}
		try {
			JmsMqSession session = messageConsumer.getSession();
			Message message = MqMessageConverter.toMessage(session, jmsMessage);
			if (message != null) {
				this.messageListener.onMessage(message);
			}
		} catch (MessagingException e) {
			throw new RuntimeException("Could not forward message to message listener", e);
		}

		AcknowledgeMode acknowledgeMode = messageConsumer.getSession().getConnection().getConfiguration().getAcknowledgeMode();
		if ((acknowledgeMode != null) && (acknowledgeMode.equals(AcknowledgeMode.AFTERPROCESSING))) {
			try {
				jmsMessage.acknowledge();
			} catch (Exception e) {
				logger.error("Could not acknowledge message "+jmsMessage, e);
			}
		}

	}

	public MessageListener getMessageListener() {
		return this.messageListener;
	}

}
