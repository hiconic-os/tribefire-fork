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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.generic.pr.criteria.TypeConditionCriterion;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.workbench.InstantiationAction;
import com.google.gwt.core.client.Scheduler;

/**
 * Expert which will return a list of {@link InstantiationAction}s for the given {@link EntityType}.
 * @author michel.couto
 *
 */
public class EntityTypeInstantiationActionsProvider implements Function<EntityType<?>, Future<List<InstantiationAction>>> {
	
	private Supplier<Future<List<InstantiationAction>>> instantiationActionsSupplier;
	private ManagedGmSession gmSession;
	private Future<List<InstantiationAction>> future;
	
	/**
	 * Configures the required {@link Supplier} of {@link InstantiationAction}.
	 */
	@Required
	public void setInstantiationActionsSupplier(Supplier<Future<List<InstantiationAction>>> instantiationActionsSupplier) {
		this.instantiationActionsSupplier = instantiationActionsSupplier;
	}
	
	/**
	 * Configures the session.
	 */
	@Required
	public void setGmSession(ManagedGmSession gmSession) {
		this.gmSession = gmSession;
	}

	@Override
	public Future<List<InstantiationAction>> apply(EntityType<?> entityType) {
		Future<List<InstantiationAction>> resultFuture = new Future<>();
		
		if (future == null)
			future = instantiationActionsSupplier.get();
		
		future.andThen(instantiationActions -> {
			// Using this to make sure this is async. After the first use, the instantiationActionsProvider returns
			// directly the result.
			Scheduler.get().scheduleDeferred(() -> {
				List<InstantiationAction> result = new ArrayList<>();
				if (instantiationActions != null) {
					for (InstantiationAction instantiationAction : instantiationActions) {
						TraversingCriterion inplaceContextCriterion = instantiationAction.getInplaceContextCriterion();
						if (inplaceContextCriterion instanceof TypeConditionCriterion) {
							GmType gmType = gmSession.getModelAccessory().getOracle().<GmType> findGmType(entityType.getTypeSignature());
							if (gmType != null && ((TypeConditionCriterion) inplaceContextCriterion).getTypeCondition().matches(gmType))
								result.add(instantiationAction);
						}
					}
				}

				resultFuture.onSuccess(result);
			});
		}).onError(resultFuture::onFailure);
		
		return resultFuture;
	}

}
