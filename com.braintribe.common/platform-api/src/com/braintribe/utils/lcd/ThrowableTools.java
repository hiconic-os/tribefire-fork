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
package com.braintribe.utils.lcd;

/**
 * This class provides utility methods related to {@link Throwable}s.
 *
 * @author michael.lafite
 */
public class ThrowableTools {

	protected ThrowableTools() {
		// nothing to do
	}

	/**
	 * Returns the root cause of the passed <code>throwable</code>.
	 */
	public static Throwable getRootCause(final Throwable throwable) {
		Throwable rootCause = throwable;
		while (rootCause.getCause() != null) {
			rootCause = rootCause.getCause();
		}
		return rootCause;
	}

	/**
	 * Sets the <code>cause</code> of the <code>throwable</code> and returns the <code>throwable</code>. This is a convenience method for throwables
	 * that don't have a constructor where one can set the cause.
	 */
	public static <T extends Throwable> T getWithCause(final T throwable, final Throwable cause) {
		throwable.initCause(cause);
		return throwable;
	}

}
