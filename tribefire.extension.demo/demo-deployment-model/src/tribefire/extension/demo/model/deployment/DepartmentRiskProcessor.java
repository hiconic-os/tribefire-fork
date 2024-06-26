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
package tribefire.extension.demo.model.deployment;

import com.braintribe.model.extensiondeployment.StateChangeProcessor;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * The denotation type for the actual DepartmentRiskProcessor implementation
 * which can be configured and deployed in the ControlCenter. The properties
 * configured here will be transfered to the DepartmentRiskProcesor
 * implementation during deployment.
 */

public interface DepartmentRiskProcessor extends StateChangeProcessor {
	
	EntityType<DepartmentRiskProcessor> T = EntityTypes.T(DepartmentRiskProcessor.class);

	/*
	 * Constants for each property name.
	 */
	String riskMessage = "riskMessage";
	String okMessage = "okMessage";

	
	/**
	 * Message that should be send to the CEO in case of risk
	 */
	void setRiskMessage(String riskMessage);
	String getRiskMessage();

	/**
	 * Message that should be send to the CEO in case everything is OK.
	 */
	void setOkMessage(String okMessage);
	String getOkMessage();
}
