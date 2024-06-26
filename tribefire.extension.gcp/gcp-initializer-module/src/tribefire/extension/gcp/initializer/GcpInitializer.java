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
package tribefire.extension.gcp.initializer;

import com.braintribe.model.gcp.deployment.GcpConnector;
import com.braintribe.model.gcp.resource.GcpStorageSource;
import com.braintribe.model.gcp.service.GcpRequest;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.prompt.Outline;
import com.braintribe.model.processing.bootstrapping.TribefireRuntime;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.gcp.initializer.wire.GcpInitializerModuleWireModule;
import tribefire.extension.gcp.initializer.wire.contract.GcpInitializerModuleMainContract;
import tribefire.extension.gcp.initializer.wire.contract.RuntimePropertiesContract;

/**
 * <p>
 * This {@link AbstractInitializer initializer} initializes targeted accesses with our custom instances available from
 * initializer's contracts.
 * </p>
 */
public class GcpInitializer extends AbstractInitializer<GcpInitializerModuleMainContract> {

	@Override
	public WireTerminalModule<GcpInitializerModuleMainContract> getInitializerWireModule() {
		return GcpInitializerModuleWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<GcpInitializerModuleMainContract> initializerContext,
			GcpInitializerModuleMainContract initializerMainContract) {

		TribefireRuntime.setPropertyPrivate("GCP_JSON_CREDENTIALS", "GCP_PRIVATE_KEY");

		GmMetaModel cortexModel = initializerMainContract.coreInstancesContract().cortexModel();
		cortexModel.getDependencies().add(initializerMainContract.initializerModelsContract().configuredDeploymentModel());

		GmMetaModel cortexServiceModel = initializerMainContract.coreInstancesContract().cortexServiceModel();
		cortexServiceModel.getDependencies().add(initializerMainContract.initializerModelsContract().configuredServiceModel());

		RuntimePropertiesContract properties = initializerMainContract.propertiesContract();
		if (properties.GCP_CREATE_DEFAULT_STORAGE_BINARY_PROCESSOR()) {
			if (!StringTools.isAnyBlank(properties.GCP_PRIVATE_KEY(), properties.GCP_PRIVATE_KEY_ID())) {
				initializerMainContract.initializerContract().gcpDefaultStorageBinaryProcessor();
				addMetaDataToModelsBinaryProcess(context, initializerMainContract);
			}
		}
		initializerMainContract.initializerContract().serviceRequestProcessor();
		initializerMainContract.initializerContract().functionalCheckBundle();
		addMetaDataToModelsCommon(context, initializerMainContract);
	}

	private void addMetaDataToModelsBinaryProcess(PersistenceInitializationContext context,
			GcpInitializerModuleMainContract initializerMainContract) {
		ModelMetaDataEditor modelEditor = BasicModelMetaDataEditor.create(initializerMainContract.initializerModelsContract().configuredDataModel())
				.withEtityFactory(context.getSession()::create).done();
		modelEditor.onEntityType(GcpStorageSource.T).addMetaData(initializerMainContract.initializerContract().binaryProcessWith());
	}

	private void addMetaDataToModelsCommon(PersistenceInitializationContext context, GcpInitializerModuleMainContract initializerMainContract) {
		ModelMetaDataEditor modelEditor = BasicModelMetaDataEditor
				.create(initializerMainContract.initializerModelsContract().configuredServiceModel()).withEtityFactory(context.getSession()::create)
				.done();
		modelEditor.onEntityType(GcpRequest.T).addMetaData(initializerMainContract.initializerContract().serviceProcessWith());

		Outline outline = context.getSession().create(Outline.T);

		modelEditor = BasicModelMetaDataEditor.create(initializerMainContract.initializerModelsContract().configuredDeploymentModel())
				.withEtityFactory(context.getSession()::create).done();
		modelEditor.onEntityType(GcpConnector.T).addPropertyMetaData(GcpConnector.jsonCredentials, outline);
		modelEditor.onEntityType(GcpConnector.T).addPropertyMetaData(GcpConnector.privateKey, outline);
	}

}
