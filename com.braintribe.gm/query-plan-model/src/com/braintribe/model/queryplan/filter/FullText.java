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
package com.braintribe.model.queryplan.filter;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;



/**
 * <ul>
 * <li>leftOperand: entity</li>
 * <li>rightOperand: string</li>
 * </ul>
 * <p>
 * Checks if any of the properties of the entity contains the <tt>rightOperand</tt> as a substring.
 */

public interface FullText extends ValueComparison {

	EntityType<FullText> T = EntityTypes.T(FullText.class);

	@Override
	default ConditionType conditionType() {
		return ConditionType.fullText;
	}

}
