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
import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThatExecuting;

import org.junit.Test;

import com.braintribe.testing.test.AbstractTest;

/**
 * Provides {@link SharedCharSequenceAssert} related tests.
 *
 * @author michael.lafite
 */
public class SharedCharSequenceAssertTest extends AbstractTest {

	@Test
	public void test() {

		// order irrelevant
		assertThat("abcde").containsAll("c", "de", "a");

		// order relevant
		assertThat("abcde").containsSubsequence("a", "cd", "e");
		assertThatExecuting(() -> assertThat("abcde").containsSubsequence("a", "e", "cd")).fails();

		// order relevant, no elements in between allowed
		assertThat("abcde").containsSequence("a", "b", "cd", "e");
		assertThatExecuting(() -> assertThat("abcde").containsSequence("a", "cd", "e")).fails();

		assertThat("abc").hasSameSizeAs("def");

		assertThat("aaba").containsNTimes("a", 3).containsNTimes("b", 0, 1).containsAtMostNTimes("c", 0);

		assertThatExecuting(() -> assertThat("test\r\n").isEqualToWithVerboseErrorMessage("test\n")).fails().throwingThrowableWhich()
				.isInstanceOf(AssertionError.class).hasMessageContaining("code 13");

		assertThatExecuting(() -> assertThat("test\r\n").isEqualToWithVerboseErrorMessageAndLogging("test\n")).fails().throwingThrowableWhich()
				.isInstanceOf(AssertionError.class).hasMessageContaining("code 13");
	}

}
