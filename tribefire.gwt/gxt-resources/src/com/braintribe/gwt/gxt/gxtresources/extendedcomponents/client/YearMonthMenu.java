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

import java.util.Date;

import com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client.YearMonthDatePicker.YearhMonthDatePickerAppearance;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.DatePicker.DatePickerAppearance;
import com.sencha.gxt.widget.core.client.menu.DateMenu;

/**
 * Extends GXT's {@link DateMenu}, by showing only the month and year in the date picker.
 * @author michel.docouto
 *
 */
public class YearMonthMenu extends DateMenu {

	protected YearMonthDatePicker yearMonthDatePicker;
	protected DatePicker mockDatePicker;
	protected boolean mockDatePickerInitialized = false;

	public YearMonthMenu() {
		remove(picker);

		yearMonthDatePicker = new YearMonthDatePicker((DatePickerAppearance) GWT.create(YearhMonthDatePickerAppearance.class));

		yearMonthDatePicker.addValueChangeHandler(new ValueChangeHandler<Date>() {
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				onPickerSelect(event);
			}
		});

		add(yearMonthDatePicker);
	}

	@Override
	public void focus() {
		super.focus();
		yearMonthDatePicker.getElement().focus();
	}

	/**
	 * Returns the selected date.
	 * 
	 * @return the date
	 */
	@Override
	public Date getDate() {
		return yearMonthDatePicker.getValue();
	}

	/**
	 * Sets the menu's date.
	 * 
	 * @param date the date
	 */
	@Override
	public void setDate(Date date) {
		yearMonthDatePicker.setValue(date);
	}
	
	@Override
	public DatePicker getDatePicker() {
		if (mockDatePicker == null) {
			mockDatePicker = new DatePicker() {
				@Override
				public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
					if (mockDatePickerInitialized)
						return yearMonthDatePicker.addValueChangeHandler(handler);
					
					return null;
				}
				
				@Override
				public void focus() {
					if (mockDatePickerInitialized)
						yearMonthDatePicker.focus();
				}
				
				@Override
				public XElement getElement() {
					return mockDatePickerInitialized ? yearMonthDatePicker.getElement() : super.getElement();
				}
				
				@Override
				public void setMaxDate(Date maxDate) {
					if (mockDatePickerInitialized)
						yearMonthDatePicker.setMaxDate(maxDate);
				}
				
				@Override
				public void setMinDate(Date minDate) {
					if (mockDatePickerInitialized)
						yearMonthDatePicker.setMinDate(minDate);
				}
				
				@Override
				public void setValue(Date date, boolean fireEvents) {
					if (mockDatePickerInitialized)
						yearMonthDatePicker.setValue(date, fireEvents);
				}
			};
				
			mockDatePickerInitialized = true;
		}
		
		return mockDatePicker;
	}

}
