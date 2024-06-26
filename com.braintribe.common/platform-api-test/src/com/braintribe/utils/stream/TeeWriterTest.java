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

public class TeeWriterTest {

	@Test
	public void testTeeWriter() throws Exception {

		StringWriter mainRecipient = new StringWriter();
		StringWriter backupRecipient = new StringWriter();

		try (TeeWriter tw = new TeeWriter(mainRecipient, backupRecipient)) {
			tw.write("hello, world");
			tw.flush();
		}

		assertThat(mainRecipient.toString()).isEqualTo("hello, world");
		assertThat(backupRecipient.toString()).isEqualTo("hello, world");

	}

	@Test
	public void testTeeWriterWithStopTee() throws Exception {

		StringWriter mainRecipient = new StringWriter();
		StringWriter backupRecipient = new StringWriter();

		try (TeeWriter tw = new TeeWriter(mainRecipient, backupRecipient)) {
			tw.write("hello");
			tw.stopTee();
			tw.write(", world");
			tw.flush();
		}

		assertThat(mainRecipient.toString()).isEqualTo("hello, world");
		assertThat(backupRecipient.toString()).isEqualTo("hello");

	}
}
