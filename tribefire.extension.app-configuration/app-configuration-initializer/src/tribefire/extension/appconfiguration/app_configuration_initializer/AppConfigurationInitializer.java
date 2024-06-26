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
package tribefire.extension.appconfiguration.app_configuration_initializer;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.appconfiguration.app_configuration_initializer.wire.AppConfigurationInitializerWireModule;
import tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract.AppConfigurationInitializerContract;
import tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract.AppConfigurationInitializerMainContract;
import tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.appconfiguration.model.AppConfiguration;
import tribefire.extension.appconfiguration.model.api.ExportLocalizationsToSpreadsheet;
import tribefire.extension.appconfiguration.model.api.GetAppConfiguration;
import tribefire.extension.appconfiguration.model.api.ImportLocalizationsFromSpreadsheet;

public class AppConfigurationInitializer extends AbstractInitializer<AppConfigurationInitializerMainContract> {

	@Override
	public WireTerminalModule<AppConfigurationInitializerMainContract> getInitializerWireModule() {
		return AppConfigurationInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<AppConfigurationInitializerMainContract> initializerContext,
			AppConfigurationInitializerMainContract initializerMainContract) {
		CoreInstancesContract coreInstances = initializerMainContract.coreInstances();
		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();
		AppConfigurationInitializerContract initializerInstances = initializerMainContract.initializer();

		GmMetaModel cortexModel = coreInstances.cortexModel();
		GmMetaModel cortexServiceModel = coreInstances.cortexServiceModel();

		cortexModel.getDependencies().add(existingInstances.appConfigurationDeploymentModel());
		cortexServiceModel.getDependencies().add(existingInstances.appConfigurationApiModel());

		initializerInstances.appConfigurationAccess();

		configureMetadata(existingInstances, initializerInstances);
	}

	private void configureMetadata(ExistingInstancesContract existingInstances, AppConfigurationInitializerContract initializerInstances) {
		BasicModelMetaDataEditor editor = new BasicModelMetaDataEditor(existingInstances.appConfigurationApiModel());
		editor.onEntityType(GetAppConfiguration.T).addMetaData(initializerInstances.processWithAppConfigurationProcessor());
		editor.onEntityType(ImportLocalizationsFromSpreadsheet.T)
				.addMetaData(initializerInstances.processWithImportLocalizationsFromSpreadsheetProcessor());
		editor.onEntityType(ExportLocalizationsToSpreadsheet.T)
				.addMetaData(initializerInstances.processWithExportLocalizationsToSpreadsheetProcessor());

		editor = new BasicModelMetaDataEditor(existingInstances.appConfigurationModel());
		editor.addModelMetaData(initializerInstances.hiddenForNonAdminAndGme());
		editor.onEntityType(GenericEntity.T).addMetaData(initializerInstances.hiddenForNonAdminAndApi());
		editor.onEntityType(AppConfiguration.T).addMetaData(initializerInstances.viewWithJsUxComponent());
	}

}
