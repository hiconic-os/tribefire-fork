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
package tribefire.extension.appconfiguration.processing.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import tribefire.extension.appconfiguration.model.AppLocalization;
import tribefire.extension.appconfiguration.model.AppLocalizationEntry;

/**
 * Provides methods to {@link #readLocalizationsFromTable(AppConfigurationTable) read} / {@link #writeLocalizationsToTable(List) write}
 * {@link AppLocalization}s from / to an {@link AppConfigurationTable}. In combination with {@link TableSpreadsheetIO} this can be used to enable
 * users to provide and maintain respective localization data via a spreadsheet.
 */
public class AppLocalizationTableIO {

	private AppLocalizationTableIO() {
		// no need to instantiate
	}

	/**
	 * Reads from the passed <code>tables</code> and returns the respective {@link AppLocalization}s. Cells with <code>null</code> values are ignored,
	 * i.e. no {@link AppLocalizationEntry} is created.
	 */
	public static List<AppLocalization> readLocalizationsFromTable(AppConfigurationTable table) {
		List<String> locations = table.denominators();
		List<String> keys = table.keys();

		List<AppLocalization> localizations = new ArrayList<>();

		for (String location : locations) {
			AppLocalization localization = AppLocalization.T.create();
			localization.setActive(true);
			localizations.add(localization);

			localization.setLocation(location);

			for (String key : keys) {
				String value = table.value(location, key);

				if (value != null) {
					AppLocalizationEntry entry = AppLocalizationEntry.T.create();
					localization.getValues().add(entry);

					entry.setKey(key);
					entry.setValue(value);
				}
			}
		}
		return localizations;
	}

	/**
	 * Writes the <code>localizations</code> to a table. If a key/location combination doesn't exist, the respective cell will be set to
	 * <code>null</code>.
	 */
	public static AppConfigurationTable writeLocalizationsToTable(List<AppLocalization> localizations) {
		Set<String> keys = AppLocalizationTools.keys(localizations);

		Set<String> locations = AppLocalizationTools.locations(localizations);

		AppConfigurationTable table = new AppConfigurationTable(new ArrayList<>(locations));

		for (String key : keys) {
			List<String> keyAndLocalizedValues = new ArrayList<>();
			keyAndLocalizedValues.add(key);

			for (String location : locations) {
				AppLocalization localization = localizations.stream().filter(it -> it.getLocation().equals(location)).findAny().orElseThrow();
				AppLocalizationEntry entry = localization.getValues().stream().filter(it -> it.getKey().equals(key)).findAny().orElse(null);
				String value = entry != null ? entry.getValue() : null;
				keyAndLocalizedValues.add(value);
			}

			table.addCellsToNewRow(keyAndLocalizedValues);
		}
		return table;
	}
}