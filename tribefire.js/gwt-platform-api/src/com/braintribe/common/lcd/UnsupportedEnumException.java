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
 * This exception signals that an enum constant couldn't be handled because it's not supported. Use this exception if
 * you are aware that there are one or more constants that your code cannot handle (yet).
 *
 * @author michael.lafite
 * @see UnknownEnumException
 */
public class UnsupportedEnumException extends AbstractUncheckedBtException {

	private static final long serialVersionUID = -3154492481256430408L;

	public UnsupportedEnumException(String message) {
		super(message);
	}

	public UnsupportedEnumException(final Enum<?> enumConstant) {
		super("Enum constant " + enumConstant + " of enum type " + enumConstant.getClass().getName() + " is not supported!");
	}

}
