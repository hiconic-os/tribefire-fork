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
package com.braintribe.model.processing.smart.query.planner.tools;

import java.util.List;
import java.util.Map;

import com.braintribe.model.access.ModelAccessException;
import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.processing.query.fluent.SelectQueryBuilder;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.processing.smart.query.planner.structure.ModelExpert;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityMapping;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EntityPropertyMapping;
import com.braintribe.model.query.SelectQuery;

/**
 * 
 */
public class ReferenceConverter {

	private final SmartQueryPlannerContext smartQueryPlannerContext;
	private final Map<IncrementalAccess, com.braintribe.model.access.IncrementalAccess> accessMapping;
	private final ModelExpert modelExpert;

	public ReferenceConverter(SmartQueryPlannerContext smartQueryPlannerContext,
			Map<IncrementalAccess, com.braintribe.model.access.IncrementalAccess> accessMapping) {

		this.smartQueryPlannerContext = smartQueryPlannerContext;
		this.modelExpert = smartQueryPlannerContext.modelExpert();
		this.accessMapping = accessMapping;
	}

	public PersistentEntityReference convertToDelegate(PersistentEntityReference ref) {
		EntityMapping em = modelExpert.resolveEntityMapping(ref.getTypeSignature(), ref.getRefPartition(), null);
		Object id = toDelegateId(ref, em);

		return reference(em.getDelegateEntityType().getTypeSignature(), id, ref.getRefPartition());
	}

	private Object toDelegateId(EntityReference ref, EntityMapping em) {
		EntityPropertyMapping idEpm = modelExpert.resolveEntityPropertyMapping(em.getSmartEntityType(), em.getAccess(), GenericEntity.id, em.getUseCase());

		return resolveDelegatePropertyValue(em, idEpm, ref, GenericEntity.id);
	}

	private Object resolveDelegatePropertyValue(EntityMapping em, EntityPropertyMapping idEpm, EntityReference smartReference,
			String delegateProperty) {

		Object delegateIdValue = smartQueryPlannerContext.convertToDelegateValue(smartReference.getRefId(), getIdEpm(em).getConversion());

		if (idEpm.isDelegatePropertyId())
			return delegateIdValue;

		SelectQuery selectQuery = new SelectQueryBuilder().from(em.getDelegateEntityType().getTypeSignature(), "s").select()
				.property("s", delegateProperty).where().property("s", idEpm.getDelegatePropertyName()).eq().value(delegateIdValue).done();

		com.braintribe.model.access.IncrementalAccess access = accessMapping.get(em.getAccess());
		try {
			List<Object> results = access.query(selectQuery).getResults();

			switch (results.size()) {
				case 1:
					return results.get(0);
				case 0:
					throw new SmartQueryPlannerException("No value found for delegte property '" + delegateProperty +
							"' of smart entity '" + toString(smartReference));
				default:
					throw new SmartQueryPlannerException("Somehow more than one value was found for delegate property '" +
							delegateProperty + "' of smart entity '" + toString(smartReference));
			}

		} catch (ModelAccessException e) {
			throw new SmartQueryPlannerException("Error while evaluating query to resolve delegate property '" + delegateProperty +
					"' of smart entity: " + smartReference.getTypeSignature(), e);
		}
	}

	private String toString(EntityReference ref) {
		return ref.getTypeSignature() + "[" + ref.getRefId() + "]";
	}

	/**
	 * @return {@link EntityPropertyMapping} corresponding to the id property of a smart entity represented by it's
	 *         {@link EntityMapping}.
	 */
	private EntityPropertyMapping getIdEpm(EntityMapping em) {
		return modelExpert.resolveEntityPropertyMapping(em.getSmartEntityType(), em.getAccess(), GenericEntity.id, em.getUseCase());
	}

	private PersistentEntityReference reference(String typeSignature, Object id, String partition) {
		PersistentEntityReference result = PersistentEntityReference.T.createPlain();
		result.setTypeSignature(typeSignature);
		result.setRefId(id);
		result.setRefPartition(partition);

		return result;
	}

}
