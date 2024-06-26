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
package com.braintribe.utils.string.caseconvert;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.braintribe.provider.Box;
import com.braintribe.utils.lcd.StringTools;

/**
 * @author peter.gazdik
 */
public class BasicCaseConverter implements CaseConversionSplitter, CaseConversionMapper {

	private final String s;
	private Stream<String> splitsStream;

	public BasicCaseConverter(String s) {
		this.s = s == null ? "" : s;
	}

	// ##########################################################
	// ## . . . . . . . CaseConversionSplitter . . . . . . . . ##
	// ##########################################################

	@Override
	public CaseConversionMapper splitCamelCase() {
		splitsStream = StringTools.splitCamelCase(s).stream();
		return this;
	}

	@Override
	public CaseConversionMapper splitCamelCaseSmart() {
		splitsStream = StringTools.splitCamelCaseSmart(s).stream();
		return this;
	}

	@Override
	public CaseConversionMapper splitOnDelimiter(String delimiter) {
		return splitOnRegex(Pattern.quote(delimiter));
	}

	@Override
	public CaseConversionMapper splitOnRegex(String regex) {
		splitsStream = Stream.of(s.split(regex));
		return this;
	}

	// ##########################################################
	// ## . . . . . . . . CaseConversionMapper . . . . . . . . ##
	// ##########################################################

	@Override
	public CaseConversionMapper when(boolean condition) {
		return condition ? this : new IgnoringNextMappingMapper();
	}

	private class IgnoringNextMappingMapper implements CaseConversionMapper {
		// @formatter:off
		@Override public CaseConversionJoiner uncapitalizeAll() { return BasicCaseConverter.this; }
		@Override public CaseConversionJoiner capitalizeAll() { return BasicCaseConverter.this; }
		@Override public CaseConversionJoiner uncapitalizeFirst() { return BasicCaseConverter.this; }
		@Override public CaseConversionJoiner capitalizeAllButFirst() { return BasicCaseConverter.this; }
		@Override public CaseConversionJoiner toLowerCase() { return BasicCaseConverter.this; }
		@Override public CaseConversionJoiner toUpperCase() { return BasicCaseConverter.this; }
		@Override public CaseConversionJoiner map(Function<? super String, String> mapping) { return null; }

		@Override public CaseConversionMapper when(boolean condition) { return throwError("when"); }
		@Override public Stream<String> asStream() { return throwError("asStream"); }
		@Override public String join(String delimiter) { return throwError("join"); }

		private <T> T throwError(String method) { throw new IllegalStateException("Cannot call " + method + " after 'when' method was used."); }
		// @formatter:on
	}

	@Override
	public CaseConversionJoiner uncapitalizeAll() {
		return map(StringTools::uncapitalize);
	}

	@Override
	public CaseConversionJoiner capitalizeAll() {
		return map(StringTools::capitalize);
	}

	@Override
	public CaseConversionJoiner uncapitalizeFirst() {
		Box<Boolean> flag = Box.of(Boolean.TRUE);
		return map(s -> {
			if (flag.value) {
				flag.value = Boolean.FALSE;
				s = StringTools.uncapitalize(s);
			}
			return s;
		});
	}

	@Override
	public CaseConversionJoiner capitalizeAllButFirst() {
		Box<Boolean> flag = Box.of(Boolean.TRUE);
		return map(s -> {
			if (flag.value)
				flag.value = Boolean.FALSE;
			else
				s = StringTools.capitalize(s);
			return s;
		});
	}

	@Override
	public CaseConversionJoiner toLowerCase() {
		return map(String::toLowerCase);
	}

	@Override
	public CaseConversionJoiner toUpperCase() {
		return map(String::toUpperCase);
	}

	@Override
	public CaseConversionJoiner map(Function<? super String, String> mapping) {
		splitsStream = splitsStream.map(mapping);
		return this;
	}

	@Override
	public Stream<String> asStream() {
		return splitsStream;
	}

	// ##########################################################
	// ## . . . . . . . . CaseConversionJoiner . . . . . . . . ##
	// ##########################################################

	@Override
	public String join(String delimiter) {
		return splitsStream.collect(Collectors.joining(delimiter));
	}

}
