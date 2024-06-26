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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

import org.junit.Test;

public class CountingPrintWriterTest {

	@Test
	public void testCountingOuputStream() {

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		CountingPrintWriter cpw = new CountingPrintWriter(pw);
		assertThat(cpw.getCount()).isEqualTo(0L);

		cpw.resetCount();
		cpw.print(true);
		assertThat(cpw.getCount()).isEqualTo(4);

		cpw.resetCount();
		cpw.print(false);
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.print('1');
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.print(new char[] { '1', '2' });
		assertThat(cpw.getCount()).isEqualTo(2);

		cpw.resetCount();
		cpw.print(1.0d);
		assertThat(cpw.getCount()).isEqualTo(3);

		cpw.resetCount();
		cpw.print(1.0f);
		assertThat(cpw.getCount()).isEqualTo(3);

		cpw.resetCount();
		cpw.print(1);
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.print(1l);
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.print((Object) "hello");
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.print((Object) "hello\n");
		assertThat(cpw.getCount()).isEqualTo(6);

		cpw.resetCount();
		cpw.print("hello");
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.printf("%s", "hello");
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.printf(Locale.US, "%s", "hello");
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.println();
		assertThat(cpw.getCount()).isEqualTo(1);

		cpw.resetCount();
		cpw.println(true);
		assertThat(cpw.getCount()).isEqualTo(5);

		cpw.resetCount();
		cpw.println(false);
		assertThat(cpw.getCount()).isEqualTo(6);

		cpw.resetCount();
		cpw.println('1');
		assertThat(cpw.getCount()).isEqualTo(2);

		cpw.resetCount();
		cpw.println(new char[] { '1', '2' });
		assertThat(cpw.getCount()).isEqualTo(3);

		cpw.resetCount();
		cpw.println(1.0d);
		assertThat(cpw.getCount()).isEqualTo(4);

		cpw.resetCount();
		cpw.println(1.0f);
		assertThat(cpw.getCount()).isEqualTo(4);

		cpw.resetCount();
		cpw.println(1);
		assertThat(cpw.getCount()).isEqualTo(2);

		cpw.resetCount();
		cpw.println(1l);
		assertThat(cpw.getCount()).isEqualTo(2);

		cpw.resetCount();
		cpw.println((Object) "hello");
		assertThat(cpw.getCount()).isEqualTo(6);

		cpw.resetCount();
		cpw.println("hello");
		assertThat(cpw.getCount()).isEqualTo(6);

	}
}
