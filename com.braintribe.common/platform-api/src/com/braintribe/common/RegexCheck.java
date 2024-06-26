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
package com.braintribe.common;

import com.braintribe.common.lcd.GenericCheck;
import com.braintribe.utils.lcd.Arguments;

/**
 * A regular expression {@link GenericCheck check}.
 *
 * @author michael.lafite
 */
public class RegexCheck extends AbstractRegexCheck<String> {

	public RegexCheck() {
		// nothing to do
	}

	public RegexCheck(final String includeRegex) {
		super(includeRegex);
	}

	public RegexCheck(final String includeRegex, final String excludeRegex) {
		super(includeRegex, excludeRegex);
	}

	@Override
	protected String getString(final String string) {
		Arguments.notNull(string, "Cannot check null string!");
		return string;
	}

}
