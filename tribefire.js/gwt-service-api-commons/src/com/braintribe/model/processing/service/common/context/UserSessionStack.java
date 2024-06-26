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
package com.braintribe.model.processing.service.common.context;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.processing.service.api.ServiceRequestContextStack;
import com.braintribe.model.processing.service.api.aspect.IsAuthorizedAspect;
import com.braintribe.model.processing.service.api.aspect.RequestorSessionIdAspect;
import com.braintribe.model.processing.service.api.aspect.RequestorUserNameAspect;
import com.braintribe.model.usersession.UserSession;
import com.braintribe.provider.Hub;
import com.braintribe.utils.collection.api.MinimalStack;

public class UserSessionStack implements Hub<UserSession>, MinimalStack<UserSession> {
	private ServiceRequestContextStack contextStack;
	
	@Override
	public void push(UserSession session) {
		contextStack.push(contextStack.get().derive() //
			.set(RequestorSessionIdAspect.class, session.getSessionId()) // 
			.set(RequestorUserNameAspect.class, session.getUser().getName()) //
			.set(IsAuthorizedAspect.class, true) //
			.set(UserSessionAspect.class, session) //
			.build());
	}

	@Override
	public UserSession peek() {
		return contextStack.get().findAttribute(UserSessionAspect.class).orElse(null);
	}

	@Override
	public UserSession pop() {
		return contextStack.pop().findAttribute(UserSessionAspect.class).orElse(null);
	}
	
	/**
	 * TODO: use MinimalStack
	 * @deprecated use {@link #push(UserSession)} and {@link #pop()} instead 
	 */
	@Override
	@Deprecated
	public void accept(UserSession session) {
		if (session == null) {
			if (isEmpty()) // to be backwards compatible
				return;
			
			pop();
		} else {
			push(session);
		}
	}

	@Override
	public UserSession get() {
		return peek();
	}

	
	public ServiceRequestContextStack getContextStack() {
		return contextStack;
	}

	@Configurable
	@Required
	public void setContextStack(ServiceRequestContextStack contextStack) {
		this.contextStack = contextStack;
	}

	@Override
	public boolean isEmpty() {
		return contextStack.get() == null;
	}

}
