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
import java.util.stream.Stream;

/**
 * @author peter.gazdik
 */
public interface CaseConversionMapper extends CaseConversionJoiner {

	/**
	 * A helper method to apply mapping conditionally without breaking the fluent API.
	 * <p>
	 * If <tt>true</tt> is passed, this method returns this mapper, so the overall effect is if is this "when" method wasn't even called.
	 * <p>
	 * If <tt>false</tt> is passed , this method returns a special mapper which ignores the next method, so the overall effect is as
	 * if this "when" method and the subsequent mapping method weren't even called.
	 */
	
	CaseConversionMapper when(boolean condition);

	CaseConversionJoiner uncapitalizeAll();

	CaseConversionJoiner capitalizeAll();

	CaseConversionJoiner uncapitalizeFirst();

	CaseConversionJoiner capitalizeAllButFirst();

	CaseConversionJoiner toLowerCase();
	
	CaseConversionJoiner toUpperCase();

	CaseConversionJoiner map(Function<? super String, String> mapping);

	Stream<String> asStream();	

}
