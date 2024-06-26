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
package com.braintribe.model.query.smart.processing.eval.context;

import com.braintribe.model.processing.query.eval.api.EvalTupleSet;
import com.braintribe.model.processing.query.eval.api.RuntimeQueryEvaluationException;
import com.braintribe.model.processing.query.eval.context.TupleSetRepository;
import com.braintribe.model.processing.smartquery.eval.api.SmartQueryEvaluationContext;
import com.braintribe.model.query.smart.processing.eval.set.EvalDelegateQueryAsIs;
import com.braintribe.model.query.smart.processing.eval.set.EvalDelegateQueryJoin;
import com.braintribe.model.query.smart.processing.eval.set.EvalDelegateQuerySet;
import com.braintribe.model.query.smart.processing.eval.set.EvalOrderedConcatenation;
import com.braintribe.model.query.smart.processing.eval.set.EvalStaticTuple;
import com.braintribe.model.query.smart.processing.eval.set.EvalStaticTuples;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.queryplan.set.TupleSetType;
import com.braintribe.model.smartqueryplan.set.DelegateQueryAsIs;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;
import com.braintribe.model.smartqueryplan.set.OrderedConcatenation;
import com.braintribe.model.smartqueryplan.set.SmartTupleSet;
import com.braintribe.model.smartqueryplan.set.StaticTuple;
import com.braintribe.model.smartqueryplan.set.StaticTuples;

//
public class SmartTupleSetRepository extends TupleSetRepository {

	private final SmartQueryEvaluationContext smartContext;

	public SmartTupleSetRepository(SmartQueryEvaluationContext context) {
		super(context);
		this.smartContext = context;
	}

	@Override
	protected EvalTupleSet newEvalTupleSetFor(TupleSet tupleSet) {
		if (tupleSet.tupleSetType() == TupleSetType.extension) {
			return newSmartEvalTupleSetFor((SmartTupleSet) tupleSet);
		} else {
			return super.newEvalTupleSetFor(tupleSet);
		}
	}

	private EvalTupleSet newSmartEvalTupleSetFor(SmartTupleSet tupleSet) {
		switch (tupleSet.smartType()) {
			case delegateQueryAsIs:
				return new EvalDelegateQueryAsIs((DelegateQueryAsIs) tupleSet, smartContext);

			case delegateQuerySet:
				return new EvalDelegateQuerySet((DelegateQuerySet) tupleSet, smartContext);

			case delegateQueryJoin:
				return new EvalDelegateQueryJoin((DelegateQueryJoin) tupleSet, smartContext);

			case orderedConcatenation:
				return new EvalOrderedConcatenation((OrderedConcatenation) tupleSet, smartContext);

			case staticTuple:
				return new EvalStaticTuple((StaticTuple) tupleSet, smartContext);
				
			case staticTuples:
				return new EvalStaticTuples((StaticTuples) tupleSet, smartContext);
		}

		throw new RuntimeQueryEvaluationException("Unsupported SmartTupleSet: " + tupleSet + " of type: " + tupleSet.smartType());
	}

}
