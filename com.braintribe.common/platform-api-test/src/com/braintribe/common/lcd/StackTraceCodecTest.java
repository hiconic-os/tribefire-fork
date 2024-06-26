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
package com.braintribe.common.lcd;

import static com.braintribe.common.lcd.StackTraceCodec.INSTANCE;

import org.junit.Assert;
import org.junit.Test;

public class StackTraceCodecTest {

	@Test
	public void test() {

		StackTraceElement[] original = Thread.currentThread().getStackTrace();

		String encoded = INSTANCE.encode(original);

		StackTraceElement[] decoded = INSTANCE.decode(encoded);

		Assert.assertEquals(original.length, decoded.length);

		for (int i = 0; i < original.length; i++) {

			StackTraceElement originalElement = original[i];

			StackTraceElement decodedElement = decoded[i];

			Assert.assertEquals(originalElement.getClassName(), decodedElement.getClassName());
			Assert.assertEquals(originalElement.getMethodName(), decodedElement.getMethodName());
			Assert.assertEquals(originalElement.getFileName(), decodedElement.getFileName());
			Assert.assertEquals(originalElement.getLineNumber(), decodedElement.getLineNumber());
			Assert.assertEquals(originalElement.isNativeMethod(), decodedElement.isNativeMethod());

		}

	}

	@Test
	public void testNullArray() {

		StackTraceElement[] original = null;

		String encoded = INSTANCE.encode(original);

		Assert.assertNull(encoded);

		StackTraceElement[] decoded = INSTANCE.decode(encoded);

		Assert.assertNull(decoded);

	}

	@Test
	public void testEmptyArray() {

		StackTraceElement[] original = {};

		String encoded = INSTANCE.encode(original);

		Assert.assertNull(encoded);

		StackTraceElement[] decoded = INSTANCE.decode(encoded);

		Assert.assertNull(decoded);

		decoded = INSTANCE.decode("");

		Assert.assertNull(decoded);

	}

	@Test
	public void testEmptyString() {

		StackTraceElement[] decoded = INSTANCE.decode("");

		Assert.assertNull(decoded);

	}

	@Test
	public void testIllegalStrings() {

		StackTraceElement[] decoded = INSTANCE.decode("Class:method\nClass:method:file");

		Assert.assertNull(decoded);

		decoded = INSTANCE.decode("\n\n\n\n\n\n");

		Assert.assertNull(decoded);

		decoded = INSTANCE.decode("\n\n\nClass:method\nClass:method:file\n\n\n");

		Assert.assertNull(decoded);

	}

}
