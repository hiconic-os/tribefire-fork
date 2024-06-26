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
package com.braintribe.codec.string;

import java.util.Date;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class LongDateCodec implements Codec<Date, String> {
	@Override
	public String encode(Date value) throws CodecException {
		return String.valueOf(value.getTime());
	}
	
	@Override
	public Date decode(String encodedValue) throws CodecException {
		try {
			Date date = new Date(Long.parseLong(encodedValue));
			return date;
		} catch (NumberFormatException e) {
			throw new CodecException("error while parsing long", e);
		}
	}
	
	@Override
	public Class<Date> getValueClass() {
		return Date.class;
	}
}
