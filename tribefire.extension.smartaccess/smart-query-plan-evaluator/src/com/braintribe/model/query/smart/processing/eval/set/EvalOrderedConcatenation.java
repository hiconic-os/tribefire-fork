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
package com.braintribe.model.query.smart.processing.eval.set;

import static com.braintribe.model.processing.query.eval.tools.QueryEvaluationTools.moreHas;

import java.util.Iterator;

import com.braintribe.model.processing.query.eval.api.EvalTupleSet;
import com.braintribe.model.processing.query.eval.api.QueryEvaluationContext;
import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.query.eval.set.HasMoreAwareSet;
import com.braintribe.model.processing.query.eval.set.base.AbstractEvalTupleSet;
import com.braintribe.model.processing.query.eval.tools.TupleComparator;
import com.braintribe.model.processing.query.eval.tuple.ArrayBasedTuple;
import com.braintribe.model.smartqueryplan.set.OrderedConcatenation;

/**
 * 
 */
public class EvalOrderedConcatenation extends AbstractEvalTupleSet implements HasMoreAwareSet {

	protected final EvalTupleSet firstOperand;
	protected final EvalTupleSet secondOperand;
	protected final int tupleSize;
	protected final TupleComparator comparator;

	public EvalOrderedConcatenation(OrderedConcatenation concatenation, QueryEvaluationContext context) {
		super(context);

		this.firstOperand = context.resolveTupleSet(concatenation.getFirstOperand());
		this.secondOperand = context.resolveTupleSet(concatenation.getSecondOperand());
		this.tupleSize = concatenation.getTupleSize();
		this.comparator = new TupleComparator(concatenation.getSortCriteria(), context);
	}

	@Override
	public boolean hasMore() {
		return moreHas(firstOperand) || moreHas(secondOperand);
	}

	@Override
	public Iterator<Tuple> iterator() {
		return new OrderedConcatenationIterator();
	}

	protected class OrderedConcatenationIterator extends AbstractTupleIterator {

		protected Iterator<Tuple> it1;
		protected Iterator<Tuple> it2;

		protected Tuple next1;
		protected Tuple next2;

		protected ArrayBasedTuple singletonTuple = new ArrayBasedTuple(totalComponentsCount());

		public OrderedConcatenationIterator() {
			it1 = firstOperand.iterator();
			it2 = secondOperand.iterator();

			next1 = next(it1);
			next2 = next(it2);

			next = singletonTuple;

			prepareNextValue();
		}

		@Override
		protected int totalComponentsCount() {
			return tupleSize == 0 ? super.totalComponentsCount() : tupleSize;
		}

		@Override
		protected void prepareNextValue() {
			Integer cmp = compareNexts();

			if (cmp == null) {
				next = null;

			} else if (cmp <= 0) {
				singletonTuple.acceptAllValuesFrom(next1);
				next1 = next(it1);
			} else {
				singletonTuple.acceptAllValuesFrom(next2);
				next2 = next(it2);
			}
		}

		private Integer compareNexts() {
			if (next1 != null) {
				return next2 != null ? comparator.compare(next1, next2) : -1;
			}

			return next2 == null ? null : 1;
		}

		private Tuple next(Iterator<Tuple> it) {
			return it.hasNext() ? it.next() : null;
		}

	}
}
