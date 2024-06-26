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
public class RealLifeExtractorLab extends AbstractResolvingRunnerLab {

	@Test
	public void test__root_model() {
		test( "com.braintribe.gm:root-model#[,]");
	}
	

	@Test
	public void test__time_model() {
		test( "com.braintribe.gm:time-model#[,]");
	}
	
	@Test
	public void test__gm_core_api() {
		test( "com.braintribe.gm:gm-core-api#[,]");
	}
	
	@Test
	public void test__mc() {		
		test(  "com.braintribe.devrock:malaclypse#[,]");	
	}
	
	@Test
	public void test__artifact_model() {
		test( "com.braintribe.devrock:artifact-model#[,]");
	}
	
	@Test
	public void test__access_api_model() {
		test( "com.braintribe.gm:access-api-model#[,]");
	}
	
	@Test
	public void test__basic_access_adapter() {
		test( "com.braintribe.gm:basic-access-adapters#[,]");
	}

	
	@Test
	public void test__service_api_model() {
		test( "com.braintribe.gm:service-api-model#[,]");
	}
	
	@Test
	public void test__basic_managed_gm_session() {
		test( "com.braintribe.gm:basic-managed-gm-session#[,]");
	}
	
	@Test
	public void test__basic_model_path_processing() {
		test( "com.braintribe.gm:basic-model-path-processing-test#[,]");
	}
	
	// fatals
	@Test
	public void test__gm_assertJ_assertions(){
		test( "com.braintribe.gm:gm-assertj-assertions#[,]");
	}
	@Test
	public void test__meta_model(){
		test( "com.braintribe.gm:meta-model#[,]");
	}
	@Test
	public void test__tribefire_platform_commons(){
		test( "tribefire.cortex:tribefire-platform-commons#[,]");
	}

	@Test
	public void test__gm_web_rpc_client_test(){
		test( "tribefire.cortex:gm-web-rpc-client-test#[,]");
	}
	
	
	@Test
	public void test__schemed_xml_service_model(){
		test( "tribefire.extension.schemed-xml:schemed-xml-service-model#[,]");
	}
	
	@Test
	public void test__artifact_processing_service_model(){
		test( "tribefire.extension.artifact:artifact-processing-service-model#[,]");
	}
	
	@Test
	public void test__version_model(){
		test( "com.braintribe.gm:version-model#[,]");
	}
	@Test
	public void test__value_desc(){
		test( "com.braintribe.gm:value-descriptor-evaluator-test#[,]");
	}
	
	@Test
	public void test__focus(){
		test( "tribefire.adx.phoenix:adx-cmis-deployment-model#[,]");
	}
	
	
	/*
	 * 
	 * ERROR:		com.braintribe.gm:access-api-model#1.0.12-pc
		tribefire.cortex:audit-aspect#2.0.12-pc
		
	*/
}

