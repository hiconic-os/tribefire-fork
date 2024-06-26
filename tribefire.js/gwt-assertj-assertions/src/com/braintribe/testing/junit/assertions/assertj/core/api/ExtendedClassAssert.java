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

import org.assertj.core.api.ClassAssert;
import org.assertj.core.internal.Objects;

import com.braintribe.logging.Logger;

/**
 * Provides custom {@link Class} assertions.
 *
 * @author michael.lafite
 */
public class ExtendedClassAssert extends ClassAssert implements SharedAssert<ClassAssert, Class<?>> {

	@SuppressWarnings("unused") // may be used by SharedAssert methods via reflection
	private static Logger logger = Logger.getLogger(ExtendedStringAssert.class);

	public ExtendedClassAssert(Class<?> actual) {
		super(actual);
	}

	/**
	 * Asserts that the actual {@link Class} {@link Class#getName() name} matches the <code>expectedName</code>.
	 */
	public ExtendedClassAssert hasName(String expectedName) {
		Objects.instance().assertNotNull(info, actual);
		if (!actual.getName().equals(expectedName)) {
			failWithMessage("Class name " + toString(actual.getName()) + " doesn't match expected classname " + toString(expectedName) + ".");
		}
		return this;
	}

	/**
	 * Asserts that the class is {@link Class#isPrimitive() primitive}.
	 */
	public ExtendedClassAssert isPrimitive() {
		Objects.instance().assertNotNull(info, actual);
		if (!actual.isPrimitive()) {
			failWithMessage("Class " + actual.getName() + " should be primitive.");
		}
		return this;
	}

	/**
	 * Asserts that the class is not {@link Class#isPrimitive() primitive}.
	 */
	public ExtendedClassAssert isNotPrimitive() {
		Objects.instance().assertNotNull(info, actual);
		if (actual.isPrimitive()) {
			failWithMessage("Class " + actual.getName() + " should not be primitive.");
		}
		return this;
	}

	/**
	 * Asserts that the class is an {@link Class#isPrimitive() enum}.
	 */
	public ExtendedClassAssert isEnum() {
		Objects.instance().assertNotNull(info, actual);
		if (!actual.isEnum()) {
			failWithMessage("Class " + actual.getName() + " should be an enum.");
		}
		return this;
	}

	/**
	 * Asserts that the class is not an {@link Class#isPrimitive() enum}.
	 */
	public ExtendedClassAssert isNotEnum() {
		Objects.instance().assertNotNull(info, actual);
		if (actual.isEnum()) {
			failWithMessage("Class " + actual.getName() + " should not be an enum.");
		}
		return this;
	}
}
