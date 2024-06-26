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
package com.braintribe.transport.jms.message;

import javax.jms.Message;

import com.braintribe.transport.jms.queuecomm.IQueueCommunication;
import com.braintribe.transport.jms.queuecomm.IQueueContext;

public interface IMessageContext {

	public IQueueContext getQueueContext();

	public Message getMessage();

	public void reply(String replyText) throws Exception;

	public String getSessionId();

	public void setSessionId(String sessionId);

	public String toString();

	public IQueueCommunication getReplyQueue();

	public void setReplyQueue(IQueueCommunication replyQueue);

	public IMessageContext cloneWithNewMessage(Message newMessage);
}
