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
package com.braintribe.utils.collection.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;

/**
 * @author peter.gazdik
 */
public class ArrayStackTests {

	private final ArrayStack<Integer> stack = new ArrayStack<>();

	@Test
	public void emptyStackWorksFine() throws Exception {
		assertThat(stack.isEmpty()).isTrue();

		Iterator<Integer> it = stack.iterator();
		assertThat(it).isNotNull();
		assertThat(it.hasNext()).isFalse();
	}

	@Test
	public void canPush() throws Exception {
		stack.push(2);
		stack.push(1);
		stack.push(0);

		assertThat(stack.peek()).isEqualTo(0);

		// check iterates in correct order
		Iterator<Integer> it = stack.iterator();
		for (int i = 0; i <= 2; i++) {
			assertThat(it.hasNext()).isTrue();
			assertThat(it.next()).isEqualTo(i);
		}
		assertThat(it.hasNext()).isFalse();
	}

	@Test
	public void canPushPop() throws Exception {
		stack.push(2);
		stack.push(1);
		stack.push(0);

		for (int i = 0; i <= 2; i++) {
			assertThat(stack.peek()).isEqualTo(i);
			assertThat(stack.pop()).isEqualTo(i);
		}

		assertThat(stack.isEmpty()).isTrue();
	}

	@Test(expected = NoSuchElementException.class)
	public void exceptionOnEmptyPop() throws Exception {
		stack.pop();
	}
}
