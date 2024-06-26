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

import static org.fest.util.ToString.toStringOf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.fest.assertions.MapAssert;

/**
 * An extended version of {@link MapAssert}. Contains some new methods and also adds some equivalent versions of existing methods (like
 * {@link ExtendedMapAssert#isNotNullExt()}), which simply invoke the corresponding <tt>super</tt> implementation, but return the result with the
 * right type.
 *
 * @see ExtendedMapAssert#containsKeys(Object...)
 */
public class ExtendedMapAssert extends MapAssert {

	private static final String KEY = "key";
	private static final String KEYS = "keys";
	private static final String VALUE = "value";
	private static final String VALUES = "values";

	protected ExtendedMapAssert(final Map<?, ?> actual) {
		super(actual);
	}

	public ExtendedMapAssert containsKeys(final Object... keys) {
		isNotNull();
		validate(KEYS, keys);

		final List<Object> notFound = new ArrayList<>();
		for (final Object e : keys) {
			if (!containsKey(e)) {
				notFound.add(e);
			}
		}

		if (!notFound.isEmpty()) {
			failIfKeyNotFound(adjustIfPlural(KEY, notFound), notFound);
		}

		return this;
	}

	public ExtendedMapAssert containsValues(final Object... values) {
		isNotNull();
		validate(VALUES, values);

		final List<Object> notFound = new ArrayList<>();
		for (final Object e : values) {
			if (!containsValue(e)) {
				notFound.add(e);
			}
		}

		if (!notFound.isEmpty()) {
			failIfKeyNotFound(adjustIfPlural(VALUE, notFound), notFound);
		}

		return this;
	}

	// #####################################
	// ## . . . . . "Overrides" . . . . . ##
	// #####################################

	public ExtendedMapAssert isNotNullExt() {
		return (ExtendedMapAssert) super.isNotNull();
	}

	public ExtendedMapAssert isNotEmptyExt() {
		return (ExtendedMapAssert) super.isNotEmpty();
	}

	// #####################################
	// ## . . . . . . Helpers . . . . . . ##
	// #####################################

	private void validate(final String description, final Object[] objects) {
		if (objects == null) {
			throw new NullPointerException(formattedErrorMessage(String.format("The given array of %s should not be null", description)));
		}
	}

	private boolean containsKey(final Object e) {
		return this.actual.containsKey(e);
	}

	private boolean containsValue(final Object e) {
		return this.actual.containsValue(e);
	}

	private void failIfKeyNotFound(final String description, final Collection<?> notFound) {
		failIfCustomMessageIsSet();
		fail(String.format("the map:<%s> does not contain the %s:<%s>", formattedActual(), description, toStringOf(notFound)));
	}

	protected String formattedActual() {
		return toStringOf(this.actual);
	}

	private static String adjustIfPlural(String s, final List<?> found) {
		return found.size() == 1 ? s : s + "s";
	}
}
