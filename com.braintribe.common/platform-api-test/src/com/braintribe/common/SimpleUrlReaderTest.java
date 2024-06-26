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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Constants;
import com.braintribe.testing.category.Online;
import com.braintribe.testing.tools.TestTools;
import com.braintribe.utils.CommonTools;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.IOTools;

/**
 * Provides tests for {@link SimpleUrlReader}.
 *
 * @author michael.lafite
 */
public class SimpleUrlReaderTest {

	@Category(Online.class)
	@Test
	public void test() {
		assertThat(CommonTools.provide(new SimpleUrlReader(IOTools.newUrl("http://www.google.com")))).startsWith("<!doctype html><html");

		final File file = TestTools.newTempFile(true);
		final String fileContentWritten = "abc���߀";
		final String encoding = Constants.encodingUTF8();
		final String wrongEncoding = "ISO8859_1";
		FileTools.writeStringToFile(file, fileContentWritten, encoding);

		final String fileContentRead = FileTools.readStringFromFile(file, encoding);
		assertThat(fileContentRead).isEqualTo(fileContentWritten);
		final String fileContentReadWithWrongEncoding = FileTools.readStringFromFile(file, wrongEncoding);
		assertThat(fileContentReadWithWrongEncoding).isNotEqualTo(fileContentWritten);

		final URL fileURL = FileTools.toURL(file);
		assertThat(CommonTools.provide(new SimpleUrlReader(fileURL))).isEqualTo(fileContentWritten);
		assertThat(CommonTools.provide(new SimpleUrlReader(fileURL, encoding))).isEqualTo(fileContentWritten);
		assertThat(CommonTools.provide(new SimpleUrlReader(fileURL, wrongEncoding))).isNotEqualTo(fileContentWritten);
	}

}
