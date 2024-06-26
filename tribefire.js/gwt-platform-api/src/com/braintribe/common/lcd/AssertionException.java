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

import com.braintribe.utils.lcd.CommonTools;

/**
 * This exception is thrown (manually) to indicate that an assertion (i.e. a some check) has failed. Note that this is
 * not the same as {@link AssertionError}, which is thrown by <code>assert</code>.
 *
 * @author michael.lafite
 */
public class AssertionException extends AbstractUncheckedBtException {

	private static final long serialVersionUID = 3017226426669205792L;

	public AssertionException(final String message, final Object actualValue, final Object expectedValue) {
		super(message + Constants.LINE_SEPARATOR + "Expected Value: " + CommonTools.getStringRepresentation(expectedValue) + "Actual Value:   "
				+ CommonTools.getStringRepresentation(actualValue));
	}

	public AssertionException(final Object actualValue, final Object expectedValue) {
		this("Assertion failed! Actual value doesn't match expected one!", expectedValue, actualValue);
	}

	public AssertionException(final String message) {
		super(message);
	}
}
