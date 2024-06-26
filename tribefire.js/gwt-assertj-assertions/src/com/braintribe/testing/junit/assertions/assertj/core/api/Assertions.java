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

import java.io.File;

import org.assertj.core.api.AssertionsForClassTypes;
import org.assertj.core.api.AssertionsForInterfaceTypes;

import com.braintribe.testing.junit.assertions.assertj.core.api.MethodInvocationAssert.ExecutableWithReturnValue;
import com.braintribe.testing.junit.assertions.assertj.core.api.MethodInvocationAssert.ExecutableWithoutReturnValue;
import com.braintribe.testing.junit.assertions.assertj.core.api.MethodInvocationAssert.MethodInvocationSettings;

/**
 * Entry point for all custom assertions as well as {@link org.assertj.core.api.Assertions standard AssertJ assertions}.
 * Just add a static import ...
 *
 * <pre>
 * <code class=
 * 'java'>import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;</code>
 * </pre>
 *
 * ... and then in your test methods write assertions starting with <code>assertThat</code>:
 *
 * <pre>
 * assertThat("").isEmpty();</code>
 * </pre>
 * <p>
 * In some special cases with generics the Java compiler may require a cast to be able to infer the type, although the
 * type may be obvious. For example, if a method returns <code>T extends Exception</code> and the result of this method
 * is passed to <code>assertThat</code>, the compiler needs a cast, because T could also implement an interface (e.g.
 * {@link CharSequence}. AssertJ therefore splits into {@link AssertionsForClassTypes} and
 * {@link AssertionsForInterfaceTypes}. By statically importing <code>assertThat</code> from those classes (instead of a
 * single static import from this class), casts are no longer required. However, this doesn't work with the Oracle Java
 * compiler (version 1.8.0_112). It also doesn't seem to be best solution. Casting when required seems to be reasonable.
 * Therefore this class serves as a single entry point for interface and class based assertions.
 *
 * @author michael.lafite
 *
 * @see AssertionsForClassTypes
 * @see AssertionsForInterfaceTypes
 */
public abstract class Assertions extends org.assertj.core.api.Assertions {

	/**
	 * Creates a {@link CharSequence} assertion.
	 */
	public static ExtendedCharSequenceAssert assertThat(CharSequence actual) {
		return new ExtendedCharSequenceAssert(actual);
	}

	/**
	 * Creates a {@link Class} assertion.
	 */
	public static ExtendedClassAssert assertThat(Class<?> actual) {
		return new ExtendedClassAssert(actual);
	}

	/**
	 * Creates a {@link File} assertion.
	 */
	public static ExtendedFileAssert assertThat(File actual) {
		return new ExtendedFileAssert(actual);
	}

	/**
	 * Creates a {@link String} assertion.
	 */
	public static ExtendedStringAssert assertThat(String actual) {
		return new ExtendedStringAssert(actual);
	}

	/**
	 * Creates an assertion for a method execution which is usually specified as lambda expression. For examples, see
	 * {@link MethodInvocationAssert}.
	 */
	public static <T> MethodInvocationSettings<T> assertThatExecuting(ExecutableWithReturnValue<T> executable) {
		MethodInvocationAssert<T> methodInvocationAssert = new MethodInvocationAssert<>(executable);
		return methodInvocationAssert.configure();
	}

	/**
	 * Creates an assertion for a method execution which is usually specified as lambda expression. For examples, see
	 * {@link MethodInvocationAssert}.
	 */
	public static MethodInvocationSettings<Void> assertThatExecuting(ExecutableWithoutReturnValue executable) {
		MethodInvocationAssert<Void> methodInvocationAssert = new MethodInvocationAssert<>(executable);
		return methodInvocationAssert.configure();
	}
}
