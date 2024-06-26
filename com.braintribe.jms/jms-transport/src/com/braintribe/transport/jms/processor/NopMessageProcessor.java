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
package com.braintribe.transport.jms.processor;

import com.braintribe.logging.Logger;
import com.braintribe.transport.jms.message.IMessageContext;


public class NopMessageProcessor extends AbstractProcessor {

	protected static Logger logger = Logger.getLogger(NopMessageProcessor.class);
	
	@Override
	public void processMessageContext(IMessageContext cc) throws Exception {
		logger.debug("No operation performed (NopMessageProcessor).");
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
}
