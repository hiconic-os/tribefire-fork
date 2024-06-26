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
package tribefire.extension.tutorial.initializer.wire.space;

import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.tutorial.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.tutorial.initializer.wire.contract.TutorialInitializerContract;
import tribefire.extension.tutorial.model.deployment.LetterCaseTransformProcessor;

@Managed
public class TutorialInitializerSpace extends AbstractInitializerSpace implements TutorialInitializerContract {

	@Import
	private ExistingInstancesContract existingInstances;

	
	@Override
	@Managed
	public ProcessWith processWithLetterCaseTransform() {
		ProcessWith bean = create(ProcessWith.T);
		
		bean.setProcessor(letterCaseTransformProcessor());
		
		return bean;
	
	}
	
	@Managed
	private LetterCaseTransformProcessor letterCaseTransformProcessor() {
		LetterCaseTransformProcessor bean = create(LetterCaseTransformProcessor.T);
		
		bean.setName("Letter Case Transform Processor");
		bean.setExternalId("serviceProcessor.letterCaseTransform");
		bean.setModule(existingInstances.tutorialModule());
		return bean;		
	}
	
}
