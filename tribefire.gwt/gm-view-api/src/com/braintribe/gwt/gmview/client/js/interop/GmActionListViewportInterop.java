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

import com.braintribe.gwt.gmview.actionbar.client.GmViewActionProvider;
import com.braintribe.gwt.gmview.client.GmActionSupport;
import com.braintribe.gwt.gmview.client.GmListView;
import com.braintribe.gwt.gmview.client.GmViewport;
import com.braintribe.gwt.gmview.client.GmViewportListener;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Class needed for being able to export the following classes via JsInterop: {@link GmListView},
 * {@link GmActionSupport}, {@link GmViewActionProvider} and {@link GmViewport}.
 */
@JsType(name = "GmActionListViewport", namespace = InteropConstants.VIEW_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class GmActionListViewportInterop extends GmActionListViewInterop implements GmViewportInterfaceInterop {
	
	@JsConstructor
	public GmActionListViewportInterop() {
		super();
	}

	@Override
	@JsMethod
	public void addGmViewportListener(GmViewportListener vl) {
		//NOP
	}

	@Override
	@JsMethod
	public void removeGmViewportListener(GmViewportListener vl) {
		//NOP
	}

	@Override
	@JsMethod
	public boolean isWindowOverlappingFillingSensorArea() {
		return false;
	}
	
	@Override
	@JsMethod
	public void fireViewportListener(GmViewportListener listener) {
		listener.onWindowChanged(this);
	}

}
