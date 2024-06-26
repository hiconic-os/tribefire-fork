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
package com.braintribe.model.processing.smood.id;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.smood.IdGenerator;

public class DateIdGenerator implements IdGenerator<Date> {

	private Date maxId = new Date(Long.MIN_VALUE);

	@Override
	public Date generateId(GenericEntity entity) {
		return generateId();
	}

	protected synchronized Date generateId() {
		Date result = new Date(); // current date

		if (!firstIsBiggerThanSecond(result, maxId)) {
			result = new Date(maxId.getTime() + 1);
		}

		return maxId = result;
	}

	@Override
	public synchronized void recognizeUsedId(Date id) {
		if (firstIsBiggerThanSecond(id, maxId)) {
			maxId = id;
		}
	}

	private static boolean firstIsBiggerThanSecond(Date d1, Date d2) {
		return d1.compareTo(d2) > 0;
	}

}
