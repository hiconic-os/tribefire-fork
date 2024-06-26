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
package com.braintribe.model.query.smart.processing.eval.context.conversion;

import java.text.DecimalFormat;
import java.text.ParseException;

import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.query.smart.processing.SmartQueryEvaluatorRuntimeException;

/**
 * 
 */
public abstract class AbstractDecimalToStringExpert<T extends Number, C extends SmartConversion> extends AbstractToStringExpert<T, C> {

	protected Number parseNumber(String value, String pattern) {
		try {
			return formatFor(pattern).parse(value);

		} catch (ParseException e) {
			throw new SmartQueryEvaluatorRuntimeException("Error while parsing String: " + value, e);
		}
	}

	protected String toString(Number value, String pattern) {
		return formatFor(pattern).format(value);
	}

	/**
	 * This is not nice, but let's have this protected so that we can also set the decimal separator in unit tests. Maybe later we'll also
	 * want to configure it per conversion...
	 */
	protected DecimalFormat formatFor(String pattern) {
		return pattern == null ? new DecimalFormat() : new DecimalFormat(pattern);
	}

}
