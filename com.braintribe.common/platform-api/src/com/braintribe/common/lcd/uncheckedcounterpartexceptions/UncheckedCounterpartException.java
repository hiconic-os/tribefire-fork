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
package com.braintribe.common.lcd.uncheckedcounterpartexceptions;

import com.braintribe.common.lcd.AbstractUncheckedBtException;
import com.braintribe.common.lcd.BtException;
import com.braintribe.common.lcd.GenericRuntimeException;

/**
 * Super class for <code>RuntimeException</code>s that are unchecked counterparts of checked <code>Exception</code>s.
 * This exception <b>requires</b> the {@link #getCause() cause} to be a checked exception! If you just want to throw an
 * unchecked {@link BtException} without having to implement your own, please use {@link GenericRuntimeException}
 * instead!
 *
 * @author michael.lafite
 */
public class UncheckedCounterpartException extends AbstractUncheckedBtException {

	private static final long serialVersionUID = -8282360971144294276L;

	protected UncheckedCounterpartException(final String message) {
		super(message);
	}

	/**
	 * Creates a new {@link UncheckedCounterpartException}.
	 *
	 * @param message
	 *            the exception message.
	 * @param cause
	 *            the cause which must be a checked exception (or <code>null</code>).
	 */
	protected UncheckedCounterpartException(final String message, final Exception cause) {
		super(message);
		// set cause via initCause method, because it checks the passed cause
		initCause(cause);
	}

	@Override
	public final synchronized UncheckedCounterpartException initCause(final Throwable cause) {
		if (cause instanceof RuntimeException) {
			throw new IllegalArgumentException(
					"The passed cause is not a checked exception! (for convenience it is added as cause of this exception)", cause);
		}
		if (!(cause instanceof Exception)) {
			throw new IllegalArgumentException("The passed cause is not an exception!", cause);
		}

		return (UncheckedCounterpartException) super.initCause(cause);
	}
}
