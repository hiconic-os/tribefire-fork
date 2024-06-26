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
package com.braintribe.model.query.smart.processing.eval.context;

import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.smartqueryplan.filter.SmartFullText;

/**
 * 
 */
class SmartFulltextComparator {

	static boolean matches(Tuple tuple, SmartFullText condition) {
		String text = condition.getText();

		for (Integer position: condition.getStringPropertyPositions()) {
			String value = (String) tuple.getValue(position);

			if (value != null && value.contains(text)) {
				return true;
			}
		}

		return false;
	}

}
