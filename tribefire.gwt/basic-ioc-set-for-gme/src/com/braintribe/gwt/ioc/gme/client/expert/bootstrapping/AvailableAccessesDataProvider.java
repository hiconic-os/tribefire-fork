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

import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.bapi.AvailableAccesses;


/**
 * Provider responsible for providing a {@link Future} for the {@link AvailableAccesses}.
 * @author michel.docouto
 *
 */
public class AvailableAccessesDataProvider implements Supplier<Future<AvailableAccesses>> {
	
	private BootstrappingRequest bootstrappingRequest;
	private Future<AvailableAccesses> future;
	
	/**
	 * Configures the required request responsible for loading the {@link AvailableAccesses}.
	 */
	@Required
	public void setBootstrappingRequest(BootstrappingRequest bootstrappingRequest) {
		this.bootstrappingRequest = bootstrappingRequest;
	}

	@Override
	public Future<AvailableAccesses> get() throws RuntimeException {
		if (future == null) {
			future = new Future<>();
			bootstrappingRequest.addBootstrappingRequestListener(new BootstrappingRequestListener() {
				@Override
				public void onFailure(Throwable t) {
					future.onFailure(t);
				}
			});
			
			bootstrappingRequest.getAccessEval().get(future);
		}
		
		return future;
	}
	
	

}
