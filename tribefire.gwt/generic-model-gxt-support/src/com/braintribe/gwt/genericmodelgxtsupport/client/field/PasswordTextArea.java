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
package com.braintribe.gwt.genericmodelgxtsupport.client.field;

import com.google.gwt.dom.client.Element;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 * Extension of the TextArea to handle passwords.
 * @author michel.docouto
 *
 */
public class PasswordTextArea extends TextArea {
	
	public PasswordTextArea() {
		getInputEl().getStyle().setColor("transparent");
		getInputEl().getStyle().setProperty("textShadow", "0 0 4px rgb(0,0,0)");
		getInputEl().getStyle().setProperty("caretColor", "black");
		getInputEl().addClassName("password-textarea");
		getInputEl().setAttribute("spellcheck", "false");
		
		disableCopy(getInputEl());
	}
	
	private native void disableCopy(Element el) /*-{
		el.addEventListener("copy", function(event) {
			event.preventDefault();
			event.stopPropagation();
		});
	}-*/;

}
