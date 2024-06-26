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
package com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client;

import com.sencha.gxt.cell.core.client.form.DateCell;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;

/**
 * Extends the {@link DateField} by adding a time field to its date picker.
 * @author michel.docouto
 *
 */
public class DateTimeField extends DateField {
	
	protected DateTimeMenu menu;
	
	public DateTimeField(DateCell cell) {
		super(cell, new DateTimePropertyEditor());
		
		menu = new DateTimeMenu();
		getCell().setMenu(menu);
		menu.addHideHandler(event -> focus());
	}
	
	public DateTimeField() {
		this(new DateCell());
	}
	
	public void setTimeRegex(String timeRegex, String pattern) {
		menu.setTimeRegex(timeRegex, pattern);
	}
	
	/**
	 * Configures whether to use seconds. Defaults to false.
	 */
	public void setUseSeconds(boolean useSeconds) {
		menu.setUseSeconds(useSeconds);
	}
	
	/**
	 * Configures whether to use milliseconds. Defaults to false.
	 */
	public void setUseMilliseconds(boolean useMilliseconds) {
		menu.setUseMilliseconds(useMilliseconds);
	}

}
