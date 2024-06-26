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

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.Iterator;
import java.util.List;

import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.query.eval.set.HasMoreAwareSet;
import com.braintribe.model.processing.query.eval.set.base.AbstractEvalTupleSet;
import com.braintribe.model.processing.smartquery.eval.api.SmartQueryEvaluationContext;
import com.braintribe.model.smartqueryplan.set.StaticTuple;
import com.braintribe.model.smartqueryplan.set.StaticTuples;

public class EvalStaticTuples extends AbstractEvalTupleSet implements HasMoreAwareSet {

	protected final List<EvalStaticTuple> evalStaticTuples = newList();

	public EvalStaticTuples(StaticTuples staticTuples, SmartQueryEvaluationContext context) {
		super(context);

		for (StaticTuple staticTuple: staticTuples.getTuples()) {
			evalStaticTuples.add(new EvalStaticTuple(staticTuple, context));
		}
	}

	@Override
	public boolean hasMore() {
		return false;
	}

	@Override
	public Iterator<Tuple> iterator() {
		return new EvalTuplesIterator();
	}

	private class EvalTuplesIterator implements Iterator<Tuple> {
		Iterator<EvalStaticTuple> staticTupleIterator = evalStaticTuples.iterator();

		@Override
		public boolean hasNext() {
			return staticTupleIterator.hasNext();
		}

		@Override
		public Tuple next() {
			return staticTupleIterator.next().iterator().next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Cannot remove a tuple from a tuple set!");
		}
	}
}
