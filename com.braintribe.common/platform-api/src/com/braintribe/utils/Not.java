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
package com.braintribe.utils;

import com.braintribe.common.lcd.AssertionException;

/**
 * This class may contain Java-only (i.e. GWT incompatible) code. For further information please see {@link com.braintribe.utils.lcd.Not}.
 *
 */
public class Not extends com.braintribe.utils.lcd.Not {

	/**
	 * Asserts the passed <code>object</code> is not <code>null</code>.
	 */
	public static <T> T Null(T object, String errorMessage, Object... args) {
		if (object == null) {
			throw new AssertionException(String.format(errorMessage, args)); // String.format() is not supported in GWT.
		}
		return object;
	}

}
