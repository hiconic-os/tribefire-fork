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
package com.braintribe.gwt.gme.verticaltabpanel.client;

import com.google.gwt.dom.client.InputElement;
import com.sencha.gxt.widget.core.client.form.TextField;

public class MaxLengthTextField extends TextField {
	
	public MaxLengthTextField (int maxLength) {
		InputElement ie= this.getInputEl().cast();
		ie.setMaxLength(maxLength);
	}
	
}
