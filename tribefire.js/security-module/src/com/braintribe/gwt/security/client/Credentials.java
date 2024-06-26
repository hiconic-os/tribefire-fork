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

import com.braintribe.model.securityservice.credentials.ExistingSessionCredentials;
import com.braintribe.model.securityservice.credentials.TokenCredentials;
import com.braintribe.model.securityservice.credentials.UserPasswordCredentials;

import jsinterop.annotations.JsMethod;

public interface Credentials {
	@JsMethod(namespace = SecurityModuleInteropNamespaces.credentials)
	@SuppressWarnings("unusable-by-js")
	static UserPasswordCredentials userPassword(String user, String password) {
		return UserPasswordCredentials.forUserName(user, password);
	}

	@JsMethod(namespace = SecurityModuleInteropNamespaces.credentials)
	@SuppressWarnings("unusable-by-js")
	static ExistingSessionCredentials sessionId(String sessionId) {
		return ExistingSessionCredentials.of(sessionId);
	}

	@JsMethod(namespace = SecurityModuleInteropNamespaces.credentials)
	@SuppressWarnings("unusable-by-js")
	static TokenCredentials token(String type, String token) {
		throw new UnsupportedOperationException();
	}
}
