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
package com.braintribe.model.processing.smart.query.planner.core.order;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static java.util.Collections.singletonList;

import java.util.Collections;
import java.util.List;

import com.braintribe.model.processing.query.planner.core.order.OrderByProcessor;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeGroup.SingleAccessGroup;
import com.braintribe.model.query.CascadedOrdering;
import com.braintribe.model.query.Ordering;
import com.braintribe.model.query.SimpleOrdering;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.queryplan.value.Value;

/**
 * 
 */
public class SmartOrderByProcessor extends OrderByProcessor {

	protected final SmartQueryPlannerContext context;

	public static TupleSet applyOrdering(SmartQueryPlannerContext context, Ordering ordering, TupleSet operand) {
		return new SmartOrderByProcessor(context).applyOrdering(ordering, operand);
	}

	protected SmartOrderByProcessor(SmartQueryPlannerContext context) {
		super(context);

		this.context = context;
	}

	public TupleSet applyOrdering(Ordering ordering, TupleSet operand) {
		List<Value> groupValues = findGroupValues();
		List<SimpleOrdering> orderings = findOrderings(ordering, groupValues);

		return super.applyOrdering(orderings, groupValues, operand);
	}

	private List<Value> findGroupValues() {
		SingleAccessGroup orderedGroup = context.orderAndPaging().getOrderedGroup();
		if (orderedGroup == null)
			return Collections.emptyList();

		List<SimpleOrdering> delegatedOrderings = orderedGroup.orderAndPagination.delegatableOrderings;
		List<Value> result = newList(delegatedOrderings.size());

		for (SimpleOrdering ordering : delegatedOrderings)
			result.add(context.convertOperand(ordering.getOrderBy()));

		return result;
	}

	private List<SimpleOrdering> findOrderings(Ordering ordering, List<Value> groupValues) {
		if (ordering instanceof CascadedOrdering)
			return findNotYetAppliedOrderings((CascadedOrdering) ordering, groupValues);

		if (ordering instanceof SimpleOrdering) {
			if (groupValues.isEmpty())
				return singletonList((SimpleOrdering) ordering);

			throw new SmartQueryPlannerException("SimpleOrdering not expected, we must have at least 2 different order criteria.");
		}

		throw new SmartQueryPlannerException("Unknown ordering type. Ordering: " + ordering);
	}

	private List<SimpleOrdering> findNotYetAppliedOrderings(CascadedOrdering ordering, List<Value> groupValues) {
		return ordering.getOrderings().subList(groupValues.size(), ordering.getOrderings().size());
	}

}
