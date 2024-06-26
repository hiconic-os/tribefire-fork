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
package com.braintribe.model.processing.smart.query.planner.core.combination;

import com.braintribe.model.accessdeployment.smart.meta.OrderedLinkPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.processing.query.tools.traverse.ConditionTraverser;
import com.braintribe.model.processing.query.tools.traverse.ConditionVisitor;
import com.braintribe.model.processing.query.tools.traverse.OperandVisitor;
import com.braintribe.model.processing.smart.query.planner.context.SmartQueryPlannerContext;
import com.braintribe.model.processing.smart.query.planner.graph.EntitySourceNode;
import com.braintribe.model.processing.smart.query.planner.graph.QueryPlanStructure;
import com.braintribe.model.processing.smart.query.planner.graph.SimpleValueNode;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNode;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNodeType;
import com.braintribe.model.processing.smart.query.planner.tools.SmartConversionTools;
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
 * Resolves SAN for given condition
 */
class ConditionNodeResolver {

	private final SmartQueryPlannerContext context;
	private final QueryPlanStructure planStructure;

	public ConditionNodeResolver(SmartQueryPlannerContext context) {
		this.context = context;
		this.planStructure = context.planStructure();
	}

	// Seems this is only invoked for ValueComparison and FulltextSearch
	public ConditionExaminationDescription resolveNodesForCondition(Condition condition) {
		return new NodeResolution().resolve(condition);
	}

	class NodeResolution implements ConditionVisitor, OperandVisitor {
		ConditionTraverser traverser = new ConditionTraverser(context.evalExclusionCheck(), this, this);
		ConditionExaminationDescription result = new ConditionExaminationDescription();

		public ConditionExaminationDescription resolve(Condition condition) {
			traverser.traverse(condition);
			return result;
		}

		@Override
		public void visit(FulltextComparison condition) {
			EntitySourceNode sourceNode = planStructure.getSourceNode(condition.getSource());
			result.affectedSourceNodes.add(sourceNode);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean visit(ValueComparison condition) {
			checkComparisonDelegateable(condition);

			return true;
		}

		private void checkComparisonDelegateable(ValueComparison vc) {
			if (SmartConversionTools.isEqualityBasedOperator(vc.getOperator()))
				return;

			markNondelegatableIfConvertedProperty(vc.getLeftOperand());
			markNondelegatableIfConvertedProperty(vc.getRightOperand());
		}

		private void markNondelegatableIfConvertedProperty(Object operand) {
			SmartConversion conversion = context.findConversion(operand);
			if (conversion != null && !SmartConversionTools.isDelegateableToStringConversion(conversion))
				result.delegateable = false;
		}

		@Override
		public void visit(PropertyOperand po) {
			markSource(po.getSource(), false);
		}

		@Override
		public void visit(JoinFunction operand) {
			markSource(operand.getJoin(), true);
		}

		@Override
		public void visit(Localize operand) {
			throw new UnsupportedOperationException("Method 'ConditionNodeResolver.NodeResolution.visit' is not implemented yet!");
		}

		@Override
		public void visit(AggregateFunction operand) {
			throw new UnsupportedOperationException("Method 'ConditionNodeResolver.NodeResolution.visit' is not implemented yet!");
		}

		@Override
		public void visit(QueryFunction operand) {
			for (Object operand1: context.listOperands(operand))
				traverser.traverseOperand(operand1);
		}

		@Override
		public void visit(Source source) {
			markSource(source, false);
		}

		private void markSource(Source source, boolean joinFunction) {
			SourceNode sourceNode = planStructure.getSourceNode(source);

			if (sourceNode instanceof EntitySourceNode)
				result.affectedSourceNodes.add(resolveEntityNode(sourceNode, joinFunction));
			else
				result.affectedSourceNodes.add(((SimpleValueNode) sourceNode).getJoinMaster());
		}

		/**
		 * Resolves the right {@link EntitySourceNode} which should be marked. In case of dealing with a joinFunction of
		 * a {@link OrderedLinkPropertyAssignment}, the index is a property of the "Link Entity". In other cases we
		 * simply return the node given as parameter.
		 */
		private EntitySourceNode resolveEntityNode(SourceNode sourceNode, boolean joinFunction) {
			EntitySourceNode entityNode = (EntitySourceNode) sourceNode;

			if (joinFunction && sourceNode.getSourceNodeType() == SourceNodeType.linkedCollectionNode)
				entityNode = entityNode.getJoinMaster();

			return entityNode;
		}

	}
}
