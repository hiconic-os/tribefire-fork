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
package com.braintribe.gwt.customizationui.client.security;

import java.util.function.Predicate;

import com.braintribe.exception.AuthorizationException;
import com.braintribe.gwt.ioc.client.Configurable;

/**
 * This filter will match against exceptions SessionTimeoutExceptions. 
 * @author michel.docouto
 *
 */
public class SessionTimeoutExceptionFilter implements Predicate<Throwable> {
	
	private String sessionTimeoutExceptionString = "biz.i2z.modules.sessionmanager.SessionTimeoutException";
	
	/**
	 * Configures the String name of the SessionTimeoutException. Defaults to "biz.i2z.modules.sessionmanager.SessionTimeoutException".
	 */
	@Configurable
	public void setSessionTimeoutExceptionString(String sessionTimeoutExceptionString) {
		this.sessionTimeoutExceptionString = sessionTimeoutExceptionString;
	}

	@Override
	public boolean test(Throwable exception) {
		if (exception != null)
			if (exception.getMessage() != null && exception.getMessage().contains(sessionTimeoutExceptionString))
				return true;
			else if (exception instanceof AuthorizationException)
				return true;
		return false;
	}

}
