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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class ThrowableToolsTest {

	@Test
	public void testGetLastMessage() {

		Throwable t = new Throwable("Hello, world");
		assertThat(ThrowableTools.getLastMessage(t)).isEqualTo("Hello, world");

		Throwable t1 = new Throwable("Hello, world");
		Throwable t2 = new Throwable("Some Test", t1);
		assertThat(ThrowableTools.getLastMessage(t2)).isEqualTo("Hello, world");

		Throwable t3 = new Throwable("Layer 3", t2);
		Throwable t4 = new Throwable("Layer 4", t3);
		Throwable t5 = new Throwable("Layer 5", t4);
		Throwable t6 = new Throwable("Layer 6", t5);
		Throwable t7 = new Throwable("Layer 7", t6);

		assertThat(ThrowableTools.getLastMessage(t7)).isEqualTo("Hello, world");

		Throwable t10 = new Throwable("Hello, world");
		t10.addSuppressed(new Throwable("Suppressed 1"));
		Throwable t11 = new Throwable("Some Test", t10);
		t11.addSuppressed(new Throwable("Suppressed 2"));
		assertThat(ThrowableTools.getLastMessage(t11)).isEqualTo("Hello, world");

		Throwable t20 = new Throwable("Hello, world");
		Throwable t21 = new Throwable(t20);
		Throwable t22 = new Throwable("Some Test", t21);
		assertThat(ThrowableTools.getLastMessage(t22)).isEqualTo("Hello, world");

		Throwable t30 = new Throwable();
		Throwable t31 = new Throwable("Hello, world", t30);
		Throwable t32 = new Throwable("Some Test", t31);
		assertThat(ThrowableTools.getLastMessage(t32)).isEqualTo("Hello, world");

		Throwable t40 = new Throwable();
		Throwable t41 = new Throwable("Hello, world", t40);
		Throwable t42 = new Throwable("Some Test", t41);
		// Create cause-loop
		t40.initCause(t42);
		assertThat(ThrowableTools.getLastMessage(t42)).isEqualTo("Hello, world");

	}
}
