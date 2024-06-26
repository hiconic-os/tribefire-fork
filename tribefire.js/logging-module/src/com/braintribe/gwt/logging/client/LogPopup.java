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
package com.braintribe.gwt.logging.client;

import com.google.gwt.core.client.JavaScriptObject;

public class LogPopup extends JavaScriptObject {
	
	protected LogPopup() {
	}
	
	public final native boolean isClosed() /*-{
		return this.closed;
	}-*/; 
	
	public static native LogPopup open(String title) /*-{
        var logWindow = $wnd.open("about:blank", "WebReaderLog", "location=no,menubar=no,resizable=yes,scrollbars=yes,status=no,toolbar=no");
        var doc = logWindow.document;
        
		doc.open();
		doc.write("<html>");
		doc.write("<body style='margin:0px;padding:0px;font-family:monospace;font-size:12px'>");
		doc.write("<table style='position:absolute;width:100%;height:100%;' cellspacing='0' cellpadding='0' >");
		doc.write("<tr>");
		doc.write("<td class='gxtReset' style='height:30px;background-color:#e0e0e0;border:0px solid #a0a0a0;border-bottom-width:1px'>");
		doc.write("<table ><tr><td class='gxtReset'><input id='autoscroll' type='checkbox' checked='checked'/></td><td class='gxtReset'>Autoscroll</td><td class='gxtReset'><button onclick='document.getElementById(\"log\").innerHTML=\"\"'>Clear</button></td></tr></table>");
		doc.write("</td>");
		doc.write("</tr>");
		doc.write("<tr>");
		doc.write("<td class='gxtReset' style='background-color:white;'>");
		doc.write("<div style='width:100%;height:100%;position:relative'>");
		doc.write("<div style='position:absolute; width:100%; height:100%; overflow:auto; padding: 0px'>");
		doc.write("<div id='log' style='padding:4px'>");
		doc.write("</div>");
		doc.write("</div>");
		doc.write("</div>");
		doc.write("</td>");
		doc.write("</tr>");
		doc.write("</table>");
		doc.write("</body>");
		doc.write("</html>");
		doc.close();
		
        doc.title = title;
        
        return logWindow;
	}-*/;
	
	public final native void appendLine(String line, String color) /*-{
		var doc = this.document;
		var log = doc.getElementById('log');
		var autoscroll = doc.getElementById('autoscroll').checked;
		
		var pre = doc.createElement("pre");
		pre.style.color = color;
		pre.style.margin = "0px";
		pre.style.overflow = "hidden";
		var text = doc.createTextNode(line);
		pre.appendChild(text);
		log.appendChild(pre);
		
		if (autoscroll)
			pre.scrollIntoView();
	}-*/;
	
	public final native void focus() /*-{
		this.focus();
	}-*/;
}
