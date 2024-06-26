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
package tribefire.extension.gcp.templates.api;

public interface GcpConstants {

	String CARTRIDGE_GROUPID = "tribefire.extension.gcp";
	
	String CARTRIDGE_GCP_EXTERNALID = CARTRIDGE_GROUPID + ".gcp-cartridge";

	String CARTRIDGE_GLOBAL_ID = "cartridge:" + CARTRIDGE_GCP_EXTERNALID;
	String MODULE_GLOBAL_ID = "module://" + CARTRIDGE_GROUPID+":gcp-module";

	
	String DEPLOYMENT_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":gcp-deployment-model";
	String SERVICE_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":gcp-service-model";
	String DATA_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":gcp-model";
	
}
