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

import org.fest.assertions.GenericAssert;

/**
 * Assertion for {@code Class<?>}.
 */
public class ClassAssert extends GenericAssert<ClassAssert, Class<?>> {

	public ClassAssert(final Class<?> clazz) {
		super(ClassAssert.class, clazz);
	}

	public ClassAssert isClass(final Class<?> clazz) {
		return isEqualTo(clazz);
	}

	public ClassAssert isInterface() {
		if (!actual.isInterface()) {
			fail("Class " + actual.getName() + " is not an interface.");
		}
		return myself;
	}

	public ClassAssert isPrimitive() {
		if (!actual.isPrimitive()) {
			fail("Class " + actual.getName() + " is not primitive.");
		}
		return myself;
	}

	public ClassAssert isAssignableFrom(final Class<?> clazz) {
		failIfFirstNotAssignableFromSecond(this.actual, clazz);
		return myself;
	}

	public ClassAssert isAssignableTo(final Class<?> clazz) {
		failIfFirstNotAssignableFromSecond(clazz, this.actual);
		return myself;
	}

	private void failIfFirstNotAssignableFromSecond(final Class<?> c1, final Class<?> c2) {
		if (!c1.isAssignableFrom(c2)) {
			fail("Class " + c1.getName() + " is not assignable from: " + c2.getName());
		}
	}
}
