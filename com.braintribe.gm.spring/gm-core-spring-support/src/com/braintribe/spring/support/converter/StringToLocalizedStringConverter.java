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
package com.braintribe.spring.support.converter;

import java.util.HashMap;

import org.springframework.core.convert.converter.Converter;

import com.braintribe.model.generic.i18n.LocalizedString;

public class StringToLocalizedStringConverter implements Converter<String, LocalizedString>{
	
	private String locale = "default";
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	@Override
	public LocalizedString convert(String source) {
		LocalizedString lc = LocalizedString.T.create();
		lc.setLocalizedValues(new HashMap<String, String>());
		lc.getLocalizedValues().put(locale, source);
		return lc;
	}
	
	

}
