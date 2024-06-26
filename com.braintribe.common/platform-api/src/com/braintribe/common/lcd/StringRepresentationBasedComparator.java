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
package com.braintribe.common.lcd;

import java.util.Comparator;

/**
 * {@link Comparator} that compares objects based on their string representations.
 *
 * @author michael.lafite
 */
public class StringRepresentationBasedComparator implements Comparator<Object> {

	@Override
	public int compare(final Object object1, final Object object2) {
		final String stringRepresentation1 = String.valueOf(object1);
		final String stringRepresentation2 = String.valueOf(object2);
		return stringRepresentation1.compareTo(stringRepresentation2);
	}
}
