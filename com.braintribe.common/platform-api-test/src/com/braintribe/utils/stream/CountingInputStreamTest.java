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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.utils.IOTools;

public class CountingInputStreamTest {

	@Test
	public void testInputStreamUnbuffered() throws Exception {
		this.testInputStream(false);
	}

	@Test
	public void testInputStreamBuffered() throws Exception {
		this.testInputStream(true);
	}

	@Ignore
	public void testInputStream(boolean buffered) throws Exception {

		int runs = 20;
		Random rnd = new Random();

		for (int i = 0; i < runs; ++i) {

			int size = rnd.nextInt(50000);

			byte[] input = new byte[size];
			rnd.nextBytes(input);

			ByteArrayInputStream bis = null;
			ByteArrayOutputStream baos = null;
			CountingInputStream cis = null;
			try {
				bis = new ByteArrayInputStream(input);
				cis = new CountingInputStream(bis, buffered);

				baos = new ByteArrayOutputStream();
				IOTools.pump(cis, baos);

			} finally {
				IOTools.closeCloseable(bis, null);
				IOTools.closeCloseable(cis, null);
			}

			assertThat(cis.getCount()).isEqualTo(size);
			assertThat(baos.toByteArray()).isEqualTo(input);

		}

	}
}
