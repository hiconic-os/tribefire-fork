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
package com.braintribe.transport.jms.queuecomm;

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.braintribe.transport.jms.IServer;
import com.braintribe.transport.jms.queuecomm.IQueueCommunication.QueueOperation;

public interface IQueueContext {
		public Message receiveMessage(long timeout) throws Exception;

		public void connect() throws Exception;

		public void disconnect();

		public String getQueueName();

		public Session getResponseSession();

		public void reconnect() throws Exception;

		public void reply(Message replyMsg, Destination replyDestination) throws Exception;

		public void send(Message msg, int deliveryMode, int priority, long timeToLive) throws Exception;

		public void send(Message msg) throws Exception;

		public void commit(boolean hideErrors) throws Exception;
		
		public void rollback(boolean hideErrors) throws Exception;
		
		public MapMessage createMapMessage() throws Exception;

		public ObjectMessage createObjectMessage() throws Exception;

		public TextMessage createTextMessage() throws Exception;

		public BytesMessage createBytesMessage() throws Exception;

		public QueueOperation getQueueOperation();
		
		public void setQueueOperation(QueueOperation queueOperation);

		public String getMessageSelector();

		public void setMessageSelector(String messageSelector);
		
		public boolean isTransactionalSession();

		public Destination getDestination();

		public Destination createTemporaryResponseQueue() throws Exception;

		public Destination getDestination(String queueName) throws Exception;
		
		
	/*
	 * Getters and Setters
	 */
		public IServer getServer();

		public void setServer(IServer arg);

		public int getAcknowledgeMode();

		public void setAcknowledgeMode(int arg);

		public void setQueueName(String queueName);

}
