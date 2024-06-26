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
package tribefire.extension.messaging.connector.logging;

import java.util.Collections;
import java.util.List;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.logging.LogLevel;

import tribefire.extension.messaging.connector.api.AbstractConsumerMessagingConnector;

/**
 *
 */
public class LoggingConsumerMessagingConnectorImpl extends AbstractConsumerMessagingConnector {

	private static final Logger logger = Logger.getLogger(LoggingConsumerMessagingConnectorImpl.class);

	private LogLevel logLevel;
	private boolean logTags;

	// -----------------------------------------------------------------------
	// LifecycleAware
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	// HEALTH
	// -----------------------------------------------------------------------

	@Override
	public CheckResultEntry actualHealth() {
		return null;
	}

	// -----------------------------------------------------------------------
	// CONSUME
	// -----------------------------------------------------------------------

	protected List<byte[]> consumerConsume() {
		// TODO: implement
		return Collections.emptyList();
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	@Configurable
	public void setLogTags(boolean logTags) {
		this.logTags = logTags;
	}

	@Override
	public String getExternalId() {
		return null; //TODO This is a stub
	}
}
