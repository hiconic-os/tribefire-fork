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

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.braintribe.cfg.Required;
import com.braintribe.utils.junit.assertions.BtAssertions;

/**
 * Simple test that demonstrates how a {@link org.springframework.aop.framework.ProxyFactoryBean ProxyFactoryBean} in
 * combination with an {@link MethodInterceptor} can be used to easily extend the functionality of a service.
 * 
 * @author michael.lafite
 */
public class ProxyFactoryTest {

	@Test
	public void test() {
		final ApplicationContext context = new FileSystemXmlApplicationContext("res/"
				+ ProxyFactoryTest.class.getSimpleName() + ".xml");

		final TestService testService = (TestService) context.getBean("proxyFactoryTest.testService");

		final String message = "test message";
		BtAssertions.assertThat(testService.echo(message)).endsWith(message);
	}

	public static class LoggingInterceptor implements org.aopalliance.intercept.MethodInterceptor {

		public Object invoke(final MethodInvocation invocation) throws Throwable {
			System.out.println("Invoking method " + invocation.getMethod().getName() + " with arguments "
					+ Arrays.asList(invocation.getArguments()) + " ...");
			final Object result = invocation.proceed();
			System.out.println("Invoked method " + invocation.getMethod().getName() + ". Result is '" + result + "'.");
			return result;
		}
	}

	/**
	 * A simple test service.
	 * 
	 * @author michael.lafite
	 */
	public static interface TestService {

		/**
		 * Returns the passed <code>message</code>.
		 */
		public String echo(String message);
	}

	/**
	 * {@link TestService} implementation.
	 * 
	 * @author michael.lafite
	 */
	public static class TestServiceImpl implements TestService {

		private String prefix;

		/**
		 * Returns the passed <code>message</code>, prepended by the configured {@link #setPrefix(String) prefix}.
		 */
		public String echo(final String message) {
			return getPrefix() + message;
		}

		public String getPrefix() {
			return this.prefix;
		}

		@Required
		public void setPrefix(final String prefix) {
			this.prefix = prefix;
		}

	}

}
