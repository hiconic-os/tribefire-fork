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
package tribefire.extension.appconfiguration.app_configuration.wire.space;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.appconfiguration.processing.services.AppConfigurationProcessor;
import tribefire.extension.appconfiguration.processing.services.ExportLocalizationsToSpreadsheetProcessor;
import tribefire.extension.appconfiguration.processing.services.ImportLocalizationsFromSpreadsheetProcessor;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class AppConfigurationDeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Managed
	public AppConfigurationProcessor appConfigurationProcessor(
			ExpertContext<tribefire.extension.appconfiguration.model.deployment.AppConfigurationProcessor> context) {
		tribefire.extension.appconfiguration.model.deployment.AppConfigurationProcessor deployable = context.getDeployable();

		AppConfigurationProcessor bean = new AppConfigurationProcessor();
		bean.setSessionSupplier(() -> tfPlatform.systemUserRelated().sessionFactory().newSession(deployable.getAccessId()));
		return bean;
	}

	@Managed
	public ImportLocalizationsFromSpreadsheetProcessor importLocalizationsFromSpreadsheetProcessor(
			ExpertContext<tribefire.extension.appconfiguration.model.deployment.ImportLocalizationsFromSpreadsheetProcessor> context) {
		tribefire.extension.appconfiguration.model.deployment.ImportLocalizationsFromSpreadsheetProcessor deployable = context.getDeployable();

		ImportLocalizationsFromSpreadsheetProcessor bean = new ImportLocalizationsFromSpreadsheetProcessor();
		bean.setSessionSupplier(() -> tfPlatform.systemUserRelated().sessionFactory().newSession(deployable.getAccessId()));
		return bean;
	}

	@Managed
	public ExportLocalizationsToSpreadsheetProcessor exportLocalizationsToSpreadsheetProcessor(
			ExpertContext<tribefire.extension.appconfiguration.model.deployment.ExportLocalizationsToSpreadsheetProcessor> context) {
		tribefire.extension.appconfiguration.model.deployment.ExportLocalizationsToSpreadsheetProcessor deployable = context.getDeployable();

		ExportLocalizationsToSpreadsheetProcessor bean = new ExportLocalizationsToSpreadsheetProcessor();
		bean.setSessionSupplier(() -> tfPlatform.systemUserRelated().sessionFactory().newSession(deployable.getAccessId()));
		return bean;
	}
	
}
