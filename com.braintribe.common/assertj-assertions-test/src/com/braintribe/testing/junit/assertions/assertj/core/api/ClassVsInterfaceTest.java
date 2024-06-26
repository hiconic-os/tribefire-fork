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
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * Demonstrates the problem with generics which is explained in documentation of {@link org.assertj.core.api.Assertions}.
 *
 * @author michael.lafite
 */
public class ClassVsInterfaceTest {

	@Test
	public void testClassVsInterface() {
		// casting required for type that matches two asserThat (where none is more concrete than the other)
		ThrowableAndCharSequence throwableAndCharSequence = new ThrowableAndCharSequence();
		assertThat((Throwable) throwableAndCharSequence).hasNoCause();
		assertThat((CharSequence) throwableAndCharSequence).isEmpty();

		// same with generics (casting required - java version 1.8.0_112)
		// (documented out because Eclipse cleanup removed the cast)
		// assertThat((Throwable)newException()).hasNoCause();

		// here it works without casting, since no interface asserts are included
		// commented out, because it fails with Java compiler (see below)
		// org.assertj.core.api.AssertionsForClassTypes.assertThat(newException()).hasNoCause();

		/* 2017-02-07: one could statically import AssertionsForClassTypes and AssertionsForInterfaceTypes instead of Assertions. This works in
		 * Eclipse, but still fails in java version 1.8.0_112. */
	}

	@SuppressWarnings("unused")
	private static <T extends Exception> T newException() {
		return (T) new Exception();
	}

	private static class ThrowableAndCharSequence extends Exception implements CharSequence {

		private static final long serialVersionUID = -8797648422313105775L;

		@Override
		public int length() {
			return 0;
		}

		@Override
		public char charAt(int index) {
			return 0;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return null;
		}

	}

}
