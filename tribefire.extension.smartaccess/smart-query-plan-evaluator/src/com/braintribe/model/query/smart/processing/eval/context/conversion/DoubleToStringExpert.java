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

import com.braintribe.model.accessdeployment.smart.meta.conversion.DoubleToString;

/**
 * 
 */
public class DoubleToStringExpert extends AbstractDecimalToStringExpert<Double, DoubleToString> {

	public static final DoubleToStringExpert INSTANCE = new DoubleToStringExpert();

	protected DoubleToStringExpert() {
	}

	@Override
	protected Double parse(String value, DoubleToString conversion) {
		return parseNumber(value, conversion.getPattern()).doubleValue();
	}

	@Override
	protected String toString(Double value, DoubleToString conversion) {
		return toString(value, conversion.getPattern());
	}

}
