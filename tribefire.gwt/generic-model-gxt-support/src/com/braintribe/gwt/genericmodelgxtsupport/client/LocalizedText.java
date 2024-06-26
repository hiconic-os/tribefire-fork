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
package com.braintribe.gwt.genericmodelgxtsupport.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface LocalizedText extends Messages {
	
	public static LocalizedText INSTANCE = GWT.create(LocalizedText.class);
	
	public String add();
	public String addLocaleDescription();
	public String addLocalization();
	public String addLocalizationDescription();
	public String apply();
	public String cancel();
	public String cancelDescription();
	public String changeColor();
	public String changeColorDescription();
	public String chooseColor();
	public String clear();
	public String clearLocalizationsDescription();
	public String closeDescription();
	public String color();
	public String fontDialog();
	public String fontName();
	public String fontSize();
	public String fontStyle();
	public String fontWeight();
	String htmlEditor();
	public String invalidColor();
	public String invalidFormat();
	public String invalidTime();
	public String locale();
	public String localeExistsAlready();
	public String localizedValues();
	public String of();
	public String ok();
	public String remove();
	public String removeLocalizationDescription();
	public String value();
	String hourMinuteSecondRegex();
	String hourMinuteSecondMillisecondRegex();
	String multiline();
	String multilineDescription();
	
}
