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
package tribefire.extension.opentracing;

/**
 *
 */
public interface OpentracingConstants {

	String MODULE_GROUPID = "tribefire.extension.opentracing";

	String MODULE_EXTERNALID = MODULE_GROUPID + ":opentracing-module";

	String MODULE_GLOBAL_ID = "module://" + MODULE_EXTERNALID;

	String DEPLOYMENT_MODEL_QUALIFIEDNAME = MODULE_GROUPID + ":opentracing-deployment-model";

	String DATA_MODEL_QUALIFIEDNAME = MODULE_GROUPID + ":opentracing-data-model";

	String SERVICE_MODEL_QUALIFIEDNAME = MODULE_GROUPID + ":opentracing-service-model";

	String OPENTRACING_WORKBENCH_ACCESS_MODEL = MODULE_GROUPID + ":opentracing-access-workbench-model";

	String OPENTRACING_WORKBENCH_ACCESS_EXTERNALID = "extension.opentracing.access.wb";
	String OPENTRACING_WORKBENCH_ACCESS_GLOBALID = OPENTRACING_WORKBENCH_ACCESS_EXTERNALID;

	String ACCESS_ID_CORTEX = "cortex";

	String MAJOR_VERSION = "1";
}
