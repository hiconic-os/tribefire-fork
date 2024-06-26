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

import com.braintribe.common.lcd.Condition;

/**
 * Provides tests for {@link CommonTools}.
 *
 * @author michael.lafite
 */

public class CommonToolsTest {

	@Test
	public void testWait() {
		final long timeToWait = 500;
		long time = System.currentTimeMillis();
		CommonTools.wait(timeToWait, null, null);
		assertThat(System.currentTimeMillis()).isGreaterThanOrEqualTo(time + timeToWait);

		final Condition breakCondition = new Condition() {
			@Override
			public boolean evaluate() {
				return true;
			}
		};
		time = System.currentTimeMillis();
		CommonTools.wait(timeToWait, breakCondition, 10);
		assertThat(System.currentTimeMillis()).isLessThan(time + timeToWait);
	}

}
