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

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Offset;
import org.junit.Ignore;
import org.junit.Test;

import com.braintribe.common.lcd.Numbers;

/**
 * Tests for {@link StopWatch}
 *
 *
 */
public class StopWatchTest {

	public static final int ALLOWED_MEASUREMENT_DELTA_IN_MS = 30;

	@Test
	public void basicTest() {
		final long start = System.currentTimeMillis();
		final StopWatch stopWatch = new StopWatch();

		final long elapsedTime = stopWatch.getElapsedTime();

		final long myCalc = System.currentTimeMillis() - start;

		assertThat(elapsedTime).isCloseTo(myCalc, Offset.offset((long) ALLOWED_MEASUREMENT_DELTA_IN_MS));
	}

	@Test
	public void keepsRunning() throws Exception {
		final long start = System.currentTimeMillis();
		final StopWatch stopWatch = new StopWatch();

		long elapsedTime = stopWatch.getElapsedTime();

		long myCalc = System.currentTimeMillis() - start;

		assertThat(elapsedTime).isCloseTo(myCalc, Offset.offset((long) ALLOWED_MEASUREMENT_DELTA_IN_MS));

		Thread.sleep(10);

		elapsedTime = stopWatch.getElapsedTime();
		myCalc = System.currentTimeMillis() - start;

		assertThat(elapsedTime).isCloseTo(myCalc, Offset.offset((long) ALLOWED_MEASUREMENT_DELTA_IN_MS));
	}

	@Test
	public void testIntermediates() throws Exception {
		StopWatch stopWatch = new StopWatch();

		Thread.sleep(10L);

		stopWatch.intermediate("X");

		Thread.sleep(10L);
		String toString = stopWatch.toString();

		assertThat(toString).contains("X:");
		assertThat(stopWatch.getElapsedTimesReport()).contains("X:");
	}

	@Test
	@Ignore("Runs too long")
	// TODO: long running tests
	public void testSeconds() throws Exception {
		final StopWatch stopWatch = new StopWatch();
		Thread.sleep(Numbers.MILLISECONDS_PER_SECOND);
		final long actualSeconds = stopWatch.getElapsedTimeInSeconds();
		assertThat(actualSeconds).isEqualTo(1);
	}

	@Test
	@Ignore("Runs too long")
	// TODO: long running tests
	public void testMinutes() throws Exception {
		final StopWatch stopWatch = new StopWatch();
		Thread.sleep(Numbers.MILLISECONDS_PER_MINUTE);
		final long actualMinutes = stopWatch.getElapsedTimeInMinutes();
		assertThat(actualMinutes).isEqualTo(1);
	}
}
