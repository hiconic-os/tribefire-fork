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
package com.braintribe.model.processing.smart.query.planner.context;

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.List;

import com.braintribe.model.accessdeployment.smart.meta.ConstantPropertyAssignment;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.query.eval.context.ConditionEvaluationTools;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.conditions.ValueComparison;

/**
 * This evaluator is used to evaluate a {@link ValueComparison} condition, in case it's possible to simplify the
 * condition based on the information available to the planner.
 * 
 * Some examples are conditions on {@link ConstantPropertyAssignment constant} properties, partition or comparisons with
 * {@link EntityReference entity references}, which also carry the partition information.
 * 
 * @author peter.gazdik
 */
/* package */ class StaticValueComparisonEvaluator<L, R, T> {

	private final Operator operator;
	private final Iterable<L> leftValues;
	private final Iterable<R> rightValues;
	private final boolean swapOrder;
	private final SimpleValueResolver<L, R, T> valueResolver;

	/* package */ static <L, R, T> List<T> evaluateVc(Operator operator, Iterable<L> leftValues, Iterable<R> rightValues, boolean swapOrder,
			SimpleValueResolver<L, R, T> valueResolver) {

		return new StaticValueComparisonEvaluator<L, R, T>(operator, leftValues, rightValues, swapOrder, valueResolver).evaluate();
	}

	// #########################################
	// ## . . . . . . Constructor . . . . . . ##
	// #########################################

	private StaticValueComparisonEvaluator(Operator operator, Iterable<L> leftValues, Iterable<R> rightValues, boolean swapOrder,
			SimpleValueResolver<L, R, T> valueResolver) {

		this.operator = operator;
		this.leftValues = leftValues;
		this.rightValues = rightValues;
		this.swapOrder = swapOrder;
		this.valueResolver = valueResolver;
	}

	// #########################################
	// ## . . . . . Implementation . . . . . .##
	// #########################################

	private List<T> evaluate() {
		List<T> result = newList();

		for (L left : leftValues)
			for (R right : rightValues)
				if (evaluateOperator(valueResolver.resolveLeft(left), valueResolver.resolveRight(right)))
					result.add(valueResolver.newEntry(left, right));

		return result;
	}

	private boolean evaluateOperator(Object left, Object right) {
		if (swapOrder) {
			Object tmp = left;
			left = right;
			right = tmp;
		}

		switch (operator) {
			case contains:
				return ConditionEvaluationTools.contains(left, right, Operator.contains);
			case equal:
				return ConditionEvaluationTools.compare(left, right) == 0;
			case greater:
				return ConditionEvaluationTools.compare(left, right) > 0;
			case greaterOrEqual:
				return ConditionEvaluationTools.compare(left, right) >= 0;
			case ilike:
				return ConditionEvaluationTools.ilike((String) left, (String) right);
			case in:
				return ConditionEvaluationTools.contains(right, left, Operator.in);
			case less:
				return ConditionEvaluationTools.compare(left, right) < 0;
			case lessOrEqual:
				return ConditionEvaluationTools.compare(left, right) <= 0;
			case like:
				return ConditionEvaluationTools.like((String) left, (String) right);
			case notEqual:
				return ConditionEvaluationTools.compare(left, right) != 0;
		}

		throw new RuntimeException("Unsupported operator " + operator);
	}

	public interface SimpleValueResolver<L, R, T> {

		Object resolveLeft(L left);

		Object resolveRight(R right);

		T newEntry(L left, R right);

	}

}
