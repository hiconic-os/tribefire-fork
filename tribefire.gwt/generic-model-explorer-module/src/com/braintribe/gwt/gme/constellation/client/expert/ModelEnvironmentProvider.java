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

import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.accessapi.ModelEnvironment;


/**
 * Provider which loads the {@link ModelEnvironment} for the given accessId.
 * @author michel.docouto
 *
 */
public class ModelEnvironmentProvider implements Function<String, Future<ModelEnvironment>> {
	
	private String currentAccessId;
	private Future<ModelEnvironment> future;
	private Function<String, Future<ModelEnvironment>> modelEnvironmentFutureProvider;
	
	/**
	 * Configures the required provider used for providing the {@link ModelEnvironment}.
	 */
	@Required
	public void setModelEnvironmentFutureProvider(Function<String, Future<ModelEnvironment>> modelEnvironmentFutureProvider) {
		this.modelEnvironmentFutureProvider = modelEnvironmentFutureProvider;
	}
	
	@Override
	public Future<ModelEnvironment> apply(String accessId) throws RuntimeException {
		if (future == null || currentAccessId == null || !currentAccessId.equals(accessId)) {
			currentAccessId = accessId;
			future = modelEnvironmentFutureProvider.apply(accessId);
		}
		
		return future;
	}
	
	public Future<ModelEnvironment> reload() {
		future = modelEnvironmentFutureProvider.apply(currentAccessId);
		return future;
	}

}
