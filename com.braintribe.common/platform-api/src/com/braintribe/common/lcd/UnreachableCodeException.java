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
 * Indicates an unanticipated execution path.
 *
 * @author michael.lafite
 */
public class UnreachableCodeException extends AbstractUncheckedBtException {

	private static final long serialVersionUID = 5969949996442696699L;

	public UnreachableCodeException() {
		this(null);
	}

	public UnreachableCodeException(final String furtherInfo) {
		this(null, furtherInfo);
	}

	public UnreachableCodeException(final String furtherInfo, final Throwable cause) {
		this(null, furtherInfo, cause);
	}

	public UnreachableCodeException(final String mainMessage, final String furtherInfo) {
		this(mainMessage, furtherInfo, null);
	}

	public UnreachableCodeException(final String mainMessage, final String furtherInfo, final Throwable cause) {
		super((mainMessage == null ? "Unanticipated execution path! This code should not be reachable!" : mainMessage)
				+ (furtherInfo == null ? "" : " Further info: " + furtherInfo), cause);
	}
}
