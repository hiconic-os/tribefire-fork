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
package com.braintribe.gwt.gme.constellation.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.workbench.KnownWorkenchPerspective;
import com.braintribe.model.workbench.WorkbenchPerspective;

/**
 * Loader used for loading folders for the Home constellation.
 * @author michel.docouto
 *
 */
public class HomeFoldersLoader implements Supplier<Future<List<?>>> {
	
	private ModelEnvironmentDrivenGmSession gmSession;
	private String currentAccessId;
	private Future<List<?>> future;
	private Function<KnownWorkenchPerspective, Future<WorkbenchPerspective>> workbenchPerspectiveFutureProvider;
	
	/**
	 * Configures the required provider used for providing the {@link WorkbenchPerspective} for the home folders.
	 */
	@Required
	public void setWorkbenchPerspectiveFutureProvider(Function<KnownWorkenchPerspective, Future<WorkbenchPerspective>> workbenchPerspectiveFuture) {
		this.workbenchPerspectiveFutureProvider = workbenchPerspectiveFuture;
	}
	
	/**
	 * Configures the required workbench session used for checking what is the current data access id.
	 */
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	@Override
	public Future<List<?>> get() throws RuntimeException {
		if (gmSession.getModelEnvironment() == null)
			return new Future<List<?>>(new ArrayList<>());
		
		String dataAccessId = gmSession.getModelEnvironment().getDataAccessId();
		if (future != null && currentAccessId != null && currentAccessId.equals(dataAccessId))
			return future;
		
		future = new Future<>();
		currentAccessId = dataAccessId;
		
		if (currentAccessId == null) {
			future.onSuccess(Collections.emptyList());
			return future;
		}
		
		workbenchPerspectiveFutureProvider.apply(KnownWorkenchPerspective.homeFolder) //
				.andThen(result -> {
					if (result == null) {
						future.onSuccess(null);
						return;
					}

					gmSession.merge().adoptUnexposed(true).suspendHistory(true).doFor(result.getFolders(),
							com.braintribe.processing.async.api.AsyncCallback.of(future::onSuccess, future::onFailure));
				}).onError(future::onFailure);
		
		return future;
	}

}
