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

import com.braintribe.gwt.gmview.client.GmContentViewWindow;

import tribefire.extension.js.model.deployment.JsUxComponent;

public class JsUxComponentContext {
	
	private JsUxComponent jsUxComponent;
	private GmContentViewWindow window;
	
	public JsUxComponentContext(JsUxComponent jsUxComponent) {
		this.jsUxComponent = jsUxComponent;
	}
	
	public JsUxComponentContext(JsUxComponent jsUxComponent, GmContentViewWindow window) {
		this(jsUxComponent);
		this.window = window;
	}

	public JsUxComponent getJsUxComponent() {
		return jsUxComponent;
	}

	public void setJsUxComponent(JsUxComponent jsUxComponent) {
		this.jsUxComponent = jsUxComponent;
	}

	public GmContentViewWindow getWindow() {
		return window;
	}

	public void setWindow(GmContentViewWindow window) {
		this.window = window;
	}

}
