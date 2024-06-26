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
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.IOTools;

public class ReaderInputStreamTest {

	@Test
	public void testReaderInputStreamSimple() throws Exception {

		String text = "Hello, world!";
		StringReader reader = new StringReader(text);
		ReaderInputStream in = new ReaderInputStream(reader);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOTools.pump(in, baos);

		String target = baos.toString("UTF-8");
		Assert.assertEquals(text, target);
	}

	@Test
	public void testReaderInputStreamLarge() throws Exception {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10000; ++i) {
			sb.append("Hello, world!");
		}
		String text = sb.toString();

		StringReader reader = new StringReader(text);
		ReaderInputStream in = new ReaderInputStream(reader);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOTools.pump(in, baos);

		String target = baos.toString("UTF-8");
		Assert.assertEquals(text, target);
	}

	@Test
	public void testReaderInputStreamEmpty() throws Exception {

		String text = "";

		StringReader reader = new StringReader(text);
		ReaderInputStream in = new ReaderInputStream(reader);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		IOTools.pump(in, baos);

		String target = baos.toString("UTF-8");
		Assert.assertEquals(text, target);
	}
}
