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
package tribefire.extension.azure.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.model.resource.source.ResourceSource;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.azure.initializer.wire.AzureInitializerWireModule;
import tribefire.extension.azure.initializer.wire.contract.AzureInitializerContract;
import tribefire.extension.azure.initializer.wire.contract.AzureInitializerMainContract;
import tribefire.extension.azure.model.api.AzureRequest;
import tribefire.extension.azure.model.resource.AzureBlobSource;

public class AzureInitializer extends AbstractInitializer<AzureInitializerMainContract> {

	@Override
	public WireTerminalModule<AzureInitializerMainContract> getInitializerWireModule() {
		return AzureInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<AzureInitializerMainContract> initializerContext,
			AzureInitializerMainContract initializerMainContract) {

		AzureInitializerContract initializer = initializerMainContract.initializer();

		GmMetaModel cortexServiceModel = initializerMainContract.coreInstances().cortexServiceModel();
		cortexServiceModel.getDependencies().add(initializerMainContract.models().configuredServiceModel());

		if (initializer.instantiateDefaultAccess()) {
			initializer.defaultAccess();

			addMetaDataToModelsCommon(context, initializerMainContract);
		}

		initializer.connectivityCheckBundle();
		addMetaDataToModelsProcess(context, initializerMainContract);
	}

	private void addMetaDataToModelsCommon(PersistenceInitializationContext context, AzureInitializerMainContract initializerMainContract) {
		ModelMetaDataEditor modelEditor = BasicModelMetaDataEditor.create(initializerMainContract.models().configuredDataModel())
				.withEtityFactory(context.getSession()::create).done();
		modelEditor.onEntityType(ResourceSource.T).addMetaData(initializerMainContract.initializer().binaryProcessWith());
		modelEditor.onEntityType(AzureBlobSource.T).addMetaData(initializerMainContract.initializer().binaryProcessWith());
	}
	private void addMetaDataToModelsProcess(PersistenceInitializationContext context, AzureInitializerMainContract initializerMainContract) {
		ModelMetaDataEditor modelEditor = BasicModelMetaDataEditor.create(initializerMainContract.models().configuredServiceModel())
				.withEtityFactory(context.getSession()::create).done();
		modelEditor.onEntityType(AzureRequest.T).addMetaData(initializerMainContract.initializer().serviceProcessWith());
	}

}
