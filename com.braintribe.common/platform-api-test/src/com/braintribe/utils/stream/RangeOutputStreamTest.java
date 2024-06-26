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

import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.junit.Test;

public class RangeOutputStreamTest {

	@Test
	public void testPlain() throws Exception {

		Random rnd = new Random();

		for (int i = 0; i < 10; ++i) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RangeOutputStream ros = new RangeOutputStream(baos, 0, null);

			int size = rnd.nextInt(1000) + 100;
			byte[] input = new byte[size];
			rnd.nextBytes(input);

			ros.write(input);
			ros.close();

			assertThat(baos.toByteArray()).isEqualTo(input);
		}
	}

	@Test
	public void testStartToLimit() throws Exception {

		Random rnd = new Random();

		for (int i = 0; i < 10; ++i) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RangeOutputStream ros = new RangeOutputStream(baos, 0, 49L);

			int size = rnd.nextInt(1000) + 100;
			byte[] input = new byte[size];
			rnd.nextBytes(input);

			ros.write(input);
			ros.close();

			byte[] actual = baos.toByteArray();
			byte[] expected = new byte[50];
			System.arraycopy(input, 0, expected, 0, 50);

			assertThat(actual).hasSize(50);
			assertThat(actual).isEqualTo(expected);
		}
	}

	@Test
	public void testLimitToLimit() throws Exception {

		Random rnd = new Random();

		for (int i = 0; i < 10; ++i) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RangeOutputStream ros = new RangeOutputStream(baos, 10, 49L);

			int size = rnd.nextInt(1000) + 100;
			byte[] input = new byte[size];
			rnd.nextBytes(input);

			ros.write(input);
			ros.close();

			byte[] actual = baos.toByteArray();
			byte[] expected = new byte[40];
			System.arraycopy(input, 10, expected, 0, 40);

			assertThat(actual).hasSize(40);
			assertThat(actual).isEqualTo(expected);
		}
	}

	@Test
	public void testLimitToEnd() throws Exception {

		Random rnd = new Random();

		for (int i = 0; i < 10; ++i) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RangeOutputStream ros = new RangeOutputStream(baos, 10, null);

			int size = rnd.nextInt(1000) + 100;
			byte[] input = new byte[size];
			rnd.nextBytes(input);

			ros.write(input);
			ros.close();

			byte[] actual = baos.toByteArray();
			byte[] expected = new byte[size - 10];
			System.arraycopy(input, 10, expected, 0, expected.length);

			assertThat(actual).hasSize(expected.length);
			assertThat(actual).isEqualTo(expected);
		}
	}

	@Test
	public void testLimitToMaxValue() throws Exception {

		Random rnd = new Random();

		for (int i = 0; i < 10; ++i) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RangeOutputStream ros = new RangeOutputStream(baos, 10, Long.MAX_VALUE);

			int size = rnd.nextInt(1000) + 100;
			byte[] input = new byte[size];
			rnd.nextBytes(input);

			ros.write(input);
			ros.close();

			byte[] actual = baos.toByteArray();
			byte[] expected = new byte[size - 10];
			System.arraycopy(input, 10, expected, 0, expected.length);

			assertThat(actual).hasSize(expected.length);
			assertThat(actual).isEqualTo(expected);
		}
	}
}
