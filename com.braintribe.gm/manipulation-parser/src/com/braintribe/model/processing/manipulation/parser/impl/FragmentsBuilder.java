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
package com.braintribe.model.processing.manipulation.parser.impl;

import com.braintribe.model.time.CalendarOffset;
import com.braintribe.model.time.DateOffset;
import com.braintribe.model.time.DateOffsetUnit;
import com.braintribe.model.time.TimeZoneOffset;

/**
 * A convenient class that contains all the instantiations any Query related model
 */
public interface FragmentsBuilder {

	public static CalendarOffset dateOffset(Integer value, DateOffsetUnit unit) {
		DateOffset dateOffset = DateOffset.T.create();
		dateOffset.setOffset(unit);
		dateOffset.setValue(value);
		return dateOffset;
	}

	public static CalendarOffset timeZoneOffset(int minutes) {
		TimeZoneOffset timeZoneOffset = TimeZoneOffset.T.create();
		timeZoneOffset.setMinutes(minutes);
		return timeZoneOffset;
	}

}