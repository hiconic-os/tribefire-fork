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
 * This exception signals that an enum constant couldn't be handled because it's unknown. If your code is intended to
 * handle all constants of an enum type but it still finds an unknown constant, throw this exception (for example in the
 * default case of a switch statement). In doing so one will get a proper exception if someone adds a new enum constant
 * but forgets to update your code.
 *
 * @author michael.lafite
 * @see UnsupportedEnumException
 */
public class UnknownEnumException extends AbstractUncheckedBtException {

	private static final long serialVersionUID = -7752127746828963233L;

	public UnknownEnumException(final Enum<?> enumConstant) {
		super("Enum constant " + enumConstant + " of enum type " + enumConstant.getClass().getName() + " is unknown!");
	}
}
