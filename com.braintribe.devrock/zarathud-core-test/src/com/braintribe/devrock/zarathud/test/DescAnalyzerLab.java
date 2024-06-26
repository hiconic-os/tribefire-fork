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
package com.braintribe.devrock.zarathud.test;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.devrock.zarathud.extracter.registry.BasicExtractionRegistry;
import com.braintribe.model.zarathud.data.AbstractEntity;
import com.braintribe.testing.category.KnownIssue;

@Category(KnownIssue.class)
public class DescAnalyzerLab {
	private void testDisemination(String ls) {
		BasicExtractionRegistry registry = new BasicExtractionRegistry();
		AbstractEntity entity = registry.analyzeDesc(ls, null);
		System.out.println(entity);
		
	}

	@Test
	public void testSimpleTypeDisemination() {
		String ls = "V";
		testDisemination(ls);
	}

	
	@Test
	public void testSimpleDisemination() {
		String ls = "Lcom/braintribe/devrock/test/zarathud/four/FourParameter;";
		testDisemination(ls);
	}
	
	@Test
	public void testSingleDimensionDisemination() {
		String ls = "[Lcom/braintribe/devrock/test/zarathud/four/FourParameter;";
		testDisemination(ls);
	}
	@Test
	public void testTwoDimensionDisemination() {
		String ls = "[[Lcom/braintribe/devrock/test/zarathud/four/FourParameter;";
		testDisemination(ls);
	}
	
	@Test
	public void testOneParameterDisemination() {
		String ls = "Ljava/util/List<Lcom/braintribe/devrock/test/zarathud/four/FourParameter;>;";
		testDisemination(ls);
	}
	
	@Test
	public void testTwoParameterDisemination() {
		String ls = "Ljava/util/Map<Ljava/lang/String;Lcom/braintribe/devrock/test/zarathud/four/FourParameter;>;";
		testDisemination(ls);
	}
	
	
	@Test
	public void testTypeParameterDisemination() {
		String ls = "<E:Ljava/lang/Object;>Ljava/util/List<TE;>;";
		testDisemination(ls);
	}
	
		
}
