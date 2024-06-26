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
package com.braintribe.testing.junit.assertions.assertj.core.api;

import static com.braintribe.testing.junit.assertions.assertj.core.api.SharedAssert.failWithMessage;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractCharSequenceAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.internal.Objects;

import com.braintribe.common.StringDiff;
import com.braintribe.logging.Logger.LogLevel;
import com.braintribe.utils.StringTools;
import com.braintribe.utils.lcd.NullSafe;

/**
 * Provides custom {@link CharSequence} asserts used by {@link ExtendedCharSequenceAssert} and {@link ExtendedStringAssert}. The interface is
 * required, because {@link ExtendedStringAssert} otherwise would have to extend two classes (one that holds the methods of this interface and
 * {@link AbstractStringAssert}), which is not possible in Java.
 *
 * @author michael.lafite
 */
public interface SharedCharSequenceAssert<S extends AbstractAssert<S, A>, A extends CharSequence> extends SharedAssert<S, A> {

	/**
	 * Asserts that the sequence contains all <code>searchedSequences</code> (order is not important).
	 */
	default S containsAll(final CharSequence... searchedSequences) {
		Objects.instance().assertNotNull(info(), actual());
		for (CharSequence searchedSequence : NullSafe.array(searchedSequences)) {
			((AbstractCharSequenceAssert<?, ?>) this).contains(searchedSequence);
		}
		return (S) this;
	}

	/**
	 * Asserts that the number of occurrences of the <code>searchedSequence</code> is greater than or equal
	 * <code>minExpectedNumberOfOccurrences</code> and less than or equal <code>maxExpectedNumberOfOccurrences</code>.
	 */
	default S containsNTimes(final CharSequence searchedSequence, Integer minExpectedNumberOfOccurrences, Integer maxExpectedNumberOfOccurrences) {
		Objects.instance().assertNotNull(info(), actual());
		int actualNumberOfOccurrences = StringTools.countOccurrences(actual().toString(), searchedSequence.toString());
		if (minExpectedNumberOfOccurrences != null && actualNumberOfOccurrences < minExpectedNumberOfOccurrences) {
			failWithMessage((S) this, "Unexpected number of occurrences of '" + searchedSequence + "' in '" + actual() + "': actual="
					+ actualNumberOfOccurrences + ", min expected=" + minExpectedNumberOfOccurrences);
		}
		if (maxExpectedNumberOfOccurrences != null && actualNumberOfOccurrences > maxExpectedNumberOfOccurrences) {
			failWithMessage((S) this, "Unexpected number of occurrences of '" + searchedSequence + "' in '" + actual() + "': actual="
					+ actualNumberOfOccurrences + ", max expected=" + minExpectedNumberOfOccurrences);
		}
		return (S) this;
	}

	/**
	 * Asserts that the number of occurrences of the <code>searchedSequence</code> matches exactly the specified
	 * <code>expectedNumberOfOccurrences</code>.
	 */
	default S containsNTimes(final CharSequence searchedSequence, int expectedNumberOfOccurrences) {
		return containsNTimes(searchedSequence, expectedNumberOfOccurrences, expectedNumberOfOccurrences);
	}

	/**
	 * Asserts that the number of occurrences of the <code>searchedSequence</code> is greater than or equal
	 * <code>minExpectedNumberOfOccurrences</code>.
	 */
	default S containsAtLeastNTimes(final CharSequence searchedSequence, int minExpectedNumberOfOccurrences) {
		return containsNTimes(searchedSequence, minExpectedNumberOfOccurrences, null);
	}

	/**
	 * Asserts that the number of occurrences of the <code>searchedSequence</code> is less than or equal <code>maxExpectedNumberOfOccurrences</code>.
	 */
	default S containsAtMostNTimes(final CharSequence searchedSequence, int maxExpectedNumberOfOccurrences) {
		return containsNTimes(searchedSequence, null, maxExpectedNumberOfOccurrences);
	}

	/**
	 * Similar to {@link SharedAssert#isEqualToWithVerboseErrorMessage(Object)}, but in addition provides detailed information about the first
	 * difference found in the compared <code>CharSequence</code>s. This also includes the character codes (which can be helpful for invisible
	 * characters, e.g. different line separators).
	 *
	 * @see #isEqualToWithVerboseErrorMessageAndLogging(CharSequence)
	 */
	@Override
	default S isEqualToWithVerboseErrorMessage(final CharSequence expected) {
		String errorMessage = createVerboseErrorMessageIfObjectsAreNotEqual(actual(), expected);
		if (errorMessage != null) {
			failWithMessage((S) this, errorMessage);
		}
		return (S) this;
	}

	/**
	 * This is similar to {@link #isEqualToWithVerboseErrorMessage(CharSequence)}, but it also logs the info on {@link LogLevel#ERROR ERROR} level
	 * using the <code>Assert</code>s {@link SharedAssert#logger(AbstractAssert) Logger}.
	 */
	@Override
	default S isEqualToWithVerboseErrorMessageAndLogging(final CharSequence expected) {
		String errorMessage = createVerboseErrorMessageIfObjectsAreNotEqual(actual(), expected);
		if (errorMessage != null) {
			logger().error(errorMessage);
			failWithMessage((S) this, errorMessage);
		}
		return (S) this;
	}

	/**
	 * Similar to {@link SharedAssert#createVerboseErrorMessageIfObjectsAreNotEqual(Object, Object)}, but adds detailed information about the first
	 * difference.
	 */
	static <A> String createVerboseErrorMessageIfObjectsAreNotEqual(A actual, A expected) {
		String errorMessage = SharedAssert.createVerboseErrorMessageIfObjectsAreNotEqual(actual, expected);
		if (errorMessage != null) {
			errorMessage += "\n" + new StringDiff().compare(NullSafe.toString(actual), NullSafe.toString(expected)).getFirstDifferenceDescription();
		}
		return errorMessage;
	}
}
