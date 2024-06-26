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

import java.util.function.Consumer;

import com.braintribe.cfg.Configurable;
import com.braintribe.model.processing.rpc.commons.api.GmRpcException;
import com.braintribe.model.processing.rpc.commons.api.authorization.RpcClientAuthorizationContext;

/**
 * <p>A basic {@link RpcClientAuthorizationContext} implementation which 
 *    notifies authorization failures ({@code Throwable}(s)) to the receiver configured via
 *    {@link #setAuthorizationFailureListener(Consumer)}.
 * 
 */
public class GmWebRpcClientAuthorizationContext implements RpcClientAuthorizationContext<Throwable> {
	
	private int maxRetries;
	private Consumer<Throwable> authorizationFailureListener;

	@Configurable
	public void setMaxRetries(int maxRetries) {
		this.maxRetries = maxRetries;
	}

	@Configurable
	public void setAuthorizationFailureListener(Consumer<Throwable> authorizationFailureListener) {
		this.authorizationFailureListener = authorizationFailureListener;
	}

	@Override
	public int getMaxRetries() {
		return maxRetries;
	}

	@Override
	public void onAuthorizationFailure(Throwable failureContext) {
		if (authorizationFailureListener != null) {
			try {
				authorizationFailureListener.accept(failureContext);
			} catch (Exception e) {
				throw new GmRpcException("Failed to notify configured listener about authorization failure. "
						+ "Listener: [ "+authorizationFailureListener+" ]. Failure: [ "+failureContext+" ]", e);
			}
		}
	}

}
