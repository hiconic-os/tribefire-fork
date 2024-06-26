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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.ClassAssert;
import org.junit.Test;

import com.braintribe.logging.Logger.LogLevel;

/**
 * Provides {@link ClassAssert} related tests.
 *
 * @author michael.lafite
 */
public class ExtendedClassAssertTest {

	@Test
	public void test() {
		assertThat(Object.class).hasName("java.lang.Object");

		assertThat(Object.class).isNotInterface();
		assertThat(Comparable.class).isInterface();

		assertThat(Object.class).isAssignableFrom(String.class);

		assertThat(LogLevel.class).isEnum();
		assertThat(Integer.class).isNotEnum();

		assertThat(Integer.class).isNotPrimitive();
		assertThat(int.class).isPrimitive();

		assertThat(Void.class).isNotPrimitive();
		assertThat(void.class).isPrimitive();
	}

}
