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
package com.braintribe.mq.model.deployment;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.messagingdeployment.jms.JmsConnection;

public interface JmsMqConnection extends JmsConnection {

	final EntityType<JmsMqConnection> T = EntityTypes.T(JmsMqConnection.class);

	String getHost();
	void setHost(String hostAddress);
	
	@Initializer("false")
	boolean getEnableTracing();
	void setEnableTracing(boolean enableTracing);
	
	@Initializer("-1")
	int getCcsId();
	void setCcsId(int ccsId);
	
	@Initializer("false")
	boolean getUseBindingsModeConnections();
	void setUseBindingsModeConnections(boolean useBindingsModeConnections);
	
	String getChannel();
	void setChannel(String channel);
	
	String getQueueManager();
	void setQueueManager(String queueManager);
	
	@Initializer("1414")	
	int getPort();
	void setPort(int port);
	
	String getSslPeerName();
	void setSslPeerName(String sslPeerName);
	
	String getSslCipherSuite();
	void setSslCipherSuite(String sslCipherSuite);
	
	String getTargetClient();
	void setTargetClient(String targetClient);
	
	@Initializer("-1l")	
	long getDestinationExpiry();
	void setDestinationExpiry(long destinationExpiry);
}
