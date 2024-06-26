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
package tribefire.extension.messaging.model.conditions;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import java.util.List;
import java.util.stream.Stream;

@Abstract
public interface Junction extends Condition {

	EntityType<Junction> T = EntityTypes.T(Junction.class);

	JunctionType junctionType();
	<C extends Condition> List<C> operands();

	@Override
	default boolean matches(GenericEntity entity) {
		List<Condition> operands = operands();
		Stream<Condition> operandsSteam = operands.stream();

		JunctionType junctionType = junctionType();
		return switch (junctionType) {
			case disjunction -> operandsSteam.anyMatch(o -> o.matches(entity));
			case conjunction -> operandsSteam.allMatch(o -> o.matches(entity));
			default -> throw new IllegalArgumentException("Unsupported JunctionType: "+junctionType);
		};
	}

}
