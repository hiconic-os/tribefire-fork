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
package edu.ycp.cs.dh.acegwt.client.ace;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Callback interface for events generated by {@link AceEditor}.
 * Note that the argument the callback receives is a
 * JavaScriptObject, so you will probably need to use JSNI to
 * make use of it.
 */
public interface AceEditorCallback {
	/**
	 * Callback method.
	 * 
	 * @param obj the event object: for example, an onChange event
	 *            if this callback is receiving onChange events
	 */
	public void invokeAceCallback(JavaScriptObject obj);
}
