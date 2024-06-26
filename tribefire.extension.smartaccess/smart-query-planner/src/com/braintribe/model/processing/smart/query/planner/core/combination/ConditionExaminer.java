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
package com.braintribe.model.processing.smart.query.planner.core.combination;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.List;
import java.util.Map;

import com.braintribe.model.processing.query.planner.RuntimeQueryPlannerException;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.query.conditions.AbstractJunction;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.Negation;

/**
 * 
 */
class ConditionExaminer {

	private final ConditionNodeResolver conditionNodeResolver;
	private final Map<Condition, ConditionExaminationDescription> map = newMap();

	public static Map<Condition, ConditionExaminationDescription> examine(SmartQueryPlannerContext context) {
		return new ConditionExaminer(context).examine(context.conjunctionOperands());
	}

	// ###################################
	// ## . . . . Constructor . . . . . ##
	// ###################################

	private ConditionExaminer(SmartQueryPlannerContext context) {
		this.conditionNodeResolver = new ConditionNodeResolver(context);
	}

	// ###################################
	// ## . . . . Implementation . . . .##
	// ###################################

	private Map<Condition, ConditionExaminationDescription> examine(List<Condition> conditions) {
		for (Condition c: conditions)
			examine(c);

		return map;
	}

	private ConditionExaminationDescription examine(Condition c) {
		switch (c.conditionType()) {
			case conjunction:
			case disjunction:
				return examineJunction((AbstractJunction) c);

			case negation:
				return examineNegation((Negation) c);

			case fulltextComparison:
			case valueComparison:
				ConditionExaminationDescription set = conditionNodeResolver.resolveNodesForCondition(c);
				map.put(c, set);

				return set;
		}

		throw new RuntimeQueryPlannerException("Unsupported condition: " + c + " of type: " + c.conditionType());
	}

	private ConditionExaminationDescription examineNegation(Negation c) {
		ConditionExaminationDescription result = examine(c.getOperand());
		map.put(c, result);

		return result;
	}

	private ConditionExaminationDescription examineJunction(AbstractJunction junction) {
		ConditionExaminationDescription result = new ConditionExaminationDescription();

		for (Condition c: junction.getOperands()) {
			ConditionExaminationDescription operandCed = examine(c);
			result.affectedSourceNodes.addAll(operandCed.affectedSourceNodes);
			result.delegateable = result.delegateable && operandCed.delegateable;
		}

		map.put(junction, result);

		return result;
	}

}
