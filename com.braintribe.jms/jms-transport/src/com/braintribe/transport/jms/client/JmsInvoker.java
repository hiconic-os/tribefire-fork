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
package com.braintribe.transport.jms.client;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.TextMessage;

import com.braintribe.logging.Logger;
import com.braintribe.transport.jms.message.MessageContext;
import com.braintribe.transport.jms.queuecomm.IQueueCommunication;
import com.braintribe.transport.jms.queuecomm.IQueueContext;
import com.braintribe.transport.jms.util.JmsUtil;

public class JmsInvoker {

	private static Logger logger = Logger.getLogger(JmsInvoker.class);

	protected IQueueCommunication sendQueueComm = null;
	protected IQueueCommunication recvQueueComm = null;
	protected Destination responseQueue = null;
	protected IQueueContext queueContext = null;
	protected long responseTimeout = 300000L;

	protected String sessionID = null;
	protected Map<String, String> metaAttributes = new HashMap<String, String>();

	public JmsInvoker(IQueueCommunication pSendQueueComm, IQueueCommunication pRecvQueueComm) {
		this.sendQueueComm = pSendQueueComm;
		this.recvQueueComm = pRecvQueueComm;
	}

	public void addMetaAttribute(String key, String value) {
		if ((key != null) && (value != null)) {
			this.metaAttributes.put(key, value);
		}
	}

	public void addMetaAttributes(Map<String, String> ma) {
		if (ma != null) {
			this.metaAttributes.putAll(ma);
		}
	}

	public String sendMessageAndWaitForResponse(String text) throws Exception {

		String correlationID = JmsUtil.generateCorrelationID();

		logger.debug("Sending message (correlationID="+correlationID+") " + text);
		String messageID = this.sendJMSMessage(text, correlationID);

		logger.debug("The message ID of the outgoing message was " + messageID);
		logger.debug("Waiting for a response with ID " + correlationID + " in the correlation ID field.");

		MessageContext responseMC = null;

		IQueueContext recvQueueContext = this.recvQueueComm.getQueueContext(false);
		recvQueueContext.setMessageSelector("JMSCorrelationID = '" + correlationID + "'");
		recvQueueContext.connect();
		javax.jms.Message msg = recvQueueContext.receiveMessage(this.responseTimeout);

		if (msg == null) {
			logger.debug("Waited " + this.responseTimeout + " ms for an answer to " + messageID);

			throw new Exception("Got no JMS response for message " + text);
		} else {
			logger.debug("Received message context " + responseMC + " for " + messageID);
		}

		if (msg instanceof TextMessage) {

			logger.debug("Received message is a text message.");

			TextMessage tm = (TextMessage) msg;
			String responseText = null;
			try {
				responseText = tm.getText();
				logger.debug("Response text is " + responseText);
			} catch (Exception e) {
				throw new Exception("Could not get response text from text message " + msg, e);
			}
			return responseText;
		} else if (msg instanceof MapMessage) {

			logger.debug("Received message is a map message.");
			MapMessage mm = (MapMessage) msg;
			String responseText = null;
			try {
				responseText = mm.getString("body");
				logger.debug("Response text is " + responseText);
			} catch (Exception e) {
				throw new Exception("Could not get response text from map message " + msg, e);
			}
			return responseText;
		} else {
			try {
				throw new Exception("Unsupported JMS message type " + msg.getJMSType() + " (" + msg.getClass() + ")");
			} catch (Exception e) {
				throw new Exception("Unsupported JMS message class " + msg.getClass());
			}
		}
	}
	
	protected String sendJMSMessage(String text, String correlationID) throws Exception {
		try {
			this.queueContext = this.sendQueueComm.getQueueContext(true);
			this.queueContext.connect();

			TextMessage tm = this.queueContext.createTextMessage();

			if ((this.metaAttributes != null) && (this.metaAttributes.size() > 0)) {
				for (Map.Entry<String,String> entry : this.metaAttributes.entrySet()) {
					tm.setStringProperty(entry.getKey(), entry.getValue());
				}
			}

			tm.setText(text);
			tm.setJMSCorrelationID(correlationID);
			tm.setJMSReplyTo(this.responseQueue);
			this.queueContext.send(tm);

			return tm.getJMSMessageID();

		} catch (Exception e) {
			throw new Exception("Could not send text " + text + " to JMS system.", e);
		}
	}

	

	public long getResponseTimeout() {
		return responseTimeout;
	}
	public void setResponseTimeout(long responseTimeout) {
		this.responseTimeout = responseTimeout;
	}

	public static String getBuildVersion() {
		return "$Build_Version$ $Id: JmsInvoker.java 86405 2015-05-28 14:38:35Z roman.kurmanowytsch $";
	}

}
