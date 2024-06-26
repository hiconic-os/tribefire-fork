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

import java.util.Collections;
import java.util.List;

import com.braintribe.filter.pattern.PatternMatcher;
import com.braintribe.filter.pattern.Range;

/**
 *
 */
public class SubstringCheckingPatternMatcher implements PatternMatcher {

	@Override
	public List<Range> matches(final String pattern, final String text) {
		if (pattern == null) {
			final Range range = new Range(0, 0);
			return Collections.singletonList(range);
		}

		final int index = text.toLowerCase().indexOf(pattern.toLowerCase());

		if (index < 0) {
			return null;
		}

		final Range range = new Range(index, pattern.length());

		return Collections.singletonList(range);
	}

}
