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
package com.braintribe.gwt.gme.constellation.client.expert;

import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.user.User;

/**
 * Provider responsible for providing the User with uncut properties in TC.
 * @author michel.docouto
 *
 */
public class UserProvider implements Supplier<Future<User>> {
	
	private Future<User> future;
	private Supplier<Future<User>> currentUserInformationFutureProvider;
	
	/**
	 * Configures the required provider for providing the current use info.
	 */
	@Required
	public void setCurrentUserInformationFutureProvider(Supplier<Future<User>> currentUserInformationFutureProvider) {
		this.currentUserInformationFutureProvider = currentUserInformationFutureProvider;
	}
	
	@Override
	public Future<User> get() throws RuntimeException {
		if (future != null)
			return future;
		
		future = new Future<>();
		
		currentUserInformationFutureProvider.get().andThen(future::onSuccess).onError(future::onFailure);
		
		return future;
	}

}
