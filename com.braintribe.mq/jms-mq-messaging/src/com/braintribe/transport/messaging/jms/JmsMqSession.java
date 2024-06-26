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

import java.io.Closeable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import com.braintribe.logging.Logger;
import com.braintribe.model.messaging.Destination;
import com.braintribe.model.messaging.Message;
import com.braintribe.transport.messaging.api.MessageConsumer;
import com.braintribe.transport.messaging.api.MessageProducer;
import com.braintribe.transport.messaging.api.MessagingException;
import com.braintribe.transport.messaging.api.MessagingSession;

public class JmsMqSession implements MessagingSession {

	private static final Logger logger = Logger.getLogger(JmsMqSession.class);
	
	private JmsMqConnection connection;

	private Set<MqMessageHandler> handlers = new HashSet<>();
	private ReentrantLock handlersLock = new ReentrantLock();

	public JmsMqSession(JmsMqConnection connection) {
		this.connection = connection;
	}


	@Override
	public void open() throws MessagingException {
		//Nothing to do
	}


	@Override
	public void close() throws MessagingException {
		handlersLock.lock();
		try {
			for (Closeable s : handlers) {
				try {
					s.close();
				} catch (Exception e) {
					logger.error("Could not close: "+s, e);
				}
			}
		} finally {
			handlersLock.unlock();
		}
		
	}


	@Override
	public com.braintribe.model.messaging.Queue createQueue(String name) throws MessagingException {
		com.braintribe.model.messaging.Queue queue = com.braintribe.model.messaging.Queue.T.create();
		queue.setName(name);
		return queue;
	}


	@Override
	public com.braintribe.model.messaging.Topic createTopic(String name) throws MessagingException {
		com.braintribe.model.messaging.Topic topic = com.braintribe.model.messaging.Topic.T.create();
		topic.setName(name);
		return topic;
	}


	@Override
	public Message createMessage() throws MessagingException {
		return Message.T.create();
	}


	@Override
	public MessageProducer createMessageProducer() throws MessagingException {
		JmsMqMessageProducer producer = new JmsMqMessageProducer(this);
		handlersLock.lock();
		try {
			handlers.add(producer);
		} finally {
			handlersLock.unlock();
		}
		return producer;
	}


	@Override
	public MessageProducer createMessageProducer(Destination destination) throws MessagingException {
		JmsMqMessageProducer producer = new JmsMqMessageProducer(this, destination);
		handlersLock.lock();
		try {
			handlers.add(producer);
		} finally {
			handlersLock.unlock();
		}
		return producer;
	}


	@Override
	public MessageConsumer createMessageConsumer(Destination destination) throws MessagingException {
		JmsMqMessageConsumer consumer = new JmsMqMessageConsumer(this, destination);
		handlersLock.lock();
		try {
			handlers.add(consumer);
		} finally {
			handlersLock.unlock();
		}
		return consumer;
	}

	protected void resetSession() {
		try {
			this.reconnect();
		} catch (MessagingException e) {
			logger.error("Could not reset session.", e);
		}
	}

	protected void reconnect() throws MessagingException {
		for (MqMessageHandler handler : handlers) {
			handler.reset();
		}
	}

	public JmsMqConnection getConnection() {
		return connection;
	}

}
