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
package com.braintribe.model.processing.smart.query.planner.graph;

import com.braintribe.model.processing.query.tools.traverse.ConditionTraverser;
import com.braintribe.model.processing.query.tools.traverse.ConditionVisitor;
import com.braintribe.model.processing.query.tools.traverse.OperandVisitor;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.FulltextComparison;
import com.braintribe.model.query.conditions.ValueComparison;
import com.braintribe.model.query.functions.JoinFunction;
import com.braintribe.model.query.functions.Localize;
import com.braintribe.model.query.functions.QueryFunction;
import com.braintribe.model.query.functions.aggregate.AggregateFunction;

/**
 * 
 */
class ConditionedPropertyMarker implements ConditionVisitor, OperandVisitor {

	private final ConditionTraverser traverser;
	private final QueryPlanStructure planStructure;

	public ConditionedPropertyMarker(QueryPlanStructure planStructure, SmartQueryPlannerContext context) {
		this.planStructure = planStructure;
		this.traverser = new ConditionTraverser(context.evalExclusionCheck(), this, this);
	}

	public void markProperties(Condition condition) {
		traverser.traverse(condition);
	}

	@Override
	public void visit(FulltextComparison condition) {
		EntitySourceNode sourceNode = planStructure.getSourceNode(condition.getSource());
		sourceNode.markNodeForSelection();
	}

	@Override
	public boolean visit(ValueComparison condition) {
		return true;
	}

	@Override
	public void visitStaticValue(Object operand) {
		return;
	}

	@Override
	// TODO check if property is simple
	public void visit(PropertyOperand po) {
		String propertyName = po.getPropertyName();

		if (propertyName.contains(".")) {
			throw new UnsupportedOperationException("Only works for simple property");
		}

		EntitySourceNode sourceNode = planStructure.getSourceNode(po.getSource());
		sourceNode.markSimpleSmartPropertyForSelection(propertyName);
	}

	@Override
	public void visit(JoinFunction operand) {
		SourceNode sourceNode = planStructure.getSourceNode(operand.getJoin());

		sourceNode.markJoinFunction();
	}

	@Override
	public void visit(Localize operand) {
		throw new UnsupportedOperationException("Method 'ConditionedPropertyMarker.visit' is not fully implemented yet!");
	}

	@Override
	public void visit(AggregateFunction operand) {
		throw new UnsupportedOperationException("Method 'ConditionedPropertyMarker.visit' is not fully implemented yet!");
	}

	@Override
	public void visit(QueryFunction operand) {
		throw new UnsupportedOperationException("Method 'ConditionedPropertyMarker.visit' is not fully implemented yet!");
	}

	@Override
	public void visit(Source operand) {
		SourceNode sourceNode = planStructure.getSourceNode(operand);
		sourceNode.markNodeForSelection();
	}

}
