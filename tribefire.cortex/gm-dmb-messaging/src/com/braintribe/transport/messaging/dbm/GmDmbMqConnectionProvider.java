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
package com.braintribe.transport.messaging.dbm;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServerConnection;

import com.braintribe.transport.messaging.api.MessagingConnectionProvider;
import com.braintribe.transport.messaging.api.MessagingContext;
import com.braintribe.transport.messaging.api.MessagingException;

/**
 * <p>
 * {@link MessagingConnectionProvider} implementation for providing {@link GmDmbMqConnection}(s).
 * 
 * @see MessagingConnectionProvider
 * @see GmDmbMqConnection
 */
public class GmDmbMqConnectionProvider implements MessagingConnectionProvider<GmDmbMqConnection> {

    private com.braintribe.model.messaging.dmb.GmDmbMqMessaging providerConfiguration;
    private MessagingContext messagingContext;


	public GmDmbMqConnectionProvider() {
    }
    
    public void setConnectionConfiguration(com.braintribe.model.messaging.dmb.GmDmbMqMessaging providerConfiguration) {
    	this.providerConfiguration = providerConfiguration;
    }
    
    public MessagingContext getMessagingContext() {
		return messagingContext;
	}

	public void setMessagingContext(MessagingContext messagingContext) {
		this.messagingContext = messagingContext;
	}
    
	@Override
	public GmDmbMqConnection provideMessagingConnection() throws MessagingException {
		
		GmDmbMqConnection gmDmbMqConnection = new GmDmbMqConnection();
		
		gmDmbMqConnection.setConnectionProvider(this);
		gmDmbMqConnection.setMBeanServerConnection(createMBeanServerConnection());
		
		return gmDmbMqConnection;
	}
	
	/**
	 * <p>
	 * Creates a {@link MBeanServerConnection} based on the configuration provided via 
	 * {@link #setConnectionConfiguration(com.braintribe.model.messaging.dmb.GmDmbMqMessaging)} 
	 * 
	 * @return The {@link MBeanServerConnection} created
	 * @throws MessagingException If a {@link MBeanServerConnection} fails to be established
	 */
	protected MBeanServerConnection createMBeanServerConnection() throws MessagingException {
		
		if (this.providerConfiguration == null) {
			throw new MessagingException("No connection provider configuration was set to this connection provider");
		}
		
		return ManagementFactory.getPlatformMBeanServer();
	}

	@Override
	public void close() {
		// no-op, there is nothing to be closed so far in this MessagingConnectionProvider
	}
	
	@Override
	public String toString() {
		return description();
	}

	@Override
	public String description() {
		return "DMB Messaging";
	}
}
