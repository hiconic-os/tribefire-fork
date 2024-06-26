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
package com.braintribe.filter.lcd.pattern;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.braintribe.filter.pattern.Range;

/**
 *
 */
public class CamelCasePatternMatcherTests {

	CamelCasePatternMatcher m = new CamelCasePatternMatcher();

	@Test
	public void matches() {
		final String text = "AaaaBbbbCcccDddd";

		final List<Range> ranges = this.m.matches("AaBCccD", text);
		final List<Range> expectedRanges = toRanges(0, 2, 4, 1, 8, 3, 12, 1);

		assertThat(ranges).isEqualTo(expectedRanges);
	}

	@Test
	public void matches2() {
		final String text = "AaaaBbbbCcccDddd";

		final List<Range> ranges = this.m.matches("AaBCD", text);
		final List<Range> expectedRanges = toRanges(0, 2, 4, 1, 8, 1, 12, 1);

		assertThat(ranges).isNotNull().isEqualTo(expectedRanges);
	}

	@Test
	public void matchesLowercase() {
		final String text = "aaaaBbbbCcccDddd";

		final List<Range> ranges = this.m.matches("aaBCD", text);
		final List<Range> expectedRanges = toRanges(0, 2, 4, 1, 8, 1, 12, 1);

		assertThat(ranges).isNotNull().isEqualTo(expectedRanges);
	}

	@Test
	public void matchesWithOtherCharacters() {
		final String text = "Aaaa_0$-BbbbCcccDddd";

		final List<Range> ranges = this.m.matches("AaBCcc", text);
		final List<Range> expectedRanges = toRanges(0, 2, 8, 1, 12, 3);

		assertThat(ranges).isEqualTo(expectedRanges);
	}

	/**
	 * If the pattern is AaaBCcc, then there can be no other capital letter between "A" and "B", and "B" and "C".
	 */
	@Test
	public void notMatchesIfOtherCapitalBetween() {
		final String text = "AaaaXBbbbCcccDddd";

		final List<Range> ranges = this.m.matches("AaaBCcc", text);

		assertThat(ranges).isNullOrEmpty();
	}

	@Test
	public void notMatchesIfStartsWithLowercase() {
		final String text = "aaaaBbbbCcccDddd";

		final List<Range> ranges = this.m.matches("AaB", text);

		assertThat(ranges).isNullOrEmpty();
	}

	private static List<Range> toRanges(final Integer... r) {
		final List<Range> result = new ArrayList<>();

		for (int i = 0; i < r.length;) {
			result.add(new Range(r[i++], r[i++]));
		}

		return result;
	}
}
