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
package com.braintribe.utils.lcd;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.Test;

/**
 * Tests for {@link AutoResettingStopWatch}
 *
 *
 */
public class AutoResettingStopWatchTest {

	@Test
	public void doesResetAutomatically() throws Exception {
		long start = System.currentTimeMillis();
		final AutoResettingStopWatch stopWatch = new AutoResettingStopWatch();

		long elapsedTime = stopWatch.getElapsedTime();
		start = System.currentTimeMillis();

		long myCalc = System.currentTimeMillis() - start;

		assertThat(elapsedTime).isCloseTo(myCalc, Offset.offset((long) StopWatchTest.ALLOWED_MEASUREMENT_DELTA_IN_MS));

		Thread.sleep(10);

		elapsedTime = stopWatch.getElapsedTime();
		myCalc = System.currentTimeMillis() - start;

		assertThat(elapsedTime).isCloseTo(myCalc, Offset.offset((long) StopWatchTest.ALLOWED_MEASUREMENT_DELTA_IN_MS));
	}

}
