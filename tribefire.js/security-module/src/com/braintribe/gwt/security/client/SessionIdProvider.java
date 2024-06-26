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

import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;


/**
 * This class can be used in the case a sessionId will be needed but
 * a decoupling from SessionService seems to be better
 * @author michel.docouto
 *
 */
public class SessionIdProvider implements Supplier<String> {
	private SecurityService securityService;
	private boolean exceptionOnMissingSession = false;
	
	@Configurable
	public void setExceptionOnMissingSession(boolean exceptionOnMissingSession) {
		this.exceptionOnMissingSession = exceptionOnMissingSession;
	}
	
	/**
	 * The security service will be used to retrieve the session id
	 */
	@Configurable @Required
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@Override
	public String get() throws RuntimeException {
		Session session;
		try {
			session = securityService.getSession();
		} catch (SecurityServiceException e) {
			throw new RuntimeException("error while accessing session", e);
		}
		
		if (session != null) return session.getId();
		else {
			if (exceptionOnMissingSession)
				throw new RuntimeException("missing session");
			else return "";
		}
	}

}
