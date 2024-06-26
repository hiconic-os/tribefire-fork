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
package com.braintribe.spring.support;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * DateEditor : text can have two forms
 * 
 * a) "dd.MM.yyyy" 
 * b) formatPattern | date value as specified in format
 * 
 * @author pit
 *
 */
public class DateEditor extends PropertyEditorSupport {
	
	public String getAsText() {
		return "";
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.trim().length() == 0) {
			setValue(null);
		} else {
			String [] data = text.split( "\\|");
			SimpleDateFormat format = null;
			if (data.length == 1) {
				format = new SimpleDateFormat( "dd.MM.yyyy");				
			} else {
				format = new SimpleDateFormat( data[0]);
				text = data[1];
			}
			try {			
				Date date = format.parse( text);
				setValue( date);
			}
			catch (ParseException e) {
				throw new IllegalArgumentException("[" + text + "] is not a valid format for a data, [" + format.toPattern() + "] expected", e);
			}
			 
		}
	}

}
