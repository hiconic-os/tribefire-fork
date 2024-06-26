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
package com.braintribe.model.processing.securityservice.impl;

import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.securityservice.api.SecurityService;
import com.braintribe.model.processing.securityservice.api.exceptions.AuthenticationException;
import com.braintribe.model.processing.securityservice.api.exceptions.SecurityServiceException;
import com.braintribe.model.securityservice.LogoutSession;
import com.braintribe.model.securityservice.OpenUserSession;
import com.braintribe.model.securityservice.OpenUserSessionResponse;
import com.braintribe.model.securityservice.ValidateUserSession;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.usersession.UserSession;
import com.braintribe.processing.async.api.AsyncCallback;

public class EvaluatingSecurityService implements SecurityService {
	private final Evaluator<ServiceRequest> evaluator;
	private AsyncCallback<Object> callback;

	public EvaluatingSecurityService(Evaluator<ServiceRequest> evaluator) {
		super();
		this.evaluator = evaluator;
	}
	
	public EvaluatingSecurityService(Evaluator<ServiceRequest> evaluator, AsyncCallback<Object> callback) {
		super();
		this.evaluator = evaluator;
		this.callback = callback;
	}

	@Override
	public OpenUserSessionResponse openUserSession(OpenUserSession request) throws AuthenticationException {
		return request.eval(evaluator).get();
	}

	@Override
	public boolean isSessionValid(String sessionId) throws SecurityServiceException {
		ValidateUserSession req = ValidateUserSession.T.create();
		req.setSessionId(sessionId);
		EvalContext<? extends UserSession> evalContext = req.eval(evaluator);
		
		if (callback != null) {
			evalContext.get(callback);
			return true;
		}
		
		UserSession userSession = evalContext.get();
		return userSession != null && !userSession.getIsInvalidated();
	}

	@Override
	public boolean logout(String sessionId) throws SecurityServiceException {
		LogoutSession logout = LogoutSession.T.create();
		logout.setSessionId(sessionId);
		return logout.eval(evaluator).get(); 
	}
}
