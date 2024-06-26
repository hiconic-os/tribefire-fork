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
package com.braintribe.execution.graph.impl;

import static com.braintribe.utils.lcd.CollectionTools2.asList;

import java.util.List;
import java.util.function.Function;

/**
 * @author peter.gazdik
 */
public abstract class _PgeTestBase {

	protected static Function<List<TestNode>, PgeGraph<TestNode>> childFactory = nodes -> PgeGraph.forChildResolver(nodes, TestNode::getChildren);
	protected static Function<List<TestNode>, PgeGraph<TestNode>> parentFactory = nodes -> PgeGraph.forParentResolver(nodes, TestNode::getParents);

	protected final TestNode root = new TestNode("root");
	protected final TestNode innerL = new TestNode("innerL");
	protected final TestNode innerR = new TestNode("innerR");

	protected final TestNode leafLL = new TestNode("leafLL");
	protected final TestNode leafLR = new TestNode("leafLR");

	protected final TestNode leafRL = new TestNode("leafRL");
	protected final TestNode leafRR = new TestNode("leafRL");

	protected final List<TestNode> NODES_ROOT = asList(root);
	protected final List<TestNode> NODES_LEAVES = asList(leafLL, leafLR, leafRL, leafRR);
	protected final List<TestNode> NODES_ALL = asList(root, innerL, innerR, leafLL, leafLR, leafRL, leafRR);

	protected void standardTreeSetup() {
		markChildParent(leafLL, innerL);
		markChildParent(leafLR, innerL);

		markChildParent(leafRL, innerR);
		markChildParent(leafRR, innerR);

		markChildParent(innerL, root);
		markChildParent(innerR, root);
	}

	protected void standardDagSetup() {
		standardTreeSetup();
		standardDagAdditionToStandardTree();
	}

	protected void standardDagAdditionToStandardTree() {
		markChildParent(leafLL, innerR);
		markChildParent(leafLR, innerR);

		markChildParent(leafRL, innerL);
		markChildParent(leafRR, innerL);
	}

	protected static void markChildParent(TestNode child, TestNode parent) {
		child.parents.add(parent);
		parent.children.add(child);
	}

}
