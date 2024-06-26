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

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.braintribe.model.processing.query.planner.builder.TupleSetBuilder;
import com.braintribe.model.queryplan.set.SortCriterion;
import com.braintribe.model.queryplan.set.StaticSet;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.smartqueryplan.SmartQueryPlan;
import com.braintribe.model.smartqueryplan.set.OrderedConcatenation;

/**
 * 
 */
public class SmartTupleSetBuilder {

	public static TupleSet concatenation(List<TupleSet> tupleSets, List<SortCriterion> sortCriteria, int tupleSize) {
		if (sortCriteria.isEmpty())
			return TupleSetBuilder.concatenation(tupleSets, tupleSize);

		Iterator<TupleSet> it = tupleSets.iterator();

		return concatenation(it.next(), it, sortCriteria, tupleSize);
	}

	private static TupleSet concatenation(TupleSet first, Iterator<TupleSet> it, List<SortCriterion> sortCriteria, int tupleSize) {
		if (!it.hasNext())
			return first;

		OrderedConcatenation result = OrderedConcatenation.T.createPlain();
		result.setSortCriteria(sortCriteria);
		result.setFirstOperand(first);
		result.setTupleSize(tupleSize);
		result.setSecondOperand(concatenation(it.next(), it, sortCriteria, tupleSize));

		return result;
	}

	public static SmartQueryPlan queryPlan(TupleSet tupleSet, int totalComponents) {
		SmartQueryPlan result = SmartQueryPlan.T.createPlain();
		result.setTotalComponentCount(totalComponents);
		result.setTupleSet(tupleSet);

		return result;
	}

	public static SmartQueryPlan emptyPlan() {
		StaticSet emptyTupleSet = StaticSet.T.createPlain();
		emptyTupleSet.setValues(Collections.emptySet());

		return queryPlan(emptyTupleSet, 0);
	}

}
