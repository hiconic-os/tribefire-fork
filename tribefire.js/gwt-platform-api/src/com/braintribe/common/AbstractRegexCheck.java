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

import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.braintribe.common.lcd.GenericCheck;
import com.braintribe.utils.lcd.Arguments;
import com.braintribe.utils.lcd.CommonTools;

/**
 * Abstract class that can be used to quickly implement a regular expression based {@link GenericCheck check}.
 *
 * @see #check(Object)
 * @see RegexCheck
 *
 * @author michael.lafite
 */
public abstract class AbstractRegexCheck<T> implements GenericCheck<T>, Predicate<T> {

	private Pattern includePattern;
	private Pattern excludePattern;
	private String includeRegex;
	private String excludeRegex;

	public AbstractRegexCheck() {
		// nothing to do
	}

	public AbstractRegexCheck(final String includeRegex) {
		setIncludeRegex(includeRegex);
	}

	public AbstractRegexCheck(final String includeRegex, final String excludeRegex) {
		setIncludeRegex(includeRegex);
		setExcludeRegex(excludeRegex);
	}

	public String getIncludeRegex() {
		return this.includeRegex;
	}

	public String getExcludeRegex() {
		return this.excludeRegex;
	}

	public void setExcludeRegex(final String regex) {
		if (regex != null) {
			this.excludeRegex = regex;
			this.excludePattern = Pattern.compile(regex);
		} else {
			this.excludeRegex = null;
			this.excludePattern = null;
		}
	}

	public void setIncludeRegex(final String regex) {
		if (regex != null) {
			this.includeRegex = regex;
			this.includePattern = Pattern.compile(regex);
		} else {
			this.includeRegex = null;
			this.includePattern = null;
		}
	}

	private Pattern getIncludePattern() {
		return this.includePattern;
	}

	private Pattern getExcludePattern() {
		return this.excludePattern;
	}

	/**
	 * Gets the string for the passed <code>object</code>. This can eitehr be a string representation (i.e. converting
	 * the object to string) or it could be some property (e.g. id/name), etc. Must not return <code>null</code>!
	 */
	protected abstract String getString(T object);

	/**
	 * {@link #getString(Object) Gets the string} for the passed <code>object</code> and checks, if it is valid
	 * according to the configured include/exclude pattern.
	 */
	@Override
	public boolean check(final T object) {
		final String string = getString(object);
		Arguments.notNull(string, "Cannot check object because the provided string is null! " + CommonTools.getParametersString("object", object));

		if (getExcludePattern() != null) {
			final boolean excludePatternMatched = getExcludePattern().matcher(string).matches();
			if (excludePatternMatched) {
				return false;
			}
		}

		if (getIncludePattern() == null) {
			return true;
		}

		final boolean includePatternMatched = getIncludePattern().matcher(string).matches();
		return includePatternMatched;
	}

	/**
	 * Just delegates to {@link #check(Object)}.
	 */
	@Override
	public boolean test(T object) {
		return check(object);
	}
		

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[includeRegex=" + getIncludeRegex() + ",excludeRegex=" + getExcludeRegex() + "]";
	}

}
