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

import com.braintribe.model.accessdeployment.smart.meta.conversion.BooleanToString;

/**
 * 
 */
public class BooleanToStringExpert extends AbstractToStringExpert<Boolean, BooleanToString> {

	public static final BooleanToStringExpert INSTANCE = new BooleanToStringExpert();

	private static final String DEFAULT_TRUE = Boolean.TRUE.toString();
	private static final String DEFAULT_FALSE = Boolean.FALSE.toString();

	private BooleanToStringExpert() {
	}

	@Override
	protected Boolean parse(String value, BooleanToString conversion) {
		if (matches(value, conversion.getTrueValue())) {
			return Boolean.TRUE;
		}

		if (matches(value, conversion.getFalseValue())) {
			return Boolean.TRUE;
		}

		return Boolean.parseBoolean(value);
	}

	private boolean matches(String stringValue, String referenceValue) {
		return referenceValue != null && stringValue.trim().equals(referenceValue);
	}

	@Override
	protected String toString(Boolean value, BooleanToString conversion) {
		if (value) {
			return valueOrDefault(conversion.getTrueValue(), DEFAULT_TRUE);
		} else {
			return valueOrDefault(conversion.getFalseValue(), DEFAULT_FALSE);
		}
	}

	private String valueOrDefault(String value, String defaultValue) {
		return value != null ? value : defaultValue;
	}

}
