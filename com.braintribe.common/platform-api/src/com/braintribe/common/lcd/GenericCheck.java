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
 * A generic interface for checks.
 *
 * @author michael.lafite
 *
 * @param <T>
 *            the type of the check context.
 */
public interface GenericCheck<T> {

	/**
	 * Returns <code>true</code>, if the check succeeds, otherwise <code>false</code>. Alternatively also an exception may be thrown, but only if
	 * something unexpected happens (indicating that the caller can/should not continue as usual).
	 */
	boolean check(final T checkContext) throws GenericCheckException;

	/**
	 * Signals an error while {@link GenericCheck#check performing a check}.
	 *
	 * @author michael.lafite
	 */
	public class GenericCheckException extends AbstractUncheckedBtException {

		private static final long serialVersionUID = 3252530243293521569L;

		public GenericCheckException(final String msg) {
			super(msg);
		}

		public GenericCheckException(final String msg, final Throwable cause) {
			super(msg, cause);
		}
	}

	/**
	 * {@link GenericCheck} that always succeeds (i.e. returns <code>true</code>).
	 */
	public class SucceedingCheck<T> implements GenericCheck<T> {

		public SucceedingCheck() {
			// nothing to do
		}

		@Override
		public boolean check(final T checkContext) throws GenericCheck.GenericCheckException {
			return true;
		}
	}

	/**
	 * {@link GenericCheck} that always fails (i.e. returns <code>false</code>).
	 */
	public class FailingCheck<T> implements GenericCheck<T> {

		public FailingCheck() {
			// nothing to do
		}

		@Override
		public boolean check(final T checkContext) throws GenericCheck.GenericCheckException {
			return false;
		}
	}

	/**
	 * {@link GenericCheck} that always throws an exception.
	 */
	public class ErroneousCheck<T> implements GenericCheck<T> {

		public ErroneousCheck() {
			// nothing to do
		}

		@Override
		public boolean check(final T checkContext) throws GenericCheck.GenericCheckException {
			throw new GenericCheckException("Dummy error performing check. " + CommonTools.getParametersString("checkContext", checkContext));
		}
	}

}
