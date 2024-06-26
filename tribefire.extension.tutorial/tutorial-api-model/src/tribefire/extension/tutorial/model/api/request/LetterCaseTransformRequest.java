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
package tribefire.extension.tutorial.model.api.request;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.AuthorizedRequest;
import com.braintribe.model.service.api.ServiceRequest;

@Abstract
public interface LetterCaseTransformRequest extends AuthorizedRequest {
	
	EntityType<LetterCaseTransformRequest> T = EntityTypes.T(LetterCaseTransformRequest.class);

	@Mandatory
	String getSentence();
	void setSentence(String sentence);
	
	@Override
	EvalContext<String> eval(Evaluator<ServiceRequest> evaluator);
	
	/** From a modeling point of view one always needs to design the architecture in 
	 * terms of "thinking as abstract as possible and as concrete as necessary" 
	 * and "avoiding redundancies" and "bundle commonalities" - in our example 
	 * both concrete requests have a sentence as an input, and both requests return a String
	 *  -> that is why we can put the sentence property as well as the eval method (response) 
	 *  to the abstract base type.
	*/

	
}
