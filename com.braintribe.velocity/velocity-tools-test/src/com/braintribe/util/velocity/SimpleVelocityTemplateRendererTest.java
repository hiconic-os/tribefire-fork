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
package com.braintribe.util.velocity;


import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;

import com.braintribe.testing.junit.rules.ThrowableChain;
import com.braintribe.testing.junit.rules.ThrowableChainRule;
import com.braintribe.utils.CommonTools;
import com.braintribe.utils.velocity.SimpleVelocityTemplateRenderer;

/**
 * Provides tests for {@link SimpleVelocityTemplateRenderer}.
 * 
 * @author michael.lafite
 */

public class SimpleVelocityTemplateRendererTest {

	@Rule
	public ThrowableChainRule exceptionChainRule = new ThrowableChainRule();

	@Test
	public void testSimpleTemplates() {

		assertThat(
				SimpleVelocityTemplateRenderer.quickEvaluate("--- $sampleString ---", "sampleString", "SUCCESS"))
				.isEqualTo("--- SUCCESS ---");

		final String loopTestTemplate = "#foreach( $person in $personList )\r\n"
				+ "   Person $person.name is $person.age years old.\r\n" + "#end";
		final String result = SimpleVelocityTemplateRenderer.quickEvaluate(loopTestTemplate, "personList",
				CommonTools.getList(new Person("john", 32), new Person("jane", 29)));
		assertThat(result).matches("(?s).*john.*32.*jane.*29.*");
	}

	@ThrowableChain(org.apache.velocity.exception.MathException.class)
	@Test
	public void testDivisionByZeroCausesMathException() {
		SimpleVelocityTemplateRenderer.quickEvaluate("#set($result = 1/0)");
	}

	/**
	 * A simple test class.
	 * 
	 * @author michael.lafite
	 */
	public static class Person {
		private String name;
		private int age;

		public Person(final String name, final int age) {
			super();
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return this.name;
		}

		public void setName(final String name) {
			this.name = name;
		}

		public int getAge() {
			return this.age;
		}

		public void setAge(final int age) {
			this.age = age;
		}
	}

}
