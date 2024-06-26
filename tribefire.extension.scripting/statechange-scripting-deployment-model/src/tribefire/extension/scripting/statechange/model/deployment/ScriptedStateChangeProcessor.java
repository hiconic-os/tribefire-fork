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
package tribefire.extension.scripting.statechange.model.deployment;

import com.braintribe.model.extensiondeployment.StateChangeProcessor;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.scripting.model.deployment.Script;

public interface ScriptedStateChangeProcessor extends StateChangeProcessor {

	EntityType<ScriptedStateChangeProcessor> T = EntityTypes.T(ScriptedStateChangeProcessor.class);

	String beforeScript = "beforeScript";

	Script getBeforeScript();
	void setBeforeScript(Script beforeScript);

	String afterScript = "afterScript";

	Script getAfterScript();
	void setAfterScript(Script afterScript);

	String processScript = "processScript";

	Script getProcessScript();
	void setProcessScript(Script processScript);
}
