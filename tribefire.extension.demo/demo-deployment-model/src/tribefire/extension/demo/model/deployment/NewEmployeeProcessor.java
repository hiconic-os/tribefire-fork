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

import com.braintribe.model.extensiondeployment.access.AccessRequestProcessor;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * The denotation type representing the actual processor implementation. The
 * properties configured here will be transfered to the NewEmployeeProcessor
 * implementation during deployment.
 */

public interface NewEmployeeProcessor extends AccessRequestProcessor {
	
	EntityType<NewEmployeeProcessor> T = EntityTypes.T(NewEmployeeProcessor.class);

	/**
	 * The message that will be added as a comment to the new employee. 
	 */
	void setWelcomeMessage(String welcomeMessage);
	String getWelcomeMessage();
	
	/**
	 * The message that will be added to the manager of the department the employee just joined. 
	 */
	void setManagerMessage(String managerMessage);
	String getManagerMessage();
}
