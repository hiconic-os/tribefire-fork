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
package com.braintribe.utils.junit.assertions;

import org.fest.assertions.Assertions;
import org.fest.assertions.StringAssert;

import com.braintribe.common.StringDiff;
import com.braintribe.logging.Logger;
import com.braintribe.utils.CommonTools;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.lcd.NullSafe;

/**
 * An extension of {@link StringAssert} to provide further assertions not (yet) provided by FEST.
 *
 * @author michael.lafite
 *
 */
public class ExtendedStringAssert extends StringAssert {

	private static Logger logger = Logger.getLogger(ExtendedStringAssert.class);

	public ExtendedStringAssert(final String actual) {
		super(actual);
	}

	/**
	 * Asserts that the string contains all <code>searchedStrings</code>.
	 */
	public StringAssert containsAll(final String... searchedStrings) {
		for (String searchedString : NullSafe.array(searchedStrings)) {
			Assertions.assertThat(super.actual).contains(searchedString);
		}
		return this;
	}

	/**
	 * Asserts that the number of occurrences of the <code>searchedString</code> matches exactly the specified
	 * <code>expectedNumberOfOccurrences</code>.
	 */
	public StringAssert containsNTimes(final String searchedString, int expectedNumberOfOccurrences) {
		int actualNumberOfOccurrences = StringTools.countOccurrences(super.actual, searchedString);
		Assertions.assertThat(actualNumberOfOccurrences).isEqualTo(expectedNumberOfOccurrences);
		return this;
	}

	/**
	 * Similar to {@link #containsNTimes(String, int)}.
	 */
	public StringAssert containsAtLeastNTimes(final String searchedString, int minimumNumberOfOccurrences) {
		int actualNumberOfOccurrences = StringTools.countOccurrences(super.actual, searchedString);
		Assertions.assertThat(actualNumberOfOccurrences).isGreaterThanOrEqualTo(minimumNumberOfOccurrences);
		return this;
	}

	/**
	 * Similar to {@link #containsNTimes(String, int)}.
	 */
	public StringAssert containsAtMostNTimes(final String searchedString, int minimumNumberOfOccurrences) {
		int actualNumberOfOccurrences = StringTools.countOccurrences(super.actual, searchedString);
		Assertions.assertThat(actualNumberOfOccurrences).isLessThanOrEqualTo(minimumNumberOfOccurrences);
		return this;
	}

	/**
	 * Asserts that the two strings are equal. The method prints verbose error info containing both strings (also using line separators), if the
	 * strings are not equal.
	 */
	public StringAssert isEqualToWithVerboseErrorInfo(String expected) {
		if (!CommonTools.equalsOrBothNull(super.actual, expected)) {
			logger.error("Assertion failed: strings are not equal!\nActual:\n" + super.actual + "\nExpected:\n" + expected + "\n"
					+ new StringDiff().compare(super.actual, expected).getFirstDifferenceDescription());
		}
		return super.isEqualTo(expected);
	}

	/**
	 * Asserts that the {@link String#length() length} matches the <code>expected</code> length.
	 */
	public StringAssert hasLength(int expected) {
		Assertions.assertThat(super.actual.length()).isEqualTo(expected);
		return this;
	}
}
