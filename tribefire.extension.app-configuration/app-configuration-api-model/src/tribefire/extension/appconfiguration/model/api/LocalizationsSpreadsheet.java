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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

import tribefire.extension.appconfiguration.model.AppConfiguration;

/**
 * A spreadsheet containing {@link AppConfiguration#getLocalizations() localizations}, e.g. as a result of {@link ExportLocalizationsToSpreadsheet}.
 */
public interface LocalizationsSpreadsheet extends GenericEntity {

	EntityType<LocalizationsSpreadsheet> T = EntityTypes.T(LocalizationsSpreadsheet.class);

	/**
	 * The spreadsheet resource.
	 */
	Resource getSpreadsheet();
	void setSpreadsheet(Resource spreadsheet);
}
