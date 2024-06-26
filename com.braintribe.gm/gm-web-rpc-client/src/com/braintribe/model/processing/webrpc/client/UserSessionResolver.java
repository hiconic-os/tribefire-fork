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
package com.braintribe.model.processing.webrpc.client;

import java.util.function.Function;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.common.lcd.function.GenericTransformer;
import com.braintribe.model.securityservice.OpenUserSession;
import com.braintribe.model.securityservice.OpenUserSessionResponse;
import com.braintribe.model.securityservice.credentials.Credentials;
import com.braintribe.model.usersession.UserSession;

/**
 * This serves as a lazy cache for a {@link UserSession}, where the initialization creates a {@link OpenUserSession} request based on configured
 * {@link #setCredentials(Credentials) credentials} and {@link #setDecryptingTransformer(GenericTransformer) decrypting transformer}, and evaluates it
 * via an "evaluator" given in the {@link #acquireUserSession(Function)} method.
 */
public class UserSessionResolver {

	private Credentials credentials;
	private GenericTransformer decryptingTransformer = GenericTransformer.identityTransformer;

	private volatile UserSession activeUserSession;

	private final String sessionIdLock = new String("acquireSessionIdLock");

	@Required
	public void setCredentials(Credentials credentials) {
		// TODO add check credentials is not null once we are confident the current client code doesn't do that.
		this.credentials = credentials;
	}

	/**
	 * Transformer to decrypt the configured {@link #setCredentials(Credentials) credentials}. This must of course be used when given credentials are
	 * encrypted.
	 * <p>
	 * Not that for security reasons it is preferable to configure encrypted credentials and this "decryptor", rather than configuring decrypted
	 * credentials, because otherwise we have sensitive data available in the heap which could under certain conditions be a vulnerability (e.g. if
	 * somebody gets hold of a heap dump).
	 */
	@Configurable
	public void setDecryptingTransformer(GenericTransformer decryptingTransformer) {
		this.decryptingTransformer = decryptingTransformer;
	}

	// #################################################################
	// ## . . . . . . . . Remote SessionId Management . . . . . . . . ##
	// #################################################################

	/**
	 * Purges the cached {@link UserSession} if it is the one given. If it's not most likely the client who is doing the clearing did not have the
	 * latest user session anyway, thus nothing has to be purged.
	 */
	public void clearUserSession(UserSession userSession) {
		if (activeUserSession == userSession)
			synchronized (sessionIdLock) {
				if (activeUserSession == userSession)
					activeUserSession = null;
			}
	}

	public UserSession acquireUserSession(Function<OpenUserSession, OpenUserSessionResponse> evaluator) {
		UserSession result = activeUserSession;
		if (result != null)
			return result;

		synchronized (sessionIdLock) {
			if (activeUserSession == null)
				activeUserSession = _refreshUserSession(evaluator);

			return activeUserSession;
		}
	}

	private UserSession _refreshUserSession(Function<OpenUserSession, OpenUserSessionResponse> evaluator) {
		OpenUserSession openSessionRequest = prepareOpenUserSessionRequest();

		OpenUserSessionResponse ousr = evaluator.apply(openSessionRequest);

		return ousr.getUserSession();
	}

	private OpenUserSession prepareOpenUserSessionRequest() {
		Credentials decryptedCredentials = decryptingTransformer.transform(credentials);

		OpenUserSession openUserSession = OpenUserSession.T.create();
		openUserSession.setCredentials(decryptedCredentials);
		return openUserSession;
	}

}
