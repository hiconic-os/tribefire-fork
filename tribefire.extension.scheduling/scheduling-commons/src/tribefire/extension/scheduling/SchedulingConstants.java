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
package tribefire.extension.scheduling;

public interface SchedulingConstants {

	String GROUPID = "tribefire.extension.scheduling";

	String MODULE_EXTERNALID = GROUPID + ".scheduling-module";

	String MODULE_GLOBAL_ID = "module://" + GROUPID + ":scheduling-module";

	String DEPLOYMENT_MODEL_QUALIFIEDNAME = GROUPID + ":scheduling-deployment-model";
	String SERVICE_MODEL_QUALIFIEDNAME = GROUPID + ":scheduling-api-model";
	String DATA_MODEL_QUALIFIEDNAME = GROUPID + ":scheduling-model";

	String MODEL_GLOBAL_ID_PREFIX = "model:";
	String WIRE_GLOBAL_ID_PREFIX = "wire://";
	String SCHEDULING_MODEL_GLOBAL_ID_PREFIX = MODEL_GLOBAL_ID_PREFIX + GROUPID + ":";

	String SCHEDULING_DEPLOYMENT_MODEL_GLOBAL_ID = SCHEDULING_MODEL_GLOBAL_ID_PREFIX + "scheduling-deployment-model";
	String SCHEDULING_API_MODEL_GLOBAL_ID = SCHEDULING_MODEL_GLOBAL_ID_PREFIX + "scheduling-api-model";
	String SCHEDULING_MODEL_GLOBAL_ID = SCHEDULING_MODEL_GLOBAL_ID_PREFIX + "scheduling-model";

	String ACCESS_ID = "access.scheduling";
	String WB_ACCESS_ID = "access.scheduling.wb";

	int MAJOR_VERSION = 1;

	String EXTERNAL_ID_SCHEDULING_SERVICE_PROCESSOR = "scheduling.serviceProcessor";

}
