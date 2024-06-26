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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	public Date convert(String source) {
	
		if (source == null || source.trim().length() == 0) {
			return null;
		} else {
			
			if (source.equals("now()")) {
				return new Date();
			}
			
			String [] data = source.split( "\\|");
			SimpleDateFormat format = null;
			if (data.length == 1) {
				format = new SimpleDateFormat( "dd.MM.yyyy");				
			} else {
				format = new SimpleDateFormat( data[0]);
				source = data[1];
			}
			try {			
				Date date = format.parse( source);
				return date;
			}
			catch (ParseException e) {
				throw new IllegalArgumentException("[" + source + "] is not a valid format for a data, [" + format.toPattern() + "] expected", e);
			}
			 
		}
	}
	
	

}
