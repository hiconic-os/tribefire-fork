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

import com.google.gwt.dom.client.Element;

public class ScrollUtil {
		
	public static native final void scrollIntoView(Element el) /*-{
		if(el){		
			bounding = el.getBoundingClientRect();		
			isVisible = (0 < bounding.top && bounding.top < (screen.availHeight)) ||
        		(0 < bounding.bottom && bounding.bottom < (screen.availHeight ));        	
	        if(!isVisible){	        	
				el.scrollIntoView({behavior: "auto", block: "start"});
			}
		}
	}-*/;
	
	public static native final void forceScrollIntoView(Element el) /*-{
		el.scrollIntoView({behavior: "auto", block: "start"});
	}-*/;
	
	public static native final boolean isInViewport(Element el) /*-{
		rect = el.getBoundingClientRect();
		elementHeight = el.offsetHeight;
		elementWidth = el.offsetWidth;
		
		return (rect.top >= elementHeight &&
	        rect.left >= elementWidth &&
	        rect.bottom <= ($wnd.innerHeight || $doc.documentElement.clientHeight) + elementHeight &&
	        rect.right <= ($wnd.innerWidth || $doc.documentElement.clientWidth) + elementWidth);
	}-*/;
	
	public static native final void scrollLeft(Element el, int diffX) /*-{
	if(el){		
		el.scrollLeft += diffX;
	}
	}-*/;	

	public static native final void scrollTop(Element el, int diffY) /*-{
	if(el){		
		el.scrollTop += diffY;
	}
	}-*/;			
}
