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
package com.braintribe.model.processing.query.smart.eval.set.base;

import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.Arrays;
import java.util.List;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.processing.query.eval.set.base.TupleSetBuilder;
import com.braintribe.model.processing.query.planner.builder.ValueBuilder;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.ConditionType;
import com.braintribe.model.query.conditions.Conjunction;
import com.braintribe.model.query.conditions.Disjunction;
import com.braintribe.model.query.conditions.ValueComparison;
import com.braintribe.model.query.smart.processing.SmartQueryEvaluatorRuntimeException;
import com.braintribe.model.queryplan.set.SortCriterion;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.queryplan.value.Value;
import com.braintribe.model.smartqueryplan.ScalarMapping;
import com.braintribe.model.smartqueryplan.set.DelegateQueryAsIs;
import com.braintribe.model.smartqueryplan.set.DelegateQueryJoin;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;
import com.braintribe.model.smartqueryplan.set.OperandRestriction;
import com.braintribe.model.smartqueryplan.set.OrderedConcatenation;

public class SmartTupleSetBuilder extends TupleSetBuilder {

	public DelegateQueryAsIs delegateQueryAsIs(IncrementalAccess delegateAccess, SelectQuery delegateQuery) {
		DelegateQueryAsIs result = DelegateQueryAsIs.T.create();
		result.setDelegateAccess(delegateAccess);
		result.setDelegateQuery(delegateQuery);

		index = delegateQuery.getSelections().size();

		return result;
	}

	public DelegateQuerySet delegateQuerySet(IncrementalAccess delegateAccess, SelectQuery delegateQuery, List<ScalarMapping> scalarMappings) {
		return delegateQuerySet(delegateAccess, delegateQuery, scalarMappings, null);
	}

	public DelegateQuerySet delegateQuerySet(IncrementalAccess delegateAccess, SelectQuery delegateQuery, List<ScalarMapping> scalarMappings,
			Integer batchSize) {

		DelegateQuerySet result = DelegateQuerySet.T.create();
		result.setDelegateAccess(delegateAccess);
		result.setDelegateQuery(delegateQuery);
		result.setScalarMappings(scalarMappings);
		result.setBatchSize(batchSize);

		return result;
	}

	public DelegateQueryJoin delegateQueryJoin(TupleSet mSet, DelegateQuerySet qSet, Integer... correlationPositions) {
		DelegateQueryJoin dqj = DelegateQueryJoin.T.create();

		dqj.setMaterializedSet(mSet);
		dqj.setQuerySet(qSet);
		dqj.setJoinDisjunction(retrieveMainJoinDisjunction(retrieveMainConjunction(qSet.getDelegateQuery())));
		dqj.setJoinRestrictions(retrieveJoinRestrictions(dqj.getJoinDisjunction(), correlationPositions));

		return dqj;
	}

	private Conjunction retrieveMainConjunction(SelectQuery query) throws SmartQueryEvaluatorRuntimeException {
		Condition condition = query.getRestriction().getCondition();

		if ((condition == null) || (condition.conditionType() != ConditionType.conjunction))
			throw new SmartQueryEvaluatorRuntimeException("malformed delegateQuery, expected main condition to be of Conjunction type");

		return (Conjunction) condition;
	}

	private Disjunction retrieveMainJoinDisjunction(Conjunction conjunction) throws SmartQueryEvaluatorRuntimeException {
		List<Condition> conjunctionOperands = conjunction.getOperands();
		Condition lastCondition = conjunctionOperands.get(conjunctionOperands.size() - 1);

		if (lastCondition.conditionType() != ConditionType.disjunction)
			throw new SmartQueryEvaluatorRuntimeException("malformed delegateQuery, expected last condition of main conjunction to be a Disjunction");

		return (Disjunction) lastCondition;
	}

	public List<OperandRestriction> retrieveJoinRestrictions(Disjunction dqjJoinDisjunction, Integer... materializedCorrelationPositions)
			throws SmartQueryEvaluatorRuntimeException {

		if (dqjJoinDisjunction.getOperands().get(0).conditionType() != ConditionType.conjunction)
			throw new SmartQueryEvaluatorRuntimeException(
					"the size of materializedCorrelationPositions must equal to number of templatedJoinRestrictions : ");

		List<OperandRestriction> joinRestrictions = newList();

		Conjunction templatedJoinRestrictions = (Conjunction) dqjJoinDisjunction.getOperands().get(0);
		List<Condition> joinConditions = templatedJoinRestrictions.getOperands();

		for (int i = 0; i < joinConditions.size(); i++) {
			ValueComparison templatedValueComparison = (ValueComparison) joinConditions.get(i);

			OperandRestriction restriction = OperandRestriction.T.create();
			restriction.setQueryOperand((PropertyOperand) (templatedValueComparison).getLeftOperand());
			restriction.setMaterializedCorrelationValue(ValueBuilder.tupleComponent(materializedCorrelationPositions[i]));
			joinRestrictions.add(restriction);
		}

		return joinRestrictions;
	}

	public OrderedConcatenation orderedConcatenation(TupleSet firstOperand, DelegateQuerySet secondOperand, int tupleSize,
			SortCriterion... sortCriteria) {

		OrderedConcatenation result = OrderedConcatenation.T.create();
		result.setFirstOperand(firstOperand);
		result.setSecondOperand(secondOperand);
		result.setSortCriteria(Arrays.asList(sortCriteria));
		result.setTupleSize(tupleSize);

		return result;
	}

	public ScalarMapping scalarMapping(int tupleIndex) {
		return scalarMapping(TupleSetBuilder.tupleComponent(tupleIndex));
	}

	public ScalarMapping scalarMapping(int tupleIndex, int index) {
		return scalarMapping(TupleSetBuilder.tupleComponent(tupleIndex), index);
	}

	public ScalarMapping scalarMapping(Value value) {
		return scalarMapping(value, null);
	}

	public ScalarMapping scalarMapping(Value value, Integer componentIndex) {
		ScalarMapping mapping = ScalarMapping.T.create();
		mapping.setSourceValue(value);
		mapping.setTupleComponentIndex(componentIndex != null ? componentIndex : index++);

		return mapping;
	}

}
