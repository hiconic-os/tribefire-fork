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
package com.braintribe.utils;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * This class provides utility methods that involve randomly generated numbers, strings, UUIDs, etc. *
 *
 * @author michael.lafite
 */
public final class RandomTools {

	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS").withLocale(Locale.US);

	private RandomTools() {
		// no instantiation required
	}

	/**
	 * Returns a random string of length 32 containing only hexadecimal numbers (much like the GUIDs used in CSP, although the algorithm is not the
	 * same).
	 *
	 * @param dtsPrefixEnabled
	 *            if <code>true</code>, the first 15 characters will contain the timestamp.
	 * @return the random time string.
	 */
	public static String getRandom32CharactersHexString(final boolean dtsPrefixEnabled) {
		String uuidString = UUID.randomUUID().toString().replace("-", "");

		if (dtsPrefixEnabled) {
			uuidString = timeStamp() + uuidString.substring(1, 18);
		}

		return uuidString;
	}

	public static String timeStamp() {
		return DateTools.encode(new Date(), dateFormat);
	}

	/**
	 * Returns the result of {@link #getRandom32CharactersHexString(boolean)} with timestamp prefix enabled.
	 */
	public static String newStandardUuid() {
		return getRandom32CharactersHexString(true);
	}

	/**
	 * Returns a random element from an indicated collection.
	 *
	 * @param elements
	 *            The provided collection.
	 * @return An random element of the collection.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getRandomCollectionElement(final Collection<T> elements) {
		int index = new Random().nextInt(elements.size());
		return (T) elements.toArray()[index];
	}

	/**
	 * Returns a random key from an indicated map collection.
	 *
	 * @param elements
	 *            The provided map collection.
	 * @return An random key of the map collection.
	 */
	public static <K> K getRandomMapKey(final Map<K, ?> elements) {
		return getRandomCollectionElement(elements.keySet());
	}
}
