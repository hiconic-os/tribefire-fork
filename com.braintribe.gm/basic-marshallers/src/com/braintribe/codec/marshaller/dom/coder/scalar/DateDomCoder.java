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
package com.braintribe.codec.marshaller.dom.coder.scalar;

import java.util.Date;

import com.braintribe.codec.CodecException;
import com.braintribe.codec.marshaller.dom.DomDecodingContext;
import com.braintribe.codec.marshaller.dom.DomEncodingContext;
import com.braintribe.codec.marshaller.dom.coder.DomTextCoder;
import com.braintribe.codec.marshaller.stax.DateFormats;
import com.braintribe.utils.DateTools;

public class DateDomCoder extends DomTextCoder<Date> {
	
	public DateDomCoder() {
		super("T");
	}

	@Override
	protected Date decodeText(DomDecodingContext context, String text) throws CodecException {
		try {
			return DateTools.decode(text, DateFormats.dateFormat);
		} catch (Exception e) {
			throw new CodecException("error while parsing date", e);
		}
	}
	
	@Override
	protected String encodeText(DomEncodingContext context, Date value) throws CodecException {
		return DateTools.encode(value, DateFormats.dateFormat);
	}

}
