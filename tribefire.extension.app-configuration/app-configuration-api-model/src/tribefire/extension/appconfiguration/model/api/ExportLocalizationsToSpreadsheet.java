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
package tribefire.extension.appconfiguration.model.api;

import com.braintribe.model.accessapi.AccessRequest;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * Exports {@link tribefire.extension.appconfiguration.model.AppConfiguration#getLocalizations() localizations} to a spreadsheet.
 */
public interface ExportLocalizationsToSpreadsheet extends AccessRequest {

	EntityType<ExportLocalizationsToSpreadsheet> T = EntityTypes.T(ExportLocalizationsToSpreadsheet.class);

	/** The {@link tribefire.extension.appconfiguration.model.AppConfiguration#getName() name} of the configuration to export. */
	String getAppConfigurationName();
	void setAppConfigurationName(String appConfigurationName);

	@Override
	EvalContext<? extends LocalizationsSpreadsheet> eval(Evaluator<ServiceRequest> evaluator);
}
