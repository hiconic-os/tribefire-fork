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
package tribefire.extension.graphux.graph_ux_initializer;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.constraint.TypeSpecification;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.graphux.graph_ux_initializer.wire.GraphUxInitializerWireModule;
import tribefire.extension.graphux.graph_ux_initializer.wire.contract.GraphUxInitializerContract;
import tribefire.extension.graphux.graph_ux_initializer.wire.contract.GraphUxInitializerMainContract;
import tribefire.extension.graphux.graph_ux_initializer.wire.contract.GraphUxInitializerModelsContract;
import tribefire.extension.graphux.model.service.GraphUxServiceRequest;


public class GraphUxInitializer extends AbstractInitializer<GraphUxInitializerMainContract> {

	@Override
	public WireTerminalModule<GraphUxInitializerMainContract> getInitializerWireModule() {
		return GraphUxInitializerWireModule.INSTANCE;
	}
	
	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<GraphUxInitializerMainContract> initializerContext,
			GraphUxInitializerMainContract initializerMainContract) {

		GraphUxInitializerModelsContract models = initializerMainContract.models();
		CoreInstancesContract coreInstances = initializerMainContract.coreInstances();
		
		GmMetaModel cortexModel = coreInstances.cortexModel();
		GmMetaModel cortexServiceModel = coreInstances.cortexServiceModel();
		
		cortexModel.getDependencies().add(models.configuredGraphUxDeploymentModel());

		cortexServiceModel.getDependencies().add(models.configuredGraphUxServiceModel());
		
		GraphUxInitializerContract initializer = initializerMainContract.initializer();
		
		initializer.graphUxProcessor();	
		
		BasicModelMetaDataEditor editor = new BasicModelMetaDataEditor(models.configuredGraphUxServiceModel());		
		editor.onEntityType(GraphUxServiceRequest.T).addMetaData(initializer.processWithGraphUxProcessor());
		
		GmMetaModel simpleModels = initializerMainContract.existingInstances().simpleServiceModel();
		
		simpleModels.getDependencies().add(models.configuredGraphUxServiceModel());
		simpleModels.getDependencies().add(initializerMainContract.simpleExistingInstances().simpleDataModel());
		
		// Fix ID type problem - remove after P.G. fix it :)
		TypeSpecification md = TypeSpecification.T.create();
		md.setType(context.getSession().query().findEntity("type:string"));
		md.setConflictPriority(1d);
		md.setImportant(true);
		
		BasicModelMetaDataEditor mdEditor = new BasicModelMetaDataEditor(initializerMainContract.existingInstances().simpleDataModel());
		mdEditor.onEntityType(GenericEntity.T).addPropertyMetaData("id", md);
		
	}
}
