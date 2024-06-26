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
package com.braintribe.gwt.ioc.gme.client.expert.bootstrapping;

import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.bapi.AvailableAccesses;


/**
 * Provider responsible for providing a {@link Future} for the {@link ModelEnvironment} for a given accessId.
 * @author michel.docouto
 *
 */
public class ModelEnvironmentDataProvider implements Function<String, Future<ModelEnvironment>> {
	
	private BootstrappingRequest bootstrappingRequest;
	
	/**
	 * Configures the required request responsible for loading the {@link AvailableAccesses}.
	 */
	@Required
	public void setBootstrappingRequest(BootstrappingRequest bootstrappingRequest) {
		this.bootstrappingRequest = bootstrappingRequest;
	}

	@Override
	public Future<ModelEnvironment> apply(String accessId) throws RuntimeException {
		if (accessId == null) { //This was needed so the bootstrapping load happens
			bootstrappingRequest.getModelEnvironmentEval(accessId);
			return null;
		}
		
		final Future<ModelEnvironment> future = new Future<>();
		bootstrappingRequest.addBootstrappingRequestListener(new BootstrappingRequestListener() {
			@Override
			public void onFailure(Throwable t) {
				future.onFailure(t);
			}
		});
		bootstrappingRequest.getModelEnvironmentEval(accessId).get(future);
		
		return future;
	}

}
