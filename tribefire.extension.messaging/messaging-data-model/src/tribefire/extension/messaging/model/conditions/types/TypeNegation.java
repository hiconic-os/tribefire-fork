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
package tribefire.extension.messaging.model.conditions.types;

import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import tribefire.extension.messaging.model.conditions.Condition;
import tribefire.extension.messaging.model.conditions.Negation;

public interface TypeNegation extends TypeCondition, Negation {

	EntityType<TypeNegation> T = EntityTypes.T(TypeNegation.class);

	String operand = "operand";

	@Name("Operand")
	@Mandatory
	TypeCondition getOperand();
	void setOperand(TypeCondition operand);

	@Override
	default <C extends Condition> C operand() {
		return (C) getOperand();
	}

}
