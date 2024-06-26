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

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import tribefire.extension.appconfiguration.model.AppLocalization;
import tribefire.extension.appconfiguration.model.AppLocalizationEntry;

/**
 * Provides various helpers which can be useful when working with (lists of) {@link AppLocalization}s.
 */
public class AppLocalizationTools {

	private AppLocalizationTools() {
		// no need to instantiate
	}

	/**
	 * Returns union of the keys of the passed <code>localizations</code>.
	 */
	public static Set<String> keys(List<AppLocalization> localizations) {
		return localizations.stream() //
				.flatMap(localization -> localization.getValues().stream() // flatten to get values from all localizations
						.map(AppLocalizationEntry::getKey)) // get key of each entry
				.distinct().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Returns the locations of the passed <code>localizations</code>.
	 */
	public static Set<String> locations(List<AppLocalization> localizations) {
		return localizations.stream().map(AppLocalization::getLocation) //
				.distinct().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Returns the localization for the specified <code>location</code> or <code>null</code>, if none of the <code>localizations</code> match.
	 */
	public static AppLocalization localization(List<AppLocalization> localizations, String location) {
		return localizations.stream().filter(it -> location.equals(it.getLocation())).findAny().orElse(null);
	}

	/**
	 * Checks whether the passed <code>localization</code> contains an entry with the specified <code>key</code>.
	 */
	public static boolean containsKey(AppLocalization localization, String key) {
		return localization.getValues().stream().anyMatch(it -> key.equals(it.getKey()));
	}

	/**
	 * Returns the localized value for the specified <code>key</code> or <code>null</code>, if there is no match.
	 */
	public static String localizedValue(AppLocalization localization, String key) {
		AppLocalizationEntry entry = localization.getValues().stream().filter(it -> key.equals(it.getKey())).findAny().orElse(null);
		return entry != null ? entry.getValue() : null;
	}

	/**
	 * Inserts the <code>localization</code> into the list of <code>localizations</code> being aware of the localization order (i.e. order by
	 * location).
	 */
	public static void insertOrdered(AppLocalization localization, List<AppLocalization> localizations) {
		GeneralTools.insertOrdered(localization, localizations, Comparator.comparing(AppLocalization::getLocation));
	}

	/**
	 * Inserts the <code>entry</code> into the list of <code>entries</code> being aware of the entry order (i.e. order by key).
	 */
	public static void insertOrdered(AppLocalizationEntry entry, List<AppLocalizationEntry> entries) {
		GeneralTools.insertOrdered(entry, entries, Comparator.comparing(AppLocalizationEntry::getKey));
	}

	/**
	 * Returns a table string representation of the passed <code>localizations</code>.
	 */
	public static String toTableString(List<AppLocalization> localizations) {
		return AppLocalizationTableIO.writeLocalizationsToTable(localizations).toMultilineString();
	}
}
