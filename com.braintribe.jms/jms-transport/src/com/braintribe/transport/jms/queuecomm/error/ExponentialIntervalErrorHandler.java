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
package com.braintribe.transport.jms.queuecomm.error;

import com.braintribe.transport.jms.IServer;
import com.braintribe.transport.jms.queuecomm.IQueueContext;

public class ExponentialIntervalErrorHandler extends com.braintribe.execution.errorhandler.ExponentialIntervalErrorHandler<IQueueContext> {

	@Override
	protected String getLoggingInformation(IQueueContext queueContext) {
		if (queueContext == null) {
			return "Unknown Queue";
		}
		String queueName = queueContext.getQueueName();
		if ((queueName == null) || (queueName.trim().length() == 0)) {
			return "Unknown Queue Name";
		}
		return queueName;
	}


	@Override
	protected String getContextKey(IQueueContext context) {
		if (context == null) {
			logger.debug("No IQueueContext available");
			return null;
		}
		IServer server = context.getServer();
		if (server == null) {
			logger.debug("Server unknown: using queue name "+context.getQueueName());
			return context.getQueueName();
		}
		String key = server.toString();
		return key;
	}

	
}
