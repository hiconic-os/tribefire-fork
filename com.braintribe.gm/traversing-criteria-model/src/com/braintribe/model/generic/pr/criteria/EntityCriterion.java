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
package com.braintribe.model.generic.pr.criteria;


import com.braintribe.model.generic.pr.criteria.typematch.EntityTypeStrategy;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface EntityCriterion extends BasicCriterion {

	final EntityType<EntityCriterion> T = EntityTypes.T(EntityCriterion.class);

	/**
	 * @deprecated use {@link TypeConditionCriterion} instead
	 */
	@Deprecated
	void setStrategy(EntityTypeStrategy strategy);

	@Deprecated
	EntityTypeStrategy getStrategy();

	@Override
	default CriterionType criterionType() {
		return CriterionType.ENTITY;
	}

}
