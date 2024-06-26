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
package tribefire.extension.demo.test.integration.utils;

public interface DemoConstants {

	String DEMO_WORKBENCH_ACCESS_ID = "access.demo.wb";
	String DEMO_ACCESS_ID = "access.demo";
	
	// meta data is applied on the enriching model
	String CONFIGURED_DEMO_MODEL_ID = "tribefire.extension.demo:configured-demo-model";
	String CONFIGURED_DEMO_SERVICE_MODEL_ID = "tribefire.extension.demo:configured-demo-api-model";
	
	String HEALTH_CHECK_PATH = "/tribefire-services/healthz";
	String DEMO_HEALTH_CHECK_PROCESSOR_GLOBAL_ID = "wire://DemoInitializerWireModule/DemoInitializerSpace/demoHealthCheckProcessor";
	
}
