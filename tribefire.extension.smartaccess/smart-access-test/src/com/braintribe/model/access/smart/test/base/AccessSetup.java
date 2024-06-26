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
package com.braintribe.model.access.smart.test.base;

import static com.braintribe.model.generic.typecondition.TypeConditions.isKind;
import static com.braintribe.model.generic.typecondition.TypeConditions.or;
import static com.braintribe.utils.lcd.CollectionTools2.asMap;

import java.util.Map;

import com.braintribe.model.access.smart.SmartAccess;
import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.generic.processing.pr.fluent.TC;
import com.braintribe.model.generic.typecondition.basic.TypeKind;
import com.braintribe.model.processing.query.smart.test.base.DelegateAccessSetup;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup;

/**
 * 
 */
public class AccessSetup extends DelegateAccessSetup {

	private SmartAccess smartAccess;

	// ######################################
	// ## . . . . . Smart Access . . . . . ##
	// ######################################

	public AccessSetup(SmartMappingSetup setup) {
		super(setup);
	}

	public SmartAccess getSmartAccess() {
		if (smartAccess == null) {
			smartAccess = configureSmartAccess();
		}
		return smartAccess;
	}

	private SmartAccess configureSmartAccess() {
		SmartAccess result = new SmartAccess();

		result.setAccessMapping(accessMapping());
		result.setSmartDenotation(setup.accessS);
		result.setMetaModel(setup.modelS);
		result.setDefaultTraversingCriteria(defaultSmartTraversingCriteria());

		return result;
	}

	private Map<IncrementalAccess, com.braintribe.model.access.IncrementalAccess> accessMapping() {
		return asMap(setup.accessA, getAccessA(), setup.accessB, getAccessB());
	}

	/** We stop on any entity- or collection- property */
	private Map<Class<? extends GenericEntity>, TraversingCriterion> defaultSmartTraversingCriteria() {
		// @formatter:off
		TraversingCriterion defaultTc = TC.create()
					.typeCondition(
							or(
								isKind(TypeKind.entityType),
								isKind(TypeKind.collectionType)
							)
					)
				.done();
		// @formatter:on

		return asMap(GenericEntity.class, defaultTc);
	}

}
