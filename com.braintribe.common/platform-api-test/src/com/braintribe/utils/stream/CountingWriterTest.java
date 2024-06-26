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

import java.io.StringWriter;

import org.junit.Test;

public class CountingWriterTest {

	@SuppressWarnings("cast")
	@Test
	public void testCountingOuputStream() throws Exception {

		StringWriter sw = new StringWriter();

		CountingWriter cpw = new CountingWriter(sw);
		assertThat(cpw.getCount()).isEqualTo(0L);

		cpw.resetCount();
		cpw.append('1');
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.append("hello");
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.append("hello", 0, 1);
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.write(new char[] { '1', '2' });
		assertThat(cpw.getCount()).isEqualTo(2);

		cpw.resetCount();
		cpw.write((int) '1');
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.write("hello");
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.write(new char[] { '1', '2' }, 0, 1);
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.write("hello2", 0, 1);
		assertThat(cpw.getCount()).isEqualTo(1);
	}
}
