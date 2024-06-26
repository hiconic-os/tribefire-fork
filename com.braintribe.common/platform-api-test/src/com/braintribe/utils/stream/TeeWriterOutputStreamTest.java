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
package com.braintribe.utils.stream;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

public class TeeWriterOutputStreamTest {

	@Test
	public void testWritingOutputStreamWithClose() throws Exception {

		String text = "Hello, world!";
		byte[] textBytes = text.getBytes("UTF-8");

		ByteArrayOutputStream delegate = new ByteArrayOutputStream();
		StringWriter writer = new StringWriter();
		int maxLogLength = 1000;

		TeeWriterOutputStream wos = new TeeWriterOutputStream(delegate, writer, maxLogLength);
		wos.write(textBytes);

		// Before close() has been invoked and before the limit has been reached, the writer should be empty
		Assert.assertEquals("", writer.toString());

		wos.flush();

		Assert.assertEquals("", writer.toString());
		Assert.assertArrayEquals(textBytes, delegate.toByteArray());

		wos.close();

		Assert.assertArrayEquals(textBytes, delegate.toByteArray());
		Assert.assertEquals(text, writer.toString());

	}

	@Test
	public void testWritingOutputStreamBeforeClose() throws Exception {

		String text = "Hello, world!";
		byte[] textBytes = text.getBytes("UTF-8");

		ByteArrayOutputStream delegate = new ByteArrayOutputStream();
		StringWriter writer = new StringWriter();
		int maxLogLength = 5;

		TeeWriterOutputStream wos = new TeeWriterOutputStream(delegate, writer, maxLogLength);
		wos.write(textBytes);

		// The limit has been reached, the first 5 bytes should be in the text
		Assert.assertEquals("Hello", writer.toString());

		wos.close();

		Assert.assertArrayEquals(textBytes, delegate.toByteArray());
		Assert.assertEquals("Hello", writer.toString());

	}
}
