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
package com.braintribe.utils.date;

import java.util.Date;
import java.util.function.Supplier;

import com.braintribe.utils.DateTools;

/**
 * Provides the current date in the specified {@link #setDateFormatPattern(String) pattern} plus optional
 * {@link #setPrefix(String) prefix} and {@link #setSuffix(String) suffix}.
 *
 * @author michael.lafite
 */
public class CurrentDateStringProvider implements Supplier<String> {

	private String dateFormatPattern = DateTools.DEFAULT_DATEFORMAT_PATTERN;
	private String prefix = "";
	private String suffix = "";

	public CurrentDateStringProvider() {
		// nothing to do
	}

	public String getDateFormatPattern() {
		return this.dateFormatPattern;
	}

	public void setDateFormatPattern(final String dateFormatPattern) {
		this.dateFormatPattern = dateFormatPattern;
		// make sure pattern is valid
		get();
	}

	public String getPrefix() {
		return this.prefix;
	}

	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(final String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String get() {
		final String datePart = new ExtSimpleDateFormat(getDateFormatPattern()).format(new Date());
		final String result = getPrefix() + datePart + getSuffix();
		return result;
	}

}
