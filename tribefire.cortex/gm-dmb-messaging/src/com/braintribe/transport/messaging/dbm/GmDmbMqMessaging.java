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

import com.braintribe.transport.messaging.api.Messaging;
import com.braintribe.transport.messaging.api.MessagingContext;

/**
 * <p>
 * Dynamic MBean-based implementation of the generic model messaging system.
 * 
 * @see Messaging
 */
public class GmDmbMqMessaging implements Messaging<com.braintribe.model.messaging.dmb.GmDmbMqMessaging> {

	@Override
	public GmDmbMqConnectionProvider createConnectionProvider(com.braintribe.model.messaging.dmb.GmDmbMqMessaging connectionProvider, MessagingContext context) {
		
		GmDmbMqConnectionProvider gmDmbMqConnectionProvider = new GmDmbMqConnectionProvider();
		gmDmbMqConnectionProvider.setConnectionConfiguration(connectionProvider);
		gmDmbMqConnectionProvider.setMessagingContext(context);
		
		return gmDmbMqConnectionProvider;
	}

}
