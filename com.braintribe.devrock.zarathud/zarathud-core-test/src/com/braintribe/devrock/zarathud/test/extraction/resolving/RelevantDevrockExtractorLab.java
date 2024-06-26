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
public class RelevantDevrockExtractorLab extends AbstractResolvingRunnerLab {

	
	@Test
	public void test__version_model() {
		test( "com.braintribe.gm:version-model#[,]");
	}
	
	@Test
	public void test__essential_artifact_model() {
		test( "com.braintribe.devrock:essential-artifact-model#[,]");
	}


	
	@Test
	public void test__declared_artifact_model() {
		test( "com.braintribe.devrock:declared-artifact-model#[,]");
	}
	
	@Test
	public void test__compiled_artifact_model() {
		test( "com.braintribe.devrock:compiled-artifact-model#[,]");
	}
	
	@Test
	public void test__consumable_artifact_model() {
		test( "com.braintribe.devrock:consumable-artifact-model#[,]");
	}
	
	@Test
	public void test__analysis_artifact_model() {
		test( "com.braintribe.devrock:analysis-artifact-model#[,]");
	}
	
	@Test
	public void test__mc_core() {
		test( "com.braintribe.devrock:mc-core#[,]");
	}


	

}

