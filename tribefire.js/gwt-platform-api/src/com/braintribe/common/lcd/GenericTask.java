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
 * Generic interface that may be used for classes that perform tasks. One purpose of this interface is to avoid creating
 * undesirable dependencies.
 *
 * @author michael.lafite
 *
 * @see GenericTaskWithContext
 */
public interface GenericTask {

	void perform() throws GenericTaskException;

	/**
	 * Signals an error while {@link GenericTask#perform() performing a task}.
	 *
	 * @author michael.lafite
	 */
	public class GenericTaskException extends AbstractUncheckedBtException {

		private static final long serialVersionUID = 9013693454009033449L;

		public GenericTaskException(final String msg) {
			super(msg);
		}

		public GenericTaskException(final String msg, final Throwable cause) {
			super(msg, cause);
		}
	}
}
