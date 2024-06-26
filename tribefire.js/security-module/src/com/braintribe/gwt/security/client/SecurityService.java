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
package com.braintribe.gwt.security.client;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.model.securityservice.credentials.Credentials;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This interface show us all methods needed for the SecurityService
 *
 */
public interface SecurityService {
	void addSessionListener(SessionListener sessionListener);
	void removeSessionListener(SessionListener sessionListener);
	Future<Boolean> loginWithExistingSession(String username, String sessionId);
	Future<Boolean> loginSSO();
	Future<Void> loginWithExistingSession(Session session);
	void login(String username, String password, AsyncCallback<Session> asyncCallback);
	void logout(AsyncCallback<Boolean> asyncCallback);
	void logout(boolean silent, AsyncCallback<Boolean> asyncCallback);
	Session getSession() throws SecurityServiceException;
	Future<Boolean> changePassword(String oldPassword, String newPassword);
	
	/**
	 * Rechecks the current user password.
	 */
	Future<Boolean> recheckUserPassword(String password);
	Future<Boolean> login(Credentials credentials);
}
