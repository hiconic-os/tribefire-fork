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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LimitedStringBuilderTest {

	@Test
	public void testLimitedStringBuilder() throws Exception {

		LimitedStringBuilder b = new LimitedStringBuilder(10);
		b.append("Hello, world");
		assertThat(b.toString()).isEqualTo("llo, world");

		b = new LimitedStringBuilder(12);
		b.append("Hello, world");
		assertThat(b.toString()).isEqualTo("Hello, world");

		b = new LimitedStringBuilder(15);
		b.append("Hello, world");
		assertThat(b.toString()).isEqualTo("Hello, world");

		b = new LimitedStringBuilder(1);
		b.append("Hello, world");
		assertThat(b.toString()).isEqualTo("d");

		b = new LimitedStringBuilder(10);
		b.append("Hello, ");
		b.append("world");
		assertThat(b.toString()).isEqualTo("llo, world");

		b = new LimitedStringBuilder(10);
		b.append("Hello, ");
		b.append("world 1234567890");
		assertThat(b.toString()).isEqualTo("1234567890");

		b = new LimitedStringBuilder(10);
		b.append("Hello, ");
		b.append("world 1234567890");
		b.append('a');
		assertThat(b.toString()).isEqualTo("234567890a");

		b = new LimitedStringBuilder(Integer.MAX_VALUE);
		b.append("Hello, world");
		assertThat(b.toString()).isEqualTo("Hello, world");

	}
}
