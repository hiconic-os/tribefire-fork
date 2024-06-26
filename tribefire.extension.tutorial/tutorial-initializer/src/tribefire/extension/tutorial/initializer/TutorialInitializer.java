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
package tribefire.extension.tutorial.initializer;

import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.tutorial.initializer.wire.TutorialInitializerWireModule;
import tribefire.extension.tutorial.initializer.wire.contract.TutorialInitializerContract;
import tribefire.extension.tutorial.initializer.wire.contract.TutorialInitializerMainContract;
import tribefire.extension.tutorial.initializer.wire.contract.TutorialInitializerModelsContract;
import tribefire.extension.tutorial.model.api.request.LetterCaseTransformRequest;

public class TutorialInitializer extends AbstractInitializer<TutorialInitializerMainContract> {


	@Override
	public WireTerminalModule<TutorialInitializerMainContract> getInitializerWireModule() {
		return TutorialInitializerWireModule.INSTANCE;
	}
	
	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<TutorialInitializerMainContract> initializerContext,
			TutorialInitializerMainContract initializerMainContract) {

		CoreInstancesContract coreInstances = initializerMainContract.coreInstances();
		TutorialInitializerModelsContract models = initializerMainContract.models();
		coreInstances.cortexServiceModel().getDependencies().add(models.configuredTutorialApiModel());
		addMetaDataToModels(initializerMainContract);
		
	}

	private void addMetaDataToModels(TutorialInitializerMainContract initializerMainContract) {
		TutorialInitializerModelsContract models = initializerMainContract.models();
		TutorialInitializerContract initializer = initializerMainContract.initializer();
		
		ModelMetaDataEditor editor = new BasicModelMetaDataEditor(models.configuredTutorialApiModel());
		
		editor.onEntityType(LetterCaseTransformRequest.T).addMetaData(initializer.processWithLetterCaseTransform());
		
	}
}
