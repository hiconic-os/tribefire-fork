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
package com.braintribe.utils;

import org.junit.Assert;
import org.junit.Test;

public class MemorySaveStringWriterTest {

	@Test
	public void testBufferLimit() throws Exception {
		MemorySaveStringWriter writer = new MemorySaveStringWriter(20);
		writer.write("Hello world!");
		writer.write("This is a test to check whether the buffer limit works.");
		writer.close();
		String result = writer.toString();
		Assert.assertEquals("Hello world!This is a test to check whether the buffer limit works.", result);
		writer.destroy();
	}

	@Test
	public void testBufferLimit2() throws Exception {
		MemorySaveStringWriter writer = new MemorySaveStringWriter(20);
		writer.write("Hello world!");
		writer.write("This is a test to check whether the buffer limit works.");
		writer.write("Hello world, again!");
		writer.close();
		String result = writer.toString();
		Assert.assertEquals("Hello world!This is a test to check whether the buffer limit works.Hello world, again!", result);
		writer.destroy();
	}

	@Test
	public void testBufferLimit3() throws Exception {
		MemorySaveStringWriter writer = new MemorySaveStringWriter(20);
		writer.write("Hello world!");
		writer.close();
		String result = writer.toString();
		Assert.assertEquals("Hello world!", result);
		writer.destroy();
	}
}
