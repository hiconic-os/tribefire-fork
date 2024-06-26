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
package com.braintribe.gwt.genericmodelgxtsupport.client.field.color;

import com.google.gwt.dom.client.Element;
/**
 * A helpful class to set transparencies in browsers GWT supports
 */
public class TransparencyImpl
{
    // Get IE version (provided by Microsoft)
    public static native float getIEVersion()
    /*-{
        var rv = -1; // Return value assumes failure.
        if (navigator.appName == 'Microsoft Internet Explorer')
        {
            var ua = navigator.userAgent;
            var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
            if (re.exec(ua) != null)
              rv = parseFloat( RegExp.$1 );
        }
        return rv;
    }-*/;

    public static void setBackgroundColor(Element elem, String color)
    {
        try
        {
        	elem.getStyle().setBackgroundColor(color);
        }
        catch (com.google.gwt.core.client.JavaScriptException e)
        {
            // Called if backgroundColor could not be set.
        }
    }

    /* Given a DOM element, set the transparency value, with 100 being fully opaque and
     * 0 being fully transparent
     * @param elem A com.google.gwt.user.client.Element object
     * @param alpha An alpha value
     */
    public static void setTransparency(Element elem, int alpha)
    {
        float ieVersion = getIEVersion();

        if (ieVersion >= 7.0)
        {
        	elem.getStyle().setProperty("filter", "alpha(opacity="+alpha+")");
        }
        else // Everyone else
        {
        	setMozOpacity(elem,(new Integer(alpha).floatValue() / 100)+"");
            //DOM.setStyleAttribute(elem, "-moz-opacity", ""+(new Integer(alpha).floatValue() / 100)+"");
        	elem.getStyle().setOpacity(new Integer(alpha).floatValue() / 100);
        }
    }
    
    //workaround for to strict debugger....
    private static native void setMozOpacity(Element elem, String opacity) /*-{
    	elem.style["-moz-opacity"] = opacity;
  	}-*/;
}
