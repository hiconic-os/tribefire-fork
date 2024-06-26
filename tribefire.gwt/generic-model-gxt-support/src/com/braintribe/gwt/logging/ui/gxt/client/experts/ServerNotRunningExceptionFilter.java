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
package com.braintribe.gwt.logging.ui.gxt.client.experts;

import java.util.function.Predicate;

import com.braintribe.gwt.ioc.client.Configurable;

/**
 * This filter will match against server not running exceptions. 
 * @author michel.docouto
 *
 */
public class ServerNotRunningExceptionFilter implements Predicate<Throwable> {

	private String serverNotRunningExceptionString = "java.net.ConnectException";
	
	/**
	 * Configures the String name of the server not running exception. Defaults to "java.net.ConnectException".
	 */
	@Configurable
	public void setServerNotRunningExceptionString(String serverNotRunningExceptionString) {
		this.serverNotRunningExceptionString = serverNotRunningExceptionString;
	}

	@Override
	public boolean test(Throwable exception) {
		if (exception != null && exception.getMessage() != null && exception.getMessage().contains(serverNotRunningExceptionString)) {
			return true;
		}
		return false;
	}

}
