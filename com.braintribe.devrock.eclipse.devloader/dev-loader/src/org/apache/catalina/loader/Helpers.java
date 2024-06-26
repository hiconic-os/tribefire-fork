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
package org.apache.catalina.loader;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Set of little helpers, e.g. reading from a file.
 */
public class Helpers {

	private Helpers() {
		// no instantiation required
	}

	static List<String> readFileLines(File file) {
		try {
			return Files.readAllLines(file.toPath());
		} catch (IOException e) {
			throw new UncheckedIOException("Error while reading from file " + file.getAbsolutePath() + ".", e);
		}
	}

	static String readFileContent(File file) {
		return readFileLines(file).stream().collect(Collectors.joining("\n"));
	}

	static String getSubstringBetween(final String string, final String leftSearchString, final String rightSearchString) {

		final int leftSearchStringIndex = string.indexOf(leftSearchString);
		final int rightSearchStringIndex = string.indexOf(rightSearchString);

		if (leftSearchStringIndex < 0) {
			throw new IllegalArgumentException("String '" + leftSearchString + "' not contained in string '" + string + "'!");
		}
		if (rightSearchStringIndex < 0) {
			throw new IllegalArgumentException("String '" + rightSearchString + "' not contained in string '" + string + "'!");
		}
		if (leftSearchStringIndex > rightSearchStringIndex) {
			throw new IllegalArgumentException(
					"String '" + rightSearchString + "' (unexpectedly) found before '" + leftSearchString + "' in string '" + string + "'!");
		}

		String result = string.substring(leftSearchStringIndex + leftSearchString.length(), rightSearchStringIndex);
		return result;
	}

	static <E> List<E> list(E... elements) {
		List<E> list = new ArrayList<>();
		if (elements != null) {
			list.addAll(Arrays.asList(elements));
		}
		return list;
	}

	static boolean getBooleanSystemPropertyValue(String propertyKey, boolean defaultValue) {
		String value = System.getProperty(propertyKey);
		boolean booleanValue;
		if (value != null) {
			if (value.equalsIgnoreCase("true")) {
				booleanValue = true;
			} else if (value.equalsIgnoreCase("false")) {
				booleanValue = false;
			} else {
				throw new RuntimeException("Value of system property '" + propertyKey + "' is '" + value + "', which is not a valid boolean value!");
			}
		} else {
			booleanValue = defaultValue;
		}
		return booleanValue;
	}

	static String normalizePath(File file) {
		return normalizePath(file.getAbsolutePath());
	}

	static String normalizePath(String path) {
		return path.replace('\\', '/');
	}
}
