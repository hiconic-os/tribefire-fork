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
package com.braintribe.gwt.codec.string.client;

import java.util.Date;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.ioc.client.Configurable;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * A date string {@link Codec} using {@link Date} as value class and an instance of {@link DateTimeFormat}
 * to parse and format {@link Date} values; 
 * @author Dirk
 *
 */
public class GwtDateCodec implements Codec<Date, String> {
	private DateTimeFormat format = DateTimeFormat.getFormat("dd.MM.yyyy");
	
	/**
	 * Sets the {@link DateTimeFormat} used for decoding the String into Date
	 * and vice-versa.
	 */
	@Configurable
	public void setFormat(DateTimeFormat format) {
		this.format = format;
	}
	
	/**
	 * Sets the format used for preparing the {@link DateTimeFormat} used for
	 * decoding the String into Date and vice-versa.
	 */
	@Configurable
	public void setFormatByString(String format) {
		this.format = DateTimeFormat.getFormat(format);
	}
	
	@Override
	public Date decode(String encodedValue) throws CodecException {
		try {
			if (encodedValue == null || encodedValue.trim().length() == 0)
				return null;
			
			Date date = format.parse(encodedValue);
			return date;
		} catch (Exception e) {
			throw new CodecException("Failed decoding the date value: " + encodedValue, e);
		}
	}
	
	@Override
	public String encode(Date value) throws CodecException {
		if (value == null)
			return "";
		
		return format.format(value);
	}
	
	@Override
	public Class<Date> getValueClass() {
		return Date.class;
	}
}
