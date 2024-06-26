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
package com.braintribe.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link ExceptionBuilder}.
 *
 */
public class ExceptionBuilderTest {

	private static Throwable cause = new IllegalArgumentException("Cause message " + System.currentTimeMillis());
	private static String message = "Exception message " + System.currentTimeMillis();

	@Test
	public void testStandardConstructor() {

		Throwable result = ExceptionBuilder.createException(StandardConstructor.class.getName(), message);

		Assert.assertEquals(StandardConstructor.class, result.getClass());
		Assert.assertEquals(message, result.getMessage());

	}

	@Test
	public void testStandardConstructorWithCause() {

		Throwable result = ExceptionBuilder.createException(StandardConstructor.class.getName(), message, cause);

		Assert.assertEquals(StandardConstructor.class, result.getClass());
		Assert.assertEquals(message, result.getMessage());
		Assert.assertNotNull(result.getCause());
		Assert.assertEquals(cause.getClass(), result.getCause().getClass());

	}

	@Test
	public void testCauseConstructor() {

		Throwable result = ExceptionBuilder.createException(CauseConstructor.class.getName(), message);

		Assert.assertEquals(CauseConstructor.class, result.getClass());
		Assert.assertNull(result.getMessage());

	}

	@Test
	public void testCauseConstructorWithCause() {

		Throwable result = ExceptionBuilder.createException(CauseConstructor.class.getName(), message, cause);

		Assert.assertEquals(CauseConstructor.class, result.getClass());
		Assert.assertEquals(cause.getClass().getName() + ": " + cause.getMessage(), result.getMessage());
		Assert.assertNotNull(result.getCause());
		Assert.assertEquals(cause.getClass(), result.getCause().getClass());

	}

	@Test
	public void testMessageConstructor() {

		Throwable result = ExceptionBuilder.createException(MessageConstructor.class.getName(), message);

		Assert.assertEquals(MessageConstructor.class, result.getClass());
		Assert.assertEquals(message, result.getMessage());

	}

	@Test
	public void testMessageConstructorWithCause() {

		Throwable result = ExceptionBuilder.createException(MessageConstructor.class.getName(), message, cause);

		Assert.assertEquals(MessageConstructor.class, result.getClass());
		Assert.assertEquals(message, result.getMessage());
		Assert.assertNotNull(result.getCause());
		Assert.assertEquals(cause.getClass(), result.getCause().getClass());

	}

	@Test
	public void testNoArgsConstructor() {

		Throwable result = ExceptionBuilder.createException(NoArgsConstructor.class.getName(), message);

		Assert.assertEquals(NoArgsConstructor.class, result.getClass());
		Assert.assertNull(result.getMessage());

	}

	@Test
	public void testNoArgsConstructorWithCause() {

		Throwable result = ExceptionBuilder.createException(NoArgsConstructor.class.getName(), message, cause);

		Assert.assertEquals(NoArgsConstructor.class, result.getClass());
		Assert.assertNull(result.getMessage());
		Assert.assertNotNull(result.getCause());
		Assert.assertEquals(cause.getClass(), result.getCause().getClass());

	}

	@Test
	public void testNoEligibleConstructor() {

		Throwable result = ExceptionBuilder.createException(NoEligibleConstructor.class.getName(), message);

		Assert.assertEquals(Exception.class, result.getClass());
		Assert.assertEquals(NoEligibleConstructor.class.getName() + ": " + message, result.getMessage());

	}

	@Test
	public void testNoEligibleConstructorWithCause() {

		Throwable result = ExceptionBuilder.createException(NoEligibleConstructor.class.getName(), message, cause);

		Assert.assertEquals(Exception.class, result.getClass());
		Assert.assertEquals(NoEligibleConstructor.class.getName() + ": " + message, result.getMessage());
		Assert.assertNotNull(result.getCause());
		Assert.assertEquals(cause.getClass(), result.getCause().getClass());

	}

	public static class StandardConstructor extends Exception {

		private static final long serialVersionUID = 1L;

		public StandardConstructor(String message, Throwable cause) {
			super(message, cause);
		}

	}

	public static class MessageConstructor extends Exception {

		private static final long serialVersionUID = 1L;

		public MessageConstructor(String message) {
			super(message);
		}

	}

	public static class CauseConstructor extends Exception {

		private static final long serialVersionUID = 1L;

		public CauseConstructor(Throwable cause) {
			super(cause);
		}

	}

	public static class NoArgsConstructor extends Exception {

		private static final long serialVersionUID = 1L;

	}

	public static class NoEligibleConstructor extends Exception {

		private static final long serialVersionUID = 1L;

		public NoEligibleConstructor(@SuppressWarnings("unused") Boolean b) {
			Assert.fail("Shouldn't be choosen");
		}

	}

}
