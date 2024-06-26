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
package com.braintribe.model.processing.smart.query.planner.tools;

import com.braintribe.model.accessdeployment.smart.meta.conversion.IntegerToString;
import com.braintribe.model.accessdeployment.smart.meta.conversion.LongToString;
import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.query.Operator;

/**
 * 
 */
public class SmartConversionTools {

	public static boolean isEqualityBasedOperator(Operator operator) {
		switch (operator) {
			case contains:
			case in:
			case equal:
			case notEqual:
				return true;
			default:
				return false;
		}
	}

	/**
	 * Some conversions can be delegated when such converted property is used in a conditions. For example, if our "convertedProperty" is integer in
	 * the delegate, but a String in smart, and we use conditions <tt>convertedProperty like '.*2'"</tt> (ends with two), we can delegate this as
	 * <tt>asString(convertedProperty) like '.*2'</tt>.
	 * 
	 * For now, we only consider non-parameter [number]ToString conversions delegate-able, i.e. only {@link IntegerToString} and {@link LongToString}.
	 */
	public static boolean isDelegateableToStringConversion(SmartConversion c) {
		if (c.getInverse())
			return false;

		return c instanceof IntegerToString || c instanceof LongToString;
	}

}
