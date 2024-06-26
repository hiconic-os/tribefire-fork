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

import java.text.ParseException;
import java.util.Date;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;

/**
 * This {@link DateTimePropertyEditor} implementation can be configured with a Codec for handling string/date encoding/decoding.
 * @author michel.docouto
 *
 */
public class CodecDateTimePropertyEditor extends DateTimePropertyEditor {
	
	private Codec<Date, String> codec;
	
	public CodecDateTimePropertyEditor() {
		format = DateTimeFormat.getFormat("dd.MM.yyyy");
	}
	
	/**
	 * Configures the required codec use for validation.
	 */
	public void setCodec(Codec<Date, String> codec) {
		this.codec = codec;
	}
	
	/**
	 * Configures the default pattern, used for returning a default DateTimeFormat.
	 * Defaults to dd.MM.yyyy
	 */
	public void setDefaultPattern(String defaultPattern) {
		format = DateTimeFormat.getFormat(defaultPattern);
	}
	
	@Override
	public DateTimeFormat getFormat() {
		return format;
	}
	
	@Override
	public Date parse(CharSequence text) throws ParseException {
		try {
			return codec.decode(text.toString());
		} catch (CodecException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public String render(Date value) {
		try {
			return codec.encode(value);
		} catch (CodecException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
