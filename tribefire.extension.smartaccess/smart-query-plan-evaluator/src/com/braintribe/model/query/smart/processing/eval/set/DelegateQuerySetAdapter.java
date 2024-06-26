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

import java.util.Collection;
import java.util.List;

import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.smartquery.eval.api.SmartQueryEvaluationContext;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.Conjunction;
import com.braintribe.model.query.conditions.Disjunction;
import com.braintribe.model.query.conditions.ValueComparison;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;
import com.braintribe.model.smartqueryplan.set.OperandRestriction;

class DelegateQuerySetAdapter {

	private final SmartQueryEvaluationContext context;

	private DelegateQuerySet clonedDqs;
	private Disjunction clonedMainJoinDisjunction;
	private List<OperandRestriction> clonedJoinRestrictions;

	public static DelegateQuerySet adaptQuerySet(DelegateQueryJoin dqj, Collection<Tuple> mTuples, SmartQueryEvaluationContext context) {
		return new DelegateQuerySetAdapter(dqj, context).adapt(mTuples);
	}

	private DelegateQuerySetAdapter(DelegateQueryJoin originalDqj, SmartQueryEvaluationContext context) {
		this.context = context;

		clonePartsToBeChanged(originalDqj);
	}

	// TODO find a good way to deal with changing the original query - this makes changes to given DQJ
	private void clonePartsToBeChanged(DelegateQueryJoin dqj) {
		// BaseType baseType = GMF.getTypeReflection().getBaseType();

		// StandardCloningContext cc = new StandardCloningContext();
		// clonedDqs = (DelegateQuerySet) baseType.clone(cc, dqj.getQuerySet(), StrategyOnCriterionMatch.reference);
		// clonedMainJoinDisjunction = (Disjunction) baseType.clone(cc, dqj.getJoinDisjunction(), StrategyOnCriterionMatch.reference);
		// clonedJoinRestrictions = (List<OperandRestriction>) baseType.clone(cc, dqj.getJoinRestrictions(),
		// StrategyOnCriterionMatch.reference);
		// counter += cc.getAssociatedObjects().size();

		// clonedDqs.setDelegateAccess(dqj.getQuerySet().getDelegateAccess());

		clonedDqs = dqj.getQuerySet();
		clonedMainJoinDisjunction = dqj.getJoinDisjunction();
		clonedJoinRestrictions = dqj.getJoinRestrictions();
	}

	private DelegateQuerySet adapt(Collection<Tuple> mTuples) {
		List<Condition> adaptedJoinDisjunctionOperands = newList();

		for (Tuple materializedTuple: mTuples) {
			List<Condition> correlationsPerTuple = newList();

			for (OperandRestriction restriction: clonedJoinRestrictions) {
				Object materializedValue = context.resolveValue(materializedTuple, restriction.getMaterializedCorrelationValue());

				if (materializedValue == null) {
					correlationsPerTuple = null;
					break;
				}

				ValueComparison valueComparison = ValueComparison.T.create();

				valueComparison.setLeftOperand(restriction.getQueryOperand());
				valueComparison.setOperator(Operator.equal);
				valueComparison.setRightOperand(materializedValue);

				correlationsPerTuple.add(valueComparison);
			}

			if (correlationsPerTuple == null) {
				continue;
			}

			Conjunction adaptedJoinConjuntions = Conjunction.T.create();
			adaptedJoinConjuntions.setOperands(correlationsPerTuple);

			adaptedJoinDisjunctionOperands.add(adaptedJoinConjuntions);
		}

		if (adaptedJoinDisjunctionOperands.isEmpty()) {
			return null;
		}
		
		clonedMainJoinDisjunction.setOperands(adaptedJoinDisjunctionOperands);

		return clonedDqs;
	}

}
