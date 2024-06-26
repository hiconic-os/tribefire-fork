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

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * A report providing information about an import of {@link tribefire.extension.appconfiguration.model.AppConfiguration#getLocalizations()
 * localizations}, e.g. from a {@link ImportLocalizationsFromSpreadsheet spreadsheet}.
 */
public interface LocalizationsImportReport extends GenericEntity {

	EntityType<LocalizationsImportReport> T = EntityTypes.T(LocalizationsImportReport.class);

	/** A text providing some statistics such as the number of added or modified entries. */
	String getStatistics();
	void setStatistics(String statistics);

	/** Multiple text messages providing detailed information about all localized values and there status (e.g. added, updated, etc.). */
	List<String> getDetails();
	void setDetails(List<String> details);
}
