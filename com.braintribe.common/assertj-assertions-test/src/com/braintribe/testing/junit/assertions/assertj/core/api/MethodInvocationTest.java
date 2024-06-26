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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThatExecuting;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.Slow;
import com.braintribe.utils.CommonTools;

/**
 * Provides {@link MethodInvocationAssert} related tests.
 *
 * @author michael.lafite
 */
public class MethodInvocationTest {

	// note that this is slow on devqa machine
	@Category(Slow.class)
	@Test
	public void testAssertThatExecuting() {
		final RuntimeException runtimeException = new IllegalArgumentException("runtimeexception");
		final Exception exception = new IOException("exception");
		final Error error = new AssertionError("error");

		assertThatExecuting(() -> dummy()).succeeds();
		assertThatExecuting(() -> dummy2()).succeeds();
		assertThatExecuting(() -> dummy(exception)).fails();
		assertThatExecuting(() -> dummy(runtimeException)).fails();
		assertThatExecuting(() -> dummy2(runtimeException)).fails();

		assertThatExecuting(() -> dummy(exception)).finishes().throwing(IOException.class);
		assertThatExecuting(() -> dummy(exception)).fails().with(IOException.class);

		assertThatExecuting(() -> dummy(error)).finishes().throwing(Error.class);

		assertThatExecuting(() -> dummy(10)).succeeds().inLessThan(20, ChronoUnit.MILLIS);
		assertThatExecuting(() -> dummy(10)).succeeds().inMoreThan(8, ChronoUnit.MILLIS);
		assertThatExecuting(() -> dummy(10)).succeeds().inBetween(5, 20, ChronoUnit.MILLIS);

		assertThatExecuting(() -> dummy(10)).once().finishes().afterLessThan(15, ChronoUnit.MILLIS);
		assertThatExecuting(() -> dummy(10)).twice().finishes().afterMoreThan(15, ChronoUnit.MILLIS);

		assertThatExecuting(() -> dummy(5)).nTimes(200).finishes().afterMoreThan(1, ChronoUnit.SECONDS);

		assertThatExecuting(() -> dummy(10, runtimeException)).tenTimes().sequentially().fails().afterMoreThan(100, ChronoUnit.MILLIS)
				.throwingExceptionWhich().isInstanceOf(RuntimeException.class).hasMessageStartingWith("runtime");

		// use feature to assert our own assertion errors
		assertThatExecuting(() -> assertThatExecuting(() -> dummy(exception)).succeeds()).fails().with(AssertionError.class);
		assertThatExecuting(() -> assertThatExecuting(() -> dummy()).fails()).fails().with(AssertionError.class);
		assertThatExecuting(() -> assertThatExecuting(() -> dummy(10)).succeeds().inLessThan(5, ChronoUnit.MILLIS)).fails().throwingThrowableWhich()
				.isInstanceOfAny(AssertionError.class).hasMessageMatching(".*Execution took .* which is more than .*");
	}

	private void dummy() throws Throwable {
		dummy(0);
	}

	private void dummy(long delayInMillis) throws Throwable {
		dummy(delayInMillis, null);
	}

	private void dummy(Throwable throwable) throws Throwable {
		dummy(0, throwable);
	}

	private void dummy(long delayInMillis, Throwable throwable) throws Throwable {
		CommonTools.sleep(delayInMillis);
		if (throwable != null) {
			throw throwable;
		}
	}

	private Integer dummy2() {
		return dummy2(0);
	}

	private Integer dummy2(long delayInMillis) {
		return dummy2(delayInMillis, null);
	}

	private Integer dummy2(RuntimeException exception) {
		return dummy2(0, exception);
	}

	private Integer dummy2(long delayInMillis, RuntimeException exception) {
		CommonTools.sleep(delayInMillis);
		if (exception != null) {
			throw exception;
		}
		return 123;
	}
}
