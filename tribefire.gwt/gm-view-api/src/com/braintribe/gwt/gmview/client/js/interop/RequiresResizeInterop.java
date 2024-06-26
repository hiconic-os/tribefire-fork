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
package com.braintribe.gwt.gmview.client.js.interop;

import com.google.gwt.user.client.ui.RequiresResize;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Interface used for exposing {@link RequiresResize} via JsInterop.
 */
@SuppressWarnings("unusable-by-js")
@JsType(name="RequiresResize", namespace = InteropConstants.VIEW_NAMESPACE)
public interface RequiresResizeInterop extends RequiresResize {
	
	@Override
	@JsMethod
	public void onResize();

}
