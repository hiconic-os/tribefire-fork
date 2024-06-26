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
package com.braintribe.model.processing.manipulator.api;

import com.braintribe.model.generic.reflection.GenericModelException;

/**
 * @deprecated Just don't use this, take {@link GenericModelException} or something specific to your problem.
 */
@Deprecated
public class ManipulatorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ManipulatorException() {
		super();
	}

	public ManipulatorException(String message, Throwable cause) {
		super(message, cause);
	}

	public ManipulatorException(String message) {
		super(message);
	}

	public ManipulatorException(Throwable cause) {
		super(cause);
	}

}