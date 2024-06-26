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

import java.util.Iterator;

import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.query.eval.set.HasMoreAwareSet;
import com.braintribe.model.processing.query.eval.set.base.TransientGeneratorEvalTupleSet;
import com.braintribe.model.processing.smartquery.eval.api.SmartQueryEvaluationContext;
import com.braintribe.model.smartqueryplan.ScalarMapping;
import com.braintribe.model.smartqueryplan.set.StaticTuple;

public class EvalStaticTuple extends TransientGeneratorEvalTupleSet implements HasMoreAwareSet {

	protected final StaticTuple staticTuple;
	protected final SmartQueryEvaluationContext smartContext;

	public EvalStaticTuple(StaticTuple staticTuple, SmartQueryEvaluationContext context) {
		super(context);

		this.staticTuple = staticTuple;
		this.smartContext = context;

	}

	@Override
	public boolean hasMore() {
		return false;
	}

	@Override
	public Iterator<Tuple> iterator() {
		return new EvalTupleIterator();
	}

	private class EvalTupleIterator implements Iterator<Tuple> {
		boolean hasNext = !staticTuple.getScalarMappings().isEmpty();

		@Override
		public boolean hasNext() {
			return hasNext;
		}

		@Override
		public Tuple next() {
			for (ScalarMapping mapping: staticTuple.getScalarMappings()) {
				Object resolvedValue = context.resolveValue(null, mapping.getSourceValue());
				singletonTuple.setValueDirectly(mapping.getTupleComponentIndex(), resolvedValue);
			}

			hasNext = false;

			return singletonTuple;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove a tuple from a tuple set!");
		}
	}
}
