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
package tribefire.extension.scripting.service.processing;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.model.check.service.CheckRequest;
import com.braintribe.model.check.service.CheckResult;
import com.braintribe.model.processing.check.api.CheckProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.scripting.common.CommonScriptedProcessor;


/**
 * Expert implementation to provide a {@link ScriptedCheckProcessor} based on {@link CommonScriptedProcessor}. 
 * The bindings (parameter input to script) contain
 * <ul>
 * <li> {@link ServiceRequestContext} as "$context" </li>
 * <li> {@link CheckRequest} as "$request" </li> 
 * </ul> 
 * 
 *
 */
public class ScriptedCheckProcessor extends CommonScriptedProcessor implements CheckProcessor {

	@Override
	public CheckResult check(ServiceRequestContext requestContext, CheckRequest request) {

		Map<String, Object> bindings = new HashMap<>();
		bindings.put("$context", requestContext);
		bindings.put("$request", request);

		return (CheckResult) processReasonedScripted(bindings).get();
	}

}
