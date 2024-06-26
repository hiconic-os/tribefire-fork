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
package com.braintribe.model.processing.auth;

public interface AuthConstants {

	String CARTRIDGE_GROUPID = "tribefire.extension.auth";
	
	String CARTRIDGE_EXTERNALID = CARTRIDGE_GROUPID + ".auth-cartridge";

	String CARTRIDGE_GLOBAL_ID = "cartridge:" + CARTRIDGE_EXTERNALID;

	
	String DEPLOYMENT_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":auth-deployment-model";
	String SERVICE_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":auth-service-model";
	String DATA_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":auth-data-model";

	String CONFIGURED_DATA_MODEL_QUALIFIEDNAME = CARTRIDGE_GROUPID + ":configured-auth-data-model";

}
