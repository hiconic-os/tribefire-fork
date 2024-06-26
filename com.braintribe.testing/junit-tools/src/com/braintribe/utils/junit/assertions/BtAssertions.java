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

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.fest.assertions.Assertions;

/**
 * Extension of {@link Assertions} class.
 *
 * @author peter.gazdik
 */
public class BtAssertions extends Assertions {

	/**
	 * Creates a new instance of {@link ClassAssert}.
	 *
	 * @param actual
	 *            the value to be the target of the assertions methods.
	 * @return the created assertion object
	 */
	public static ClassAssert assertThat(final Class<?> actual) {
		return new ClassAssert(actual);
	}

	/**
	 * Creates a new instance of {@link ExtendedFileAssert}.
	 *
	 * @param actual
	 *            the value to be the target of the assertions methods.
	 * @return the created assertion object
	 */
	public static ExtendedFileAssert assertThat(final File actual) {
		return new ExtendedFileAssert(actual);
	}

	/**
	 * Creates a new instance of {@link ExtendedLongAssert}.
	 *
	 * @param actual
	 *            the value to be the target of the assertions methods.
	 * @return the created assertion object
	 */
	public static ExtendedLongAssert assertThat(final long actual) {
		return new ExtendedLongAssert(actual);
	}

	/**
	 * Creates a new instance of {@link ExtendedLongAssert}.
	 *
	 * @param actual
	 *            the value to be the target of the assertions methods.
	 * @return the created assertion object
	 */
	public static ExtendedLongAssert assertThat(final Long actual) {
		return new ExtendedLongAssert(actual);
	}

	/** Returns a new instance of {@link ExtendedMapAssert}. */
	public static ExtendedMapAssert assertThat(final Map<?, ?> actual) {
		return new ExtendedMapAssert(actual);
	}

	/**
	 * Creates a new instance of {@link ExtendedStringAssert}.
	 */
	public static ExtendedStringAssert assertThat(final String actual) {
		return new ExtendedStringAssert(actual);
	}

	/**
	 * Creates a new instance of {@link InputStreamAssert}.
	 *
	 * @param actual
	 *            the value to be the target of the assertions methods.
	 * @return the created assertion object
	 */
	public static InputStreamAssert assertThat(final InputStream actual) {
		return new InputStreamAssert(actual);
	}

}
