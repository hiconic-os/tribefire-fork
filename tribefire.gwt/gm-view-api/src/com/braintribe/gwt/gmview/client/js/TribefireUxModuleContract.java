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
package com.braintribe.gwt.gmview.client.js;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmContentViewWindow;
import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import tribefire.extension.js.model.deployment.JsUxComponent;

@SuppressWarnings("unusable-by-js")
@JsType (namespace = InteropConstants.MODULE_NAMESPACE)
public abstract class TribefireUxModuleContract {
	
	@JsConstructor
	public TribefireUxModuleContract() {
		super();
	}
	
	/**
	 * Implementations which prepare the view in a sync way should use this method.
	 * @param context - the ComponentCreateContext
	 * @param denotation - The denotation type
	 */
	@JsMethod
	public native GmContentView createComponent(ComponentCreateContext context, JsUxComponent denotation);
	
	/**
	 * Implementations which prepare the view in an async way, should use this method instead.
	 * @param context - the ComponentCreateContext
	 * @param denotation - The denotation type
	 */
	@JsMethod
	public native Future<GmContentView> createComponentAsync(ComponentCreateContext context, JsUxComponent denotation);
	
	/**
	 * Implementations should use this method for binding service processors by using the given context.
	 * @param context - the ServiceBindingContext
	 */
	@JsMethod
	public native void bindServiceProcessors(ServiceBindingContext context);
	
	/**
	 * Implementations should use this method for binding the windows (if any) responsible for displaying the views.
	 * @param window - the window
	 */
	@JsMethod
	public native void bindWindow(GmContentViewWindow window);

}
