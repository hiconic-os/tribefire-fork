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
package com.braintribe.gwt.browserfeatures.client.intersection;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class IntersectionObserver extends JavaScriptObject{
	protected IntersectionObserver() {
		
	}
	
	public final native void observe(Element e) /*-{
		this.observe(e);
	}-*/;
	
	public final native void observe(String selector) /*-{
		this.observe($doc.querySelector(selector));
	}-*/;
	
	public static native IntersectionObserver create(Element el, IntersectionObserverCallback callback) /*-{
		return new IntersectionObserver(function(entries){
			for(i=0; i<entries.length;i++){
				entry = entries[i]
				callback(entry.isIntersecting, entry.target);
			}
		}, {root: el, threshold: [0]});
	}-*/;
	
	public static native boolean isSupported() /*-{
		if($wnd.IntersectionObserver)
			return true;
		else
			return false;
	}-*/;

	public final native void disconnect()/*-{
		this.disconnect();
	}-*/;
}
