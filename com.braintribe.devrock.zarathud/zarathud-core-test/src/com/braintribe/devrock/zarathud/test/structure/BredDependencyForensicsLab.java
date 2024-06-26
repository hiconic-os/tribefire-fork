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
package com.braintribe.devrock.zarathud.test.structure;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zarathud.test.extraction.resolving.AbstractResolvingRunnerLab;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;

/**
 * apropos test-category : currently requires the artifacts to be present in the system's local repository..  will be changed to 
 * use the repolet eventually..
 * 
 * @author pit
 *
 */
@Category(KnownIssue.class)
public class BredDependencyForensicsLab extends AbstractResolvingRunnerLab {


	@Test
	public void test_direct_aggregator_terminal() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-direct-aggregator-terminal#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> pair = test( terminal);
		Assert.assertTrue("unexpectedly, Zed hasn't retrieved any data for [" + terminal + "]", pair.first != null);
		Assert.assertTrue("Expected reported rating for ["+ terminal + "] was [NONE], found [" + pair.first.toString() + "]", pair.first == ForensicsRating.OK);
	}

	@Test
	public void test_indirect_aggregator_terminal() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-indirect-aggregator-terminal#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> pair = test( terminal);
		Assert.assertTrue("unexpectedly, Zed hasn't retrieved any data for [" + terminal + "]", pair.first != null);
		Assert.assertTrue("Expected reported rating for ["+ terminal + "] was [NONE], found [" + pair.first.toString() + "]", pair.first == ForensicsRating.OK);
	}
	
	@Test
	public void test_direct_hybrid_terminal() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-direct-hybrid-terminal#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> pair = test( terminal);
		Assert.assertTrue("unexpectedly, Zed hasn't retrieved any data for [" + terminal + "]", pair.first != null);
		Assert.assertTrue("Expected reported rating for ["+ terminal + "] was [NONE], found [" + pair.first.toString() + "]", pair.first == ForensicsRating.OK);
	}
	
	@Test
	public void test_indirect_hybrid_terminal() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-indirect-hybrid-terminal#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> pair = test( terminal);
		Assert.assertTrue("unexpectedly, Zed hasn't retrieved any data for [" + terminal + "]", pair.first != null);
		Assert.assertTrue("Expected reported rating for ["+ terminal + "] was [NONE], found [" + pair.first.toString() + "]", pair.first == ForensicsRating.OK);
	}
	
	
	@Test
	public void test_aggregator() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-aggregator-one#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> test = test( terminal);		
		Assert.assertTrue("unexpectedly, Zed has retrieved data from [" + terminal + "]", test.first == null && test.second == null);
	}
	
	@Test
	public void test_hybrid() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-hybrid-one#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> pair = test( terminal);
		Assert.assertTrue("unexpectedly, Zed hasn't retrieved any data for [" + terminal + "]", pair.first != null);
		Assert.assertTrue("Expected reported rating for ["+ terminal + "] was [NONE], found [" + pair.first.toString() + "]", pair.first == ForensicsRating.OK);
	}
	
	@Test
	public void test_perserve_terminal() {
		String terminal = "com.braintribe.devrock.test.zarathud:z-preserving-terminal#1.0.1-pc";
		Pair<ForensicsRating,Map<FingerPrint,ForensicsRating>> pair = test( terminal);
		Assert.assertTrue("unexpectedly, Zed hasn't retrieved any data for [" + terminal + "]", pair.first != null);
		Assert.assertTrue("Expected reported rating for ["+ terminal + "] was [NONE], found [" + pair.first.toString() + "]", pair.first == ForensicsRating.OK);
	}
	
}
