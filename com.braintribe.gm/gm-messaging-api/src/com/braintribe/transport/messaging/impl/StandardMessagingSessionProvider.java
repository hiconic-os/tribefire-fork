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
package com.braintribe.transport.messaging.impl;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.transport.messaging.api.MessagingConnection;
import com.braintribe.transport.messaging.api.MessagingConnectionProvider;
import com.braintribe.transport.messaging.api.MessagingException;
import com.braintribe.transport.messaging.api.MessagingSession;
import com.braintribe.transport.messaging.api.MessagingSessionProvider;

/**
 * Natural implementation of {@link MessagingSessionProvider}.
 * <p>
 * This component establishes and manages {@link MessagingConnection}(s) to the platform message broker, providing
 * {@link MessagingSession}(s).
 * 
 */
public class StandardMessagingSessionProvider implements MessagingSessionProvider, LifecycleAware {

	// constants
	private static final Logger log = Logger.getLogger(StandardMessagingSessionProvider.class);

	// configurable
	private MessagingConnectionProvider<?> messagingConnectionProvider;
	private boolean lazyInitialization = true;

	// cached
	private MessagingConnection messagingConnection;

	public StandardMessagingSessionProvider() {
	}

	@Required
	@Configurable
	public void setMessagingConnectionProvider(MessagingConnectionProvider<?> messagingConnectionProvider) {
		this.messagingConnectionProvider = messagingConnectionProvider;
	}

	@Configurable
	public void setLazyInitialization(boolean lazyInitialization) {
		this.lazyInitialization = lazyInitialization;
	}

	@Override
	public void postConstruct() {
		ensureConnection(true);
	}

	@Override
	public void preDestroy() {
		close();
	}

	@Override
	public MessagingSession provideMessagingSession() throws MessagingException {
		ensureConnection(false);

		return messagingConnection.createMessagingSession();
	}

	@Override
	public void close() {
		closeConnection();
		closeConnectionProvider();
	}

	private void closeConnection() {
		MessagingConnection connection = messagingConnection;

		if (connection == null)
			return;

		try {
			connection.close();

			log.debug(() -> "Closed messaging connection: " + connection);

		} catch (Exception e) {
			log.error("Failed to close messaging connection", e);
		}
	}

	private void closeConnectionProvider() {
		MessagingConnectionProvider<?> connectionProvider = messagingConnectionProvider;
		if (connectionProvider == null)
			return;

		try {
			connectionProvider.close();

			log.debug(() -> "Closed messaging connection provider: " + connectionProvider);

		} catch (Exception e) {
			log.error("Failed to close messaging connection provider", e);
		}
	}

	private void initializeConnection() {
		if (messagingConnection != null)
			return;

		synchronized (this) {
			if (messagingConnection == null) {
				messagingConnection = messagingConnectionProvider.provideMessagingConnection();

				log.debug(() -> "Initialized messaging connection: " + messagingConnection);
			}
		}
	}

	private void ensureConnection(boolean startup) {
		if (messagingConnection == null && startup ^ lazyInitialization) {
			initializeConnection();
		}
	}

	@Override
	public String toString() {
		return description();
	}

	@Override
	public String description() {
		if (messagingConnectionProvider == null) {
			return "StandardMessagingSessionProvider (unconfigured)";
		} else {
			return messagingConnectionProvider.description();
		}
	}
}
