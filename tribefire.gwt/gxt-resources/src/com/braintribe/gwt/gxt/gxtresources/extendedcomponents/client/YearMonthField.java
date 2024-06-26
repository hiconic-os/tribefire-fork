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
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.form.DateField;

/**
 * Extends the {@link DateField} by showing only the month and year options.
 * @author michel.docouto
 *
 */
public class YearMonthField extends DateField {
	
	public YearMonthField(DateCell cell) {
		super(cell);
		
		YearMonthMenu menu = new YearMonthMenu();
		getCell().setMenu(menu);
		menu.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent event) {
				focus();
			}
		});
	}
	
	public YearMonthField() {
		this(new DateCell());
	}

}
