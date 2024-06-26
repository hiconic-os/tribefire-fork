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

import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.processing.service.api.ProceedContext;
import com.braintribe.model.processing.service.api.ReasonedServiceAroundProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.scripting.common.CommonScriptedProcessor;

/**
 * Expert implementation to provide a {@link ScriptedServiceAroundProcessor} based on {@link CommonScriptedProcessor}. 
 * The bindings (parameter input to script) contain
 * <ul>
 * <li> {@link ServiceRequestContext} as "$context" </li>
 * <li> {@link ProceedContext} as "$proceedContext" </li>
 * <li> {@link ServiceRequest} as "$request" </li> 
 * </ul> 
 * 
 *
 */
public class ScriptedServiceAroundProcessor extends CommonScriptedProcessor implements ReasonedServiceAroundProcessor<ServiceRequest, Object> {

	@Override
	public Maybe<? extends ServiceRequest> processReasoned(ServiceRequestContext requestContext, ServiceRequest request,
			ProceedContext proceedContext) {
		
		Map<String, Object> bindings = new HashMap<>();
		bindings.put("$context", requestContext);
		bindings.put("$request", request);
		bindings.put("$proceedContext", proceedContext);

		return processReasonedScripted(bindings);
	}

}
