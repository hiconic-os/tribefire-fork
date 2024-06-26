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
package com.braintribe.logging;

import java.awt.Component;

public interface ErrorFeedback {

	public void reportError(String msg, Throwable ex, boolean critical);

	/**
	 * reports an error
	 * 
	 * @param msg
	 *            the error message
	 * @param ex
	 *            the exception that cause the error (or null)
	 * @param critical
	 *            if true, the error is critial. Exiting the application may be recommended.
	 */
	public void reportError(Component component, String msg, Throwable ex, boolean critical);

	/**
	 * reports an error, prompting for retry
	 * 
	 * @param msg
	 *            the error message
	 * @param ex
	 *            the exception that cause the error (or null)
	 * @param allowExit
	 *            if true, the user may choose to terminate the application.
	 */
	public boolean reportErrorWithRetry(Component component, String msg, Throwable ex, boolean allowExit);
}
