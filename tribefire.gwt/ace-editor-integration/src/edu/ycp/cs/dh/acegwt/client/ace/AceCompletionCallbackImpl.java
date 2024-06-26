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
import com.google.gwt.core.client.JsArray;

/**
 * Implementation of {@link AceCompletionCallback}
 * that delegates to a native JavaScript Ace code completion
 * callback.
 */
class AceCompletionCallbackImpl implements AceCompletionCallback {
	private JavaScriptObject jsCallback;
	
	public AceCompletionCallbackImpl(JavaScriptObject jsCallback) {
		this.jsCallback = jsCallback;
	}
	
	@Override
	public void invokeWithCompletions(AceCompletion[] proposals) {
		JsArray<JavaScriptObject> jsProposals = JavaScriptObject.createArray().cast();
		for (AceCompletion proposal : proposals) {
			jsProposals.push(proposal.toJsObject());
		}
		doInvokeWithCompletions(jsProposals);
	}
	
	private native void doInvokeWithCompletions(JsArray<JavaScriptObject> jsProposals) /*-{
		var callback = this.@edu.ycp.cs.dh.acegwt.client.ace.AceCompletionCallbackImpl::jsCallback;
		callback(null, jsProposals);
	}-*/;
}
