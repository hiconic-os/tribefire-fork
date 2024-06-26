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
 * Helper class used to conveniently assert that some condition is not met. Examples:
 *
 * <pre>
 * Not.True(myBoolean);
 * Not.blank(myString);
 * Not.Null(myObject);
 * </pre>
 * <p>
 * Class and method names are intentionally very short so that method calls can be used inline without having too much
 * impact on readability:
 *
 * <pre>
 * doSomethingWithCollection(Not.empty(myCollection));
 * printMap(Not.empty(myMap));
 * </pre>
 *
 * All methods throw an {@link AssertionException}, if the check fails.
 *
 * @author michael.lafite
 *
 * @see Is
 */
// if you add a method to this class, please consider adding a similar method to com.braintribe.utils.lcd.Is.
public class Not {

	/**
	 * Asserts the passed <code>value</code> is not <code>true</code>.
	 */
	public static Boolean TRUE(Boolean value) {
		if (value == true) {
			throw new AssertionException("The passed boolean must not be true!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is not <code>true</code>.
	 */
	public static Boolean True(Boolean value) {
		if (value == true) {
			throw new AssertionException("The passed boolean must not be true!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is not <code>true</code>.
	 */
	public static Boolean true_(Boolean value) {
		if (value == true) {
			throw new AssertionException("The passed boolean must not be true!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is not <code>false</code>.
	 */
	public static Boolean FALSE(Boolean value) {
		if (value == false) {
			throw new AssertionException("The passed boolean must not be false!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is not <code>false</code>.
	 */
	public static Boolean False(Boolean value) {
		if (value == false) {
			throw new AssertionException("The passed boolean must not be false!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>value</code> is not <code>false</code>.
	 */
	public static Boolean false_(Boolean value) {
		if (value == false) {
			throw new AssertionException("The passed boolean must not be false!");
		}
		return value;
	}

	/**
	 * Asserts the passed <code>object</code> is not <code>null</code>.
	 */
	public static <T> T NULL(T object) {
		if (object == null) {
			throw new AssertionException("The passed object must not be null!");
		}
		return object;
	}

	/**
	 * Asserts the passed <code>object</code> is not <code>null</code>.
	 */
	public static <T> T Null(T object) {
		if (object == null) {
			throw new AssertionException("The passed object must not be null!");
		}
		return object;
	}

	/**
	 * Asserts the passed <code>object</code> is not <code>null</code>.
	 */
	public static <T> T Null(T object, String errorMessage) {
		if (object == null) {
			throw new AssertionException(errorMessage);
		}
		return object;
	}

	/**
	 * Asserts the passed <code>object</code> is not <code>null</code>.
	 */
	public static <T> T null_(T object) {
		if (object == null) {
			throw new AssertionException("The passed object must not be null!");
		}
		return object;
	}

	/**
	 * Asserts the passed <code>string</code> is not {@link CommonTools#isEmpty(String) empty}.
	 */
	public String empty(String string) {
		if (CommonTools.isEmpty(string)) {
			throw new AssertionException("The passed string must not be empty!");
		}
		return string;
	}

	/**
	 * Asserts the passed <code>string</code> is not {@link CommonTools#isBlank(String) blank}.
	 */
	public String blank(String string) {
		if (CommonTools.isBlank(string)) {
			throw new AssertionException("The passed string must not be blank!");
		}
		return string;
	}

	/**
	 * Asserts the passed <code>collection</code> is not {@link CommonTools#isEmpty(Collection) empty}.
	 */
	public <T extends Collection<?>> T empty(T collection) {
		if (CommonTools.isEmpty(collection)) {
			throw new AssertionException("The passed collection must not be empty!");
		}
		return collection;
	}

	/**
	 * Asserts the passed <code>map</code> is not {@link CommonTools#isEmpty(Map) empty}.
	 */
	public <T extends Map<?, ?>> T empty(T map) {
		if (CommonTools.isEmpty(map)) {
			throw new AssertionException("The passed map must not be empty!");
		}
		return map;
	}

}
