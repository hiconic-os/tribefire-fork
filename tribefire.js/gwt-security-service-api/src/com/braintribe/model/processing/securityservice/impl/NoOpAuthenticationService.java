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

import com.braintribe.model.processing.securityservice.api.AuthenticationService;
import com.braintribe.model.processing.securityservice.api.exceptions.AuthenticationException;
import com.braintribe.model.securityservice.UserAuthenticationResponse;
import com.braintribe.model.securityservice.credentials.Credentials;

public class NoOpAuthenticationService implements AuthenticationService {

	public static final NoOpAuthenticationService instance = new NoOpAuthenticationService();

	private NoOpAuthenticationService() {
	}

	@Override
	public UserAuthenticationResponse authenticate(Credentials credentials) throws AuthenticationException {
		return null;
	}

}
