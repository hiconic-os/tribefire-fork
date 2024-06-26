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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.braintribe.filter.pattern.PatternMatcher;
import com.braintribe.filter.pattern.Range;
import com.braintribe.utils.lcd.StringTools;

/**
 *
 */
public class CamelCasePatternMatcher implements PatternMatcher {

	/**
	 * Matches texts supporting camel-case like search/code completion in eclipse does.
	 */
	@Override
	public List<Range> matches(final String pattern, final String text) {
		if (pattern == null) {
			final Range range = new Range(0, 0);
			return Collections.singletonList(range);
		}

		final List<String> partialPatterns = StringTools.splitCamelCase(pattern);

		String remainingText = text;
		int offset = 0;
		final List<Range> result = new ArrayList<>();

		for (final String partialPattern : partialPatterns) {
			if (!remainingText.startsWith(partialPattern)) {
				return null;
			}

			result.add(new Range(offset, partialPattern.length()));

			offset += partialPattern.length();
			remainingText = remainingText.substring(partialPattern.length());

			final int firstCapitalPosition = StringTools.findFirstCapitalPosition(remainingText);

			if (firstCapitalPosition < 0) {
				remainingText = "";
			} else {
				offset += firstCapitalPosition;
				remainingText = remainingText.substring(firstCapitalPosition);
			}
		}

		return result;
	}

}
