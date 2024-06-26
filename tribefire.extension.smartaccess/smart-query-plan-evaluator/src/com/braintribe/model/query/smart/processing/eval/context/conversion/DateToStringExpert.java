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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.braintribe.model.accessdeployment.smart.meta.conversion.DateToString;
import com.braintribe.model.processing.smartquery.eval.api.RuntimeSmartQueryEvaluationException;

/**
 * 
 */
public class DateToStringExpert extends AbstractToStringExpert<Date, DateToString> {

	public static final DateToStringExpert INSTANCE = new DateToStringExpert();

	private DateToStringExpert() {
	}

	@Override
	protected Date parse(String value, DateToString conversion) {
		SimpleDateFormat sdf = new SimpleDateFormat(conversion.getPattern());

		try {
			return sdf.parse(value);

		} catch (ParseException e) {
			throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + value + "' to a Date.", e);
		}
	}

	@Override
	protected String toString(Date value, DateToString conversion) {
		SimpleDateFormat sdf = new SimpleDateFormat(conversion.getPattern());

		return sdf.format(value);
	}

}
