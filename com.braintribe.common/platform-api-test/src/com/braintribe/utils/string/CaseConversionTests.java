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
package com.braintribe.utils.string;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.utils.StringTools;
import com.braintribe.utils.string.caseconvert.CaseConversionSplitter;
import com.google.common.base.Function;

/**
 * @author peter.gazdik
 */
public class CaseConversionTests {

	@Test
	public void testItWorks() throws Exception {
		assertConversion("HelloWorld", "hello-world", s -> s.splitCamelCase().uncapitalizeAll().join("-"));
		assertConversion("HelloWorld", "hello-World", s -> s.splitCamelCase().uncapitalizeFirst().join("-"));
		assertConversion("hello-World", "helloWorld", s -> s.splitOnDelimiter("-").joinWithoutdelimiter());
		assertConversion("hello-world", "helloWorld", s -> s.splitOnDelimiter("-").capitalizeAllButFirst().joinWithoutdelimiter());

		assertConversion("HelloTHISWorld", "hello-this-world", s -> s.splitCamelCaseSmart().toLowerCase().join("-"));
		assertConversion("HelloTHISWorld", "HELLO_THIS_WORLD", s -> s.splitCamelCaseSmart().toUpperCase().join("_"));
	}

	private void assertConversion(String input, String expectedResult, Function<CaseConversionSplitter, String> conversionF) {
		CaseConversionSplitter convertCase = StringTools.convertCase(input);
		String result = conversionF.apply(convertCase);

		assertThat(result).as("Wrong conversion for input: " + input).isEqualTo(expectedResult);
	}

}
