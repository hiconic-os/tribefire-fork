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
package tribefire.extension.tutorial.processing;

import java.util.function.Consumer;

import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.api.aspect.HttpStatusCodeNotification;
import com.braintribe.model.processing.service.impl.AbstractDispatchingServiceProcessor;
import com.braintribe.model.processing.service.impl.DispatchConfiguration;
import com.braintribe.utils.CommonTools;

import tribefire.extension.tutorial.model.api.request.LetterCaseTransformRequest;
import tribefire.extension.tutorial.model.api.request.TransformToLowerCase;
import tribefire.extension.tutorial.model.api.request.TransformToUpperCase;

public class LetterCaseTransformProcessor extends AbstractDispatchingServiceProcessor<LetterCaseTransformRequest, Object> {

	@Override
	protected void configureDispatching(DispatchConfiguration<LetterCaseTransformRequest, Object> dispatching) {

		//Preparing internal logic. Depending on which request is incoming, execute the proper method (maps the request to
		//its implementation)
		dispatching.register(TransformToLowerCase.T, this::transformToLowerCase);
		dispatching.register(TransformToUpperCase.T, this::transformToUpperCase);
	}

	private String transformToLowerCase(ServiceRequestContext context, TransformToLowerCase request) {
		
		//check if the incoming sentence is not null or empty - throw illegalArgumentException in such case
		//Otherwise return the lower-case sentence
		String sentence = request.getSentence();
		
		if(sentence == null) {
			
			throw new IllegalArgumentException("Sentence must not be null or empty!");			
			
		}
		
		if(sentence.isEmpty()) {
			
			Consumer<Integer> statusCodeNotification = context.findAspect(HttpStatusCodeNotification.class);
			if (statusCodeNotification != null)
				statusCodeNotification.accept(204);
		
		}
		
		return sentence.toLowerCase();
	
			
	}
	
	private String transformToUpperCase(ServiceRequestContext context, TransformToUpperCase request) {
		
		//check if the incoming sentence is not null or empty - throw illegalArgumentException in such case
		//Otherwise return the lower-case sentence
		String sentence = request.getSentence();
		
		if(CommonTools.isEmpty(sentence)) {
			
			throw new IllegalArgumentException("Sentence must not be null or empty!");
		}
		
		return sentence.toUpperCase();
	}
	
}
