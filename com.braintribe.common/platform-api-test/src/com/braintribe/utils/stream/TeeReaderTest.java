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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.Test;

public class TeeReaderTest {

	@SuppressWarnings("resource")
	@Test
	public void testTeeReader() throws Exception {

		String originalString = "Hello, world";

		StringReader sr = new StringReader(originalString);

		TeeReader r = new TeeReader(sr);

		BufferedReader br = new BufferedReader(r);

		String result = br.readLine();

		assertThat(result).isEqualTo(originalString);

		assertThat(r.getBuffer()).isEqualTo(originalString);

	}

	@SuppressWarnings("resource")
	@Test
	public void testTeeReaderLimited() throws Exception {

		String originalString = "Hello, world";

		StringReader sr = new StringReader(originalString);

		TeeReader r = new TeeReader(sr, 10);

		BufferedReader br = new BufferedReader(r);

		String result = br.readLine();

		assertThat(result).isEqualTo(originalString);

		assertThat(r.getBuffer()).isEqualTo("llo, world");

	}
}
