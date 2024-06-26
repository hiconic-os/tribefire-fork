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
package com.braintribe.devrock.zarathud.test.extraction.resolving;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.KnownIssue;

@Category(KnownIssue.class)
public class BredExtractorLab extends AbstractResolvingRunnerLab {
	
	
	@Test
	public void testZOne() {		
		test( "com.braintribe.devrock.test.zarathud:z-one#[,]");
	}
	
	@Test
	public void testZParams() {
		test( "com.braintribe.devrock.test.zarathud:z-params#[,]");
	}

	@Test
	public void testZAnnotations() {
		test( "com.braintribe.devrock.test.zarathud:z-annotations#[,]");
	}

	@Test
	public void testZTwo() {
		test( "com.braintribe.devrock.test.zarathud:z-two#[,]");
	}
	
	@Test
	public void testZThree() {
		test( "com.braintribe.devrock.test.zarathud:z-three#[,]");
	}
	@Test
	public void testZFour() {
		test(  "com.braintribe.devrock.test.zarathud:z-four#[,]");
	}
	
	@Test
	public void testZFive() {
		test(  "com.braintribe.devrock.test.zarathud:z-five#[,]");
	}
	
	@Test
	public void testZSix() {
		test( "com.braintribe.devrock.test.zarathud:z-six#[,]");
	}
	@Test
	public void testZSeven() {
		test(  "com.braintribe.devrock.test.zarathud:z-seven#[,]");
	}


	//@Test
	public void testZScratch() {
		test( "com.braintribe.devrock.test.zarathud:z-scratch#[,]");
	}
	
	@Test
	public void testZInners() {
		test( "com.braintribe.devrock.test.zarathud:z-inners#[,]");
	}
	
	
	@Test
	public void testZSuppress() {
		test(  "com.braintribe.devrock.test.zarathud:z-suppress#[,]");
	}
	

	@Test
	public void testZModelOne() {
		test( "com.braintribe.devrock.test.zarathud:z-model-one#[,]");
	}
	@Test
	public void testZModelTwo() {
		test( "com.braintribe.devrock.test.zarathud:z-model-two#[,]");
	}
	
	@Test
	public void testZAggregator() {
		test( "com.braintribe.devrock.test.zarathud:z-direct-aggregator-terminal#[,]");
	}
	
	public static void main (String [] args) {
		if (args.length == 0){
			System.err.println("parameters : <name>\nwhere <name> is a valid condensed artifact name");
			return;
		}		
		String condensedName = args[0];				
		new BredExtractorLab().test(condensedName);
	}
}
