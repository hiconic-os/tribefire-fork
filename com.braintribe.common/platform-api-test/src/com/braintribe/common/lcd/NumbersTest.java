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
package com.braintribe.common.lcd;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.utils.MathTools;

public class NumbersTest {

	public final static long NANOS_PER_MILLISECOND = 1_000_000L;
	public final static long NANOS_PER_SECOND = NANOS_PER_MILLISECOND * 1_000L;
	public final static long NANOS_PER_MINUTE = NANOS_PER_SECOND * 60;
	public final static long NANOS_PER_HOUR = NANOS_PER_MINUTE * 60;
	public final static long NANOS_PER_DAY = NANOS_PER_HOUR * 24;

	@Test
	public void testNumbersValidity() {

		// This is necessary as nobody realized that these values were defined as integer and therefore became negative

		assertThat(Numbers.NANOSECONDS_PER_MILLISECOND).isEqualTo(NANOS_PER_MILLISECOND);
		assertThat(Numbers.NANOSECONDS_PER_SECOND).isEqualTo(NANOS_PER_SECOND);
		assertThat(Numbers.NANOSECONDS_PER_MINUTE).isEqualTo(NANOS_PER_MINUTE);
		assertThat(Numbers.NANOSECONDS_PER_HOUR).isEqualTo(NANOS_PER_HOUR);
		assertThat(Numbers.NANOSECONDS_PER_DAY).isEqualTo(NANOS_PER_DAY);

		assertThat(Numbers.NANOSECONDS_PER_DAY).isGreaterThan(Numbers.NANOSECONDS_PER_HOUR);
		assertThat(Numbers.NANOSECONDS_PER_HOUR).isGreaterThan(Numbers.NANOSECONDS_PER_MINUTE);
		assertThat(Numbers.NANOSECONDS_PER_MINUTE).isGreaterThan(Numbers.NANOSECONDS_PER_SECOND);
		assertThat(Numbers.NANOSECONDS_PER_SECOND).isGreaterThan(Numbers.NANOSECONDS_PER_MILLISECOND);

		assertThat(Numbers.NANOSECONDS_PER_DAY).isGreaterThan(0L);
		assertThat(Numbers.NANOSECONDS_PER_DAY).isLessThan(Long.MAX_VALUE); // Dumb test? Yes, it is but what the heck

		assertThat(Numbers.BYTE).isEqualTo(1);
		assertThat(Numbers.KIBIBYTE).isEqualTo(1024L);
		assertThat(Numbers.MEBIBYTE).isEqualTo(MathTools.power(Numbers.KIBIBYTE, 2));
		assertThat(Numbers.GIBIBYTE).isEqualTo(MathTools.power(Numbers.KIBIBYTE, 3));
		assertThat(Numbers.TEBIBYTE).isEqualTo(MathTools.power(Numbers.KIBIBYTE, 4));
		assertThat(Numbers.PEBIBYTE).isEqualTo(MathTools.power(Numbers.KIBIBYTE, 5));
		assertThat(Numbers.EXBIBYTE).isEqualTo(MathTools.power(Numbers.KIBIBYTE, 6));

		assertThat(Numbers.KILOBYTE).isEqualTo(1000L);
		assertThat(Numbers.MEGABYTE).isEqualTo(MathTools.power(Numbers.KILOBYTE, 2));
		assertThat(Numbers.GIGABYTE).isEqualTo(MathTools.power(Numbers.KILOBYTE, 3));
		assertThat(Numbers.TERABYTE).isEqualTo(MathTools.power(Numbers.KILOBYTE, 4));
		assertThat(Numbers.PETABYTE).isEqualTo(MathTools.power(Numbers.KILOBYTE, 5));
		assertThat(Numbers.EXABYTE).isEqualTo(MathTools.power(Numbers.KILOBYTE, 6));
	}

}
