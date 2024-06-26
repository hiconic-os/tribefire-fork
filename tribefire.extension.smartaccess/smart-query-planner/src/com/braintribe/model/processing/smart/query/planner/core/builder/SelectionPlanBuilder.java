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
package com.braintribe.model.processing.smart.query.planner.core.builder;

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.List;

import com.braintribe.model.processing.query.planner.builder.TupleSetBuilder;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.queryplan.set.AggregatingProjection;
import com.braintribe.model.queryplan.set.Projection;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.queryplan.value.Value;
import com.braintribe.model.queryplan.value.ValueType;

/**
 * @see TupleSetBuilder
 */
public class SelectionPlanBuilder {

	public static TupleSet projection(TupleSet tupleSet, List<Object> selections, SmartQueryPlannerContext context) {
		List<Value> values = newList();

		boolean aggregation = false;

		for (Object operand: selections) {
			Value convertOperand = context.convertOperand(operand);
			values.add(convertOperand);

			aggregation |= convertOperand.valueType() == ValueType.aggregateFunction;
		}

		Projection result = aggregation ? AggregatingProjection.T.createPlain() : Projection.T.createPlain();
		result.setOperand(tupleSet);
		result.setValues(values);

		return result;
	}

}
