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
package com.braintribe.gwt.security.mock.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.security.client.AbstractSecurityService;
import com.braintribe.gwt.security.client.SecurityServiceException;
import com.braintribe.gwt.security.client.Session;
import com.braintribe.gwt.security.client.SessionScope;
import com.braintribe.model.securityservice.credentials.Credentials;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a mock implementation of the SecurityService
 *
 */
public class MockSecurityService extends AbstractSecurityService {
	
	@Override
	public Future<Boolean> loginWithExistingSession(String username, String sessionId) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Future<Boolean> loginSSO() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void login(String username, String password,
			AsyncCallback<Session> asyncCallback) {
		if (session != null) {
			asyncCallback.onFailure(new SecurityServiceException("a session already exists"));
			return;
		}
		session = new Session(username, Long.toHexString(System.currentTimeMillis()));
		session.setRoles(new HashSet<String>(Arrays.asList("admin")));
		onSessionCreated();
		asyncCallback.onSuccess(session);
	}
	
	@Override
	public Future<Boolean> login(Credentials credentials) {
		Future<Boolean> future = new Future<Boolean>();
		future.onFailure(new UnsupportedOperationException());
		return future;
	}

	@Override
	public void logout(AsyncCallback<Boolean> asyncCallback) {
		logout(false, asyncCallback);
	}
	
	@Override
	public void logout(boolean silent, AsyncCallback<Boolean> asyncCallback) {
		session = null;
		asyncCallback.onSuccess(true);
		onSessionClosed(silent);
	}
	
	protected void onSessionCreated() {
		createSessionScope();
		fireSessionCreated();
	}
	
	protected void onSessionClosed(boolean silent) {
		if (!silent)
			fireSessionClosed();
		removeSessionScope();
	}
		
	private void createSessionScope() {
		SessionScope scope = new SessionScope();
		SessionScope.scopeManager.openAndPushScope(scope);
	}

	private void removeSessionScope() {
		SessionScope.scopeManager.closeAndPopScope();
	}
	
	public Supplier<String> getSessionProvider() {
		return new Supplier<String>() {
			@Override
			public String get() throws RuntimeException {
				return session.getId();
			}
		};
	}

	@Override
	public Future<Boolean> changePassword(String oldPassword, String newPassword) {
		return new Future<Boolean>(true);
	}
	
	@Override
	public Future<Void> loginWithExistingSession(Session session) {
		Future<Void> future = new Future<Void>();
		future.onFailure(new UnsupportedOperationException());
		return future;
	}
	
	@Override
	public Future<Boolean> recheckUserPassword(String password) {
		return new Future<Boolean>(true);
	}
}
