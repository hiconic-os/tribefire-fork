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
package com.braintribe.model.processing.smart.query.planner.context;

import static com.braintribe.utils.lcd.CollectionTools2.newList;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.List;
import java.util.Map;

import com.braintribe.model.processing.query.tools.traverse.OperandTraverser;
import com.braintribe.model.processing.query.tools.traverse.OperandVisitor;
import com.braintribe.model.query.Operand;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.functions.JoinFunction;
import com.braintribe.model.query.functions.Localize;
import com.braintribe.model.query.functions.QueryFunction;
import com.braintribe.model.query.functions.aggregate.AggregateFunction;

/**
 * 
 * @author peter.gazdik
 */
class OperandSourceResolver {

	private final SmartQueryPlannerContext context;
	private final SourceFinder sourceFinder;

	private final Map<Object, List<Source>> sourcesForOperand = newMap();

	public OperandSourceResolver(SmartQueryPlannerContext context) {
		this.context = context;
		this.sourceFinder = new SourceFinder();
	}

	public List<Source> getSourcesForOperand(Object operand) {
		List<Source> result = sourcesForOperand.get(operand);

		if (result == null) {
			result = resolveSourcesFor(operand);
			sourcesForOperand.put(operand, result);
		}

		return result;
	}

	private List<Source> resolveSourcesFor(Object operand) {
		return sourceFinder.findSources(operand);
	}

	private class SourceFinder implements OperandVisitor {

		private final OperandTraverser traverser;

		private List<Source> currentSources;

		public SourceFinder() {
			this.traverser = new OperandTraverser(context.evalExclusionCheck(), this);
			this.currentSources = newList();
		}

		public List<Source> findSources(Object operand) {
			currentSources = newList();
			traverser.traverseOperand(operand);

			return currentSources;
		}

		@Override
		public void visit(PropertyOperand operand) {
			visit(operand.getSource());
		}

		@Override
		public void visit(JoinFunction operand) {
			visit(operand.getJoin());
		}

		@Override
		public void visit(Localize operand) {
			traverser.traverseOperand(operand.getLocalizedStringOperand());
		}

		@Override
		public void visit(AggregateFunction operand) {
			throw new UnsupportedOperationException("Method 'GroupComparator.SourceMarker.visit' is not implemented yet!");
		}

		@Override
		public void visit(QueryFunction operand) {
			for (Operand o: context.listOperands(operand))
				traverser.traverseOperand(o);
		}

		@Override
		public void visit(Source operand) {
			currentSources.add(operand);
		}

	}

}
