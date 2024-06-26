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
package com.braintribe.testing.test.logging;

import org.junit.Test;

import com.braintribe.logging.Logger;

/**
 * Provides simple test methods which just log a few messages. Purpose of this class in combination with {@link LoggingTest_A} is to verify that
 * logging from multiple test classes/methods doesn't interfere in any way, e.g. that logs end up in the wrong test report or that after logging in
 * one class logging in the other class doesn't work anymore or bugs like that. Note that tests currently don't contain any assertions, i.e. logs have
 * to checked manually.
 *
 * @author michael.lafite
 */
public class LoggingTest_B {

	private static Logger logger = Logger.getLogger(LoggingTest_B.class);

	@Test
	public void test1() {
		logger.info("[LoggingTest_B.test1] test INFO message 1");
		System.out.println("[LoggingTest_B.test1] test System.out message 1");
		System.err.println("[LoggingTest_B.test1] test System.err message 1");
		logger.info("[LoggingTest_B.test1] test INFO message 2");
		System.out.println("[LoggingTest_B.test1] test System.out message 2");
		System.err.println("[LoggingTest_B.test1] test System.err message 2");
	}

	@Test
	public void test2() {
		logger.info("[LoggingTest_B.test2] test INFO message 1");
		System.out.println("[LoggingTest_B.test2] test System.out message 1");
		System.err.println("[LoggingTest_B.test2] test System.err message 1");
		logger.info("[LoggingTest_B.test2] test INFO message 2");
		System.out.println("[LoggingTest_B.test2] test System.out message 2");
		System.err.println("[LoggingTest_B.test2] test System.err message 2");
	}
}
