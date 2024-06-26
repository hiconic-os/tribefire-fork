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
package tribefire.extension.modelling_cortex_initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.modelling.commons.ModellingConstants;
import tribefire.extension.modelling.management.api.ModellingManagementRequest;
import tribefire.extension.modelling.model.api.request.ModellingRequest;
import tribefire.extension.modelling.model.diagram.ModellingDiagram;
import tribefire.extension.modelling_cortex_initializer.wire.ModellingCortexModule;
import tribefire.extension.modelling_cortex_initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.modelling_cortex_initializer.wire.contract.ModellingCortexContract;
import tribefire.extension.modelling_cortex_initializer.wire.contract.ModellingCortexMainContract;
import tribefire.extension.modelling_cortex_initializer.wire.contract.ModellingCortexModelsContract;

public class ModellingCortexInitializer extends AbstractInitializer<ModellingCortexMainContract> implements ModellingConstants {

	@Override
	protected WireTerminalModule<ModellingCortexMainContract> getInitializerWireModule() {
		return ModellingCortexModule.INSTANCE;
	}

	@Override
	protected void initialize(PersistenceInitializationContext context, WiredInitializerContext<ModellingCortexMainContract> initializerContext,
			ModellingCortexMainContract initializerContract) {
		
		String accessId = context.getAccessId();
		ModellingCortexContract modellingContract = initializerContract.initializerContract();
		
		// cortex
		if (accessId.equalsIgnoreCase(EXT_ID_ACCESS_CORTEX)) {
			ModellingCortexModelsContract models = initializerContract.initializerModelsContract();
			
			ExistingInstancesContract existingInstances = initializerContract.existingInstancesContract();
			CoreInstancesContract coreInstances = initializerContract.coreInstancesContract();
			
			//
			// Models
			//
			
			models.managementWbModel();
			models.projectWbModel();
			models.projectModel();
			
			
			GmMetaModel cortexModel = coreInstances.cortexModel();
			cortexModel.getDependencies().add(existingInstances.modellingDeploymentModel());
			
			//
			// Deployables
			//
			
			modellingContract.processWithModellingManagementProcessor();
			modellingContract.processWithModellingProcessor();
			
			modellingContract.managementAccess();
			modellingContract.modellingWbAccess();
			
			//
			// Meta Data
			//
			
			addMetaDataToModels(initializerContext);
		}
		
		
		// APE
		if (accessId.equalsIgnoreCase(EXT_ID_ACCESS_REPOSITORY_CONFIGURATION)) {
			modellingContract.repositoryConfiguration();
		}
	}
	
	private void addMetaDataToModels(WiredInitializerContext<ModellingCortexMainContract> context) {
		addMetaDataToModellingApiModel(context);
		addMetaDataToModellingProjectModel(context);
	}
	
	private void addMetaDataToModellingApiModel(WiredInitializerContext<ModellingCortexMainContract> context) {
		ModellingCortexContract initializer = context.contract().initializerContract();
		ExistingInstancesContract existingInstances = context.contract().existingInstancesContract();
		
		ModelMetaDataEditor editor = new BasicModelMetaDataEditor(existingInstances.modellingApiModel());
		
		editor
		.onEntityType(ModellingRequest.T)
		.addMetaData(initializer.processWithModellingProcessor());
		
		
		editor = new BasicModelMetaDataEditor(existingInstances.managementApiModel());
		
		editor
		.onEntityType(ModellingManagementRequest.T)
		.addMetaData(initializer.processWithModellingManagementProcessor());
	}
	
	private void addMetaDataToModellingProjectModel(WiredInitializerContext<ModellingCortexMainContract> context) {
		ModelMetaDataEditor editor = new BasicModelMetaDataEditor(context.contract().initializerModelsContract().projectModel());
		
		editor.onEntityType(ModellingDiagram.T).addMetaData(context.contract().initializerContract().viewWithModeller());
	}
}
