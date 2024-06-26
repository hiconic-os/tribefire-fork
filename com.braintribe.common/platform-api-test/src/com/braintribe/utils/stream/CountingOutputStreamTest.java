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

import org.junit.Test;

import com.braintribe.utils.IOTools;

public class CountingOutputStreamTest {

	@Test
	public void testCountingOuputStream() {

		CountingOutputStream cos = new CountingOutputStream(new ByteArrayOutputStream());
		assertThat(cos.getCount()).isEqualTo(0L);
	}

	@Test
	public void testCountingOuputStreamMultipleRandom() throws Exception {

		int runs = 100;
		Random rnd = new Random();

		for (int i = 0; i < runs; ++i) {

			int size = rnd.nextInt(100000);

			byte[] data = new byte[size];
			rnd.nextBytes(data);

			ByteArrayInputStream bis = null;
			ByteArrayOutputStream baos = null;

			CountingOutputStream cos = null;
			try {
				bis = new ByteArrayInputStream(data);
				baos = new ByteArrayOutputStream();
				cos = new CountingOutputStream(baos);

				IOTools.pump(bis, cos);

			} finally {
				IOTools.closeCloseable(bis, null);
				IOTools.closeCloseable(cos, null);
			}

			assertThat(cos.getCount()).isEqualTo(size);
			assertThat(baos.toByteArray()).isEqualTo(data);

		}

	}
}
