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
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.junit.Test;

import com.braintribe.provider.Holder;
import com.braintribe.utils.lcd.IOTools;

public class AutoCloseInputStreamTest {

	@Test
	public void testInputStream() throws Exception {

		Holder<Boolean> closedHolder = new Holder<>();
		closedHolder.accept(Boolean.FALSE);

		byte[] bytes = new byte[1024];
		new Random().nextBytes(bytes);

		InputStream bais = new AutoCloseInputStream(new ByteArrayInputStream(bytes) {
			@Override
			public void close() throws IOException {
				super.close();
				closedHolder.accept(Boolean.TRUE);
			}
		});

		ByteArrayOutputStream devNull = new ByteArrayOutputStream();
		IOTools.pump(bais, devNull);

		assertThat(closedHolder.get().booleanValue()).isTrue();
	}
}
