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

/**
 * This exception can be used, if one wants to throw an {@link UncheckedBtException}, but if it's not worth it to create a new exception class.
 *
 * @author michael.lafite
 */
public class GenericRuntimeException extends AbstractUncheckedBtException {

	private static final long serialVersionUID = -8428924677824553505L;

	public GenericRuntimeException(final String message) {
		super(message);
	}

	/**
	 * Creates a new {@link GenericRuntimeException} instance.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the cause of the exception.
	 */
	public GenericRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
