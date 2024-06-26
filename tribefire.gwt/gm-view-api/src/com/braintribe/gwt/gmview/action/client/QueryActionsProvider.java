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
package com.braintribe.gwt.gmview.action.client;

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.processing.pr.fluent.TC;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;
import com.braintribe.model.workbench.QueryAction;
import com.braintribe.processing.async.api.AsyncCallback;


/**
 * Provider used for providing a list of {@link QueryAction}s.
 * @author michel.docouto
 *
 */
public class QueryActionsProvider implements Supplier<Future<List<QueryAction>>> {
	
	private ModelEnvironmentDrivenGmSession gmSession;
	
	/**
	 * Configures the required session used for looking for {@link QueryAction}s.
	 */
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Future<List<QueryAction>> get() {
		Future<List<QueryAction>> future = new Future<>();
		
		if (gmSession.getModelEnvironment().getDataAccessId() == null) {
			future.onSuccess(null);
			return future;
		}
		
		gmSession.query().entities(prepareEntityQuery()).result(AsyncCallback.of( //
				entityQueryResultConvenience -> {
					if (entityQueryResultConvenience == null) {
						future.onSuccess(null);
						return;
					}

					try {
						EntityQueryResult result = entityQueryResultConvenience.result();
						if (result != null) {
							future.onSuccess((List) result.getEntities());
							return;
						}
					} catch (GmSessionException e) {
						future.onFailure(e);
					}

					future.onSuccess(null);
				}, future::onFailure));
		
		return future;
	}
	
	private EntityQuery prepareEntityQuery() {
		return EntityQueryBuilder.from(QueryAction.class).tc(TC.create().negation().joker().done()).done();
	}

}
