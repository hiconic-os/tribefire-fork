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
package com.braintribe.gwt.browserfeatures.client;

import com.google.gwt.xhr.client.XMLHttpRequest;

/**
 * This is an implementation of the XMLHttpRequest, but for preparing sync calls.
 * @author michel.docouto
 *
 */
public class SyncXMLHttpRequest extends XMLHttpRequest {
	
	protected SyncXMLHttpRequest() {
	}
	
	/**
	 * Creates an XMLHttpRequest object.
	 * 
	 * @return the created object
	 */
	public static native SyncXMLHttpRequest create() /*-{
		// Don't check window.XMLHttpRequest, because it can
		// cause cross-site problems on IE8 if window's URL
		// is javascript:'' .
		if ($wnd.XMLHttpRequest) {
			return new $wnd.XMLHttpRequest();
		} else {
			try {
				return new $wnd.ActiveXObject('MSXML2.XMLHTTP.3.0');
			} catch (e) {
				return new $wnd.ActiveXObject("Microsoft.XMLHTTP");
			}
		}
	}-*/;
	
	public final native String sendSynchronous(String method, String url) /*-{
		this.open(method, url, false);
		this.send(null);
		var serverResponse = this.responseText;
		return serverResponse;
	}-*/;
	
}
