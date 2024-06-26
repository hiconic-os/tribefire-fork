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
package com.braintribe.spring.support;

import java.util.function.Supplier;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


import com.braintribe.utils.junit.assertions.BtAssertions;

public class StringToProviderConverterTest {

	@Test
	public void test() throws RuntimeException {
		final ApplicationContext context = new FileSystemXmlApplicationContext("res/"
				+ StringToProviderConverterTest.class.getSimpleName() + ".xml");

		final TestBean testBean = (TestBean) context.getBean("stringToProviderConverterTest.testBean");
		BtAssertions.assertThat(testBean.getStringProvider().get()).isEqualTo("provided string value");
	}

	public static class TestBean {

		private Supplier<String> stringProvider;

		public Supplier<String> getStringProvider() {
			return this.stringProvider;
		}

		public void setStringProvider(final Supplier<String> stringProvider) {
			this.stringProvider = stringProvider;
		}
	}
}
