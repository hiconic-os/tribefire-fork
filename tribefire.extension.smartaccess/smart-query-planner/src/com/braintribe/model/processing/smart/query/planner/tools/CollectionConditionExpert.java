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

import static com.braintribe.model.processing.smart.query.planner.tools.SmartQueryPlannerTools.newMultiMap;

import java.util.Collection;

import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.conditions.AbstractJunction;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.Negation;
import com.braintribe.model.query.conditions.ValueComparison;
import com.braintribe.utils.collection.api.MultiMap;

/**
 * 
 */
public class CollectionConditionExpert {

	private final SmartQueryPlannerContext context;

	public CollectionConditionExpert(SmartQueryPlannerContext context) {
		this.context = context;
	}

	public MultiMap<Source, ValueComparison> findCollectionConditions(Collection<Condition> conditions) {
		MultiMap<Source, ValueComparison> result = newMultiMap();
		findFor(conditions, result);

		return result;
	}

	private void findFor(Collection<Condition> conditions, MultiMap<Source, ValueComparison> map) {
		for (Condition c: conditions)
			findFor(c, map);
	}

	private void findFor(Condition condition, MultiMap<Source, ValueComparison> map) {
		switch (condition.conditionType()) {
			case conjunction:
			case disjunction:
				findFor(((AbstractJunction) condition).getOperands(), map);
				return;
			case negation:
				findFor(((Negation) condition).getOperand(), map);
				return;
			case valueComparison:
				findFor((ValueComparison) condition, map);
				return;
			default:
				return;
		}
	}

	private void findFor(ValueComparison comparison, MultiMap<Source, ValueComparison> map) {
		switch (comparison.getOperator()) {
			case in:
				addSourceIfPossible(comparison, comparison.getRightOperand(), map);
				return;
			case contains:
				addSourceIfPossible(comparison, comparison.getLeftOperand(), map);
				return;
			default:
				return;
		}
	}

	private void addSourceIfPossible(ValueComparison comparison, Object operand, MultiMap<Source, ValueComparison> map) {
		if (!isPropertyOperand(operand))
			return;

		PropertyOperand po = (PropertyOperand) operand;

		map.put(po.getSource(), comparison);
	}

	private boolean isPropertyOperand(Object operand) {
		return operand instanceof PropertyOperand && !context.isEvaluationExclude(operand);
	}

}
