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
package com.braintribe.model.processing.am;

import java.util.Map;

import org.junit.Test;

import com.braintribe.utils.lcd.MapTools;

public class SimpleMonitoringTest extends AbstractMonitoringTest {

	@Test
	public void testSimpleChainCreatedIncrementally() throws Exception {
		initMonitoring(n0);

		n0.setLink1(n1);
		n1.setLink1(n2);

		assertReachableEntities(n0, n1, n2);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n1, n2, 1);
	}

	@Test
	public void testSimpleChainCreatedAtOnce() throws Exception {
		initMonitoring(n0);

		n1.setLink1(n2);
		n0.setLink1(n1);

		assertReachableEntities(n0, n1, n2);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n1, n2, 1);
	}

	@Test
	public void testRemovingEdge() throws Exception {
		initMonitoring(n0);

		chain(n0, n1, n2);
		n0.setLink1(null);

		assertReachableEntities(n0);
	}

	@Test
	public void testDoubleRef() throws Exception {
		initMonitoring(n0);

		n0.setLink1(n1);
		n0.setLink2(n1);

		assertReachableEntities(n0, n1);
		assertReferenceDegree(n0, n1, 2);
	}

	@Test
	public void testMultipleDifferentRefs() throws Exception {
		initMonitoring(n0);

		n0.setLink1(n1);
		n1.setLink1(n2);
		n0.setLink2(n2);

		assertReachableEntities(n0, n1, n2);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n0, n2, 1);
		assertReferenceDegree(n1, n2, 1);
	}

	@Test
	public void testMultipleCircle() throws Exception {
		initMonitoring(n0);

		circle(n0, n1, n2, n3);

		assertReachableEntities(n0, n1, n2, n3);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n1, n2, 1);
		assertReferenceDegree(n2, n3, 1);
		assertReferenceDegree(n3, n0, 1);
	}

	@Test
	public void testRemovingArticulationVertex() throws Exception {
		chain(n0, n1, n2, n3);
		n2.setLink2(n1);

		initMonitoring(n0);

		n0.setLink1(null);

		assertReachableEntities(n0);
	}

	@Test
	public void testRemovingArticulationVertex_WithNonRootVertex() throws Exception {
		chain(n5, n0, n1, n2, n3);
		n2.setLink2(n1);

		initMonitoring(n5);

		n0.setLink1(null);

		assertReachableEntities(n5, n0);
	}

	@Test
	public void testRelinkingWithBacklink() throws Exception {
		chain(n0, n1, n2, n3);
		n2.setLink2(n1);

		initMonitoring(n0);

		n0.setLink1(n3);

		assertReachableEntities(n0, n3);
		assertReferenceDegree(n0, n3, 1);
	}

	@Test
	public void testUnlinkingCircle() throws Exception {
		chain(n0, n1, n2, n3);
		n3.setLink1(n1);

		initMonitoring(n0);

		n1.setLink1(n0);

		assertReachableEntities(n0, n1);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n1, n0, 1);
	}

	@Test
	public void testInnerCircleNotCollectedIfStillReferenced() throws Exception {
		chain(n0, n1, n2, n3);
		circle(n4, n5, n6, n7);
		n1.setLink2(n4);
		n3.setLink2(n6);

		initMonitoring(n0);
		n1.setLink2(null);

		assertReachableEntities(n0, n1, n2, n3, n4, n5, n6, n7);
		assertReferenceDegree(n4, n5, 1);
		assertReferenceDegree(n3, n6, 1);
		assertReferenceDegree(n5, n6, 1);
		assertReferenceDegree(n6, n7, 1);
		assertReferenceDegree(n7, n4, 1);
	}

	@Test
	public void testInnerCircleNotCollectedIfReReferenced() throws Exception {
		chain(n0, n1, n2, n3);
		circle(n4, n5, n6, n7);
		n1.setLink2(n4);

		initMonitoring(n0);
		n1.setLink2(n6);

		assertReachableEntities(n0, n1, n2, n3, n4, n5, n6, n7);
		assertReferenceDegree(n4, n5, 1);
		assertReferenceDegree(n1, n6, 1);
		assertReferenceDegree(n5, n6, 1);
		assertReferenceDegree(n6, n7, 1);
		assertReferenceDegree(n7, n4, 1);
	}

	@Test
	public void testSkippingVertex() throws Exception {
		chain(n0, n1, n2, n3);
		n0.setLink2(n2);

		initMonitoring(n0);

		n0.setLink1(null);

		assertReachableEntities(n0, n2, n3);
		assertReferenceDegree(n0, n2, 1);
		assertReferenceDegree(n2, n3, 1);
	}

	@Test
	public void testRemovingOneOfTwoParallelEdgesIsNoProblem() throws Exception {
		circle(n0, n1, n2);
		n1.setLink2(n2);

		initMonitoring(n0);

		n1.setLink2(null);

		assertReachableEntities(n0, n1, n2);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n1, n2, 1);
		assertReferenceDegree(n2, n0, 1);
	}

	@Test
	public void removingTwoEdgesAtOnce() throws Exception {
		chain(n0, n1, n2, n3);
		chain(n4, n5, n6, n7);
		n1.setLinkMap(asMap(n4, n4, n6, n6));
		n4.setLink2(n2);

		initMonitoring(n0);

		n1.getLinkMap().clear();

		assertReachableEntities(n0, n1, n2, n3);
		assertReferenceDegree(n0, n1, 1);
		assertReferenceDegree(n1, n2, 1);
		assertReferenceDegree(n2, n3, 1);
	}

	@Test
	public void removing() throws Exception {
		chain(n0, n1, n2, n3);
		chain(n4, n5, n6, n7);
		n1.setLinkMap(asMap(n5, n4, n6, n6));
		n4.setLink2(n2);
		
		initMonitoring(n0);
		
		n1.getLinkMap().putAll(asMap(n5, n5, n6, n5));
		
		printMonitoringState();

		assertReachableEntities(n0, n1, n2, n3, n5, n6, n7);
	}
	
	@SuppressWarnings("unchecked")
	Map<Node, Node> asMap(Node... objects) {
		return (Map<Node, Node>) (Map<?, ?>) MapTools.getMap((Object[]) objects);
	}
}
