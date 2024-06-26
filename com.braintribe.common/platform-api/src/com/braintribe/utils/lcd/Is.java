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
package com.braintribe.utils.lcd;

import java.util.Collection;
import java.util.Map;

import com.braintribe.common.lcd.AssertionException;

/**
 * Helper class used to conveniently assert that some condition is met. Examples:
 *
 * <pre>
 * Is.True(myBoolean);
 * Is.blank(myString);
 * Is.Null(myObject);
 * </pre>
 * <p>
 * Class and method names are intentionally very short so that method calls can be used inline without having too much impact on readability:
 *
 * <pre>
 * addElements(Is.empty(myCollection));
 * addEntries(Is.empty(myMap));
 * </pre>
 *
 * All methods throw an {@link AssertionException}, if the check fails.
 *
 * @author michael.lafite
 *
 * @see Not
 */
// if you add a method to this class, please consider adding a similar method to com.braintribe.utils.lcd.Not.
public class Is {

	/**
	 * Asserts the passed <code>value</code> is <code>true</code>.
	 */
	public static Boolean TRUE(Boolean value) {
		if (!value) {
			throw new AssertionException("The passed boolean must be true, but is " + value + "!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is <code>true</code>.
	 */
	public static Boolean True(Boolean value) {
		if (!value) {
			throw new AssertionException("The passed boolean must be true, but is " + value + "!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is <code>true</code>.
	 */
	public static Boolean true_(Boolean value) {
		if (!value) {
			throw new AssertionException("The passed boolean must be true, but is " + value + "!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is <code>false</code>.
	 */
	public static Boolean FALSE(Boolean value) {
		if (value) {
			throw new AssertionException("The passed boolean must be false, but is " + value + "!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is <code>false</code>.
	 */
	public static Boolean False(Boolean value) {
		if (value) {
			throw new AssertionException("The passed boolean must be false, but is " + value + "!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is <code>false</code>.
	 */
	public static Boolean false_(Boolean value) {
		if (value) {
			throw new AssertionException("The passed boolean must be false, but is " + value + "!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>object</code> is <code>null</code>.
	 */
	public static <T> T NULL(T object) {
		if (object != null) {
			throw new AssertionException("The passed object must be null, but is " + object + "!");
		}
		return object;
	}

	/**
	 * Asserts the passed <code>object</code> is <code>null</code>.
	 */
	public static <T> T Null(T object) {
		if (object != null) {
			throw new AssertionException("The passed object must be null, but is " + object + "!");
		}
		return object;
	}

	/**
	 * Asserts the passed <code>object</code> is <code>null</code>.
	 */
	public static <T> T null_(T object) {
		if (object != null) {
			throw new AssertionException("The passed object must be null, but is " + object + "!");
		}
		return object;
	}

	/**
	 * Asserts the passed <code>string</code> is {@link CommonTools#isEmpty(String) empty}.
	 */
	public String empty(String string) {
		if (!CommonTools.isEmpty(string)) {
			throw new AssertionException("The passed string must be empty, but is '" + string + "'!");
		}
		return string;
	}

	/**
	 * Asserts the passed <code>string</code> is {@link CommonTools#isBlank(String) blank}.
	 */
	public String blank(String string) {
		if (!CommonTools.isBlank(string)) {
			throw new AssertionException("The passed string must be blank, but is '" + string + "'!");
		}
		return string;
	}

	/**
	 * Asserts the passed <code>collection</code> is {@link CommonTools#isEmpty(Collection) empty}.
	 */
	public <T extends Collection<?>> T empty(T collection) {
		if (!CommonTools.isEmpty(collection)) {
			throw new AssertionException("The passed collection must be empty, but has size " + collection.size() + ".");
		}
		return collection;
	}

	/**
	 * Asserts the passed <code>map</code> is {@link CommonTools#isEmpty(Map) empty}.
	 */
	public <T extends Map<?, ?>> T empty(T map) {
		if (!CommonTools.isEmpty(map)) {
			throw new AssertionException("The passed map must be empty, but has size " + map.size() + ".");
		}
		return map;
	}
}
