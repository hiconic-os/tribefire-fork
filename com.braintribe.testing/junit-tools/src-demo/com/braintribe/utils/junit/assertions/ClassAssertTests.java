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

import static com.braintribe.utils.junit.assertions.BtAssertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

/**
 *
 */
public class ClassAssertTests {

	@Test
	public void demo() {
		assertThat(List.class).isAssignableFrom(ArrayList.class).isAssignableTo(Collection.class);
	}

	// ###################################
	// ## . . . . ACTUAL TESTS . . . . .##
	// ###################################

	@Test(expected = AssertionError.class)
	public void testIsAssignableFrom() {
		assertThat(List.class).isAssignableFrom(Integer.class);
	}

	@Test(expected = AssertionError.class)
	public void testIsAssignableTo() {
		assertThat(List.class).isAssignableTo(Integer.class);
	}

}
