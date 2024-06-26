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
/**
 * Base class to use CheckBox items inside the ComboBox
 *
 */
package com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client;

public class CheckValueItem {
	private boolean selected;
	
	public void setSelected(boolean selected) {
	    this.selected = selected;
	}

	public boolean getSelected() {
	   return selected;
	}	
}
