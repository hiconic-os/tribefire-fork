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

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

public class CssLoader {
	
	public static void unloadCss(String id) {
		Element cssElement = Document.get().getElementById(id);
		if (cssElement != null)
			cssElement.removeFromParent();
	}
	
	public static native void loadCss(String id, String url) /*-{
    	var l = $doc.createElement("link");
    	l.setAttribute("id", id);
    	l.setAttribute("rel", "stylesheet");
    	l.setAttribute("type", "text/css");
    	l.setAttribute("href", url); // Make sure this request is not cached
    	$doc.getElementsByTagName("head")[0].appendChild(l);
	}-*/;

}
