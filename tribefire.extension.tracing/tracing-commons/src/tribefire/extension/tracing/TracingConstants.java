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
package tribefire.extension.tracing;

/**
 *
 */
public interface TracingConstants {

	String MODULE_GROUPID = "tribefire.extension.tracing";

	String MODULE_EXTERNALID = MODULE_GROUPID + ":tracing-module";

	String MODULE_GLOBAL_ID = "module://" + MODULE_EXTERNALID;

	String DEPLOYMENT_MODEL_QUALIFIEDNAME = MODULE_GROUPID + ":tracing-deployment-model";

	String SERVICE_MODEL_QUALIFIEDNAME = MODULE_GROUPID + ":tracing-service-model";

	String ACCESS_ID_CORTEX = "cortex";

	String MAJOR_VERSION = "1";
}
