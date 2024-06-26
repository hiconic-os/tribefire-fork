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
package tribefire.extension.elasticsearch.model.api.request.doc.conditions;

import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.elasticsearch.model.api.request.doc.Comparison;

public interface Negation extends Comparison {

	EntityType<Negation> T = EntityTypes.T(Negation.class);

	@Priority(0.8d)
	Condition getOperand();
	void setOperand(Condition operand);

	static Negation of(Condition condition) {
		Negation negation = Negation.T.create();
		negation.setOperand(condition);
		return negation;
	}
}
