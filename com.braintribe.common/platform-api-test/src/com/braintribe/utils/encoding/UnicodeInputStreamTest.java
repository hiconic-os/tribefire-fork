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
package com.braintribe.utils.encoding;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.utils.IOTools;

public class UnicodeInputStreamTest {

	@Test
	public void testUnicodeInputStreamWithBOM() throws Exception {

		this.doTest("UTF-8", (byte) 0xEF, (byte) 0xBB, (byte) 0xBF);
		this.doTest("UTF-32BE", (byte) 0x00, (byte) 0x00, (byte) 0xFE, (byte) 0xFF);
		this.doTest("UTF-32LE", (byte) 0xFF, (byte) 0xFE, (byte) 0x00, (byte) 0x00);
		this.doTest("UTF-16BE", (byte) 0xFE, (byte) 0xFF);
		this.doTest("UTF-16LE", (byte) 0xFF, (byte) 0xFE);

	}

	@Test
	public void testUnicodeInputStreamWithoutBOM() throws Exception {

		this.doTest("UTF-8");

	}

	@Ignore
	protected void doTest(String encoding, byte... prefix) throws Exception {
		String expected = "Hello, world";
		InputStream in = this.createContentStreamWithPrefix(expected, encoding, prefix);
		UnicodeInputStream uis = new UnicodeInputStream(in, "UTF-8");
		String actual = this.getContentBackFromStream(uis);
		String actualEncoding = uis.getEncoding();
		assertThat(actualEncoding).isEqualTo(encoding);
		assertThat(actual).isEqualTo(expected);
	}

	@Ignore
	protected String getContentBackFromStream(UnicodeInputStream uis) throws Exception {
		String encoding = uis.getEncoding();
		if (encoding == null) {
			encoding = "UTF-8";
		}
		String content = IOTools.slurp(uis, encoding);
		return content;
	}

	@Ignore
	protected InputStream createContentStreamWithPrefix(String content, String encoding, byte... prefix) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(prefix);
		baos.write(content.getBytes(encoding));
		byte[] byteArray = baos.toByteArray();
		return new ByteArrayInputStream(byteArray);
	}
}
