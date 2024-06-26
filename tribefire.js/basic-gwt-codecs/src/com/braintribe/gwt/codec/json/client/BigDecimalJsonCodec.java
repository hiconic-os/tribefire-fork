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
package com.braintribe.gwt.codec.json.client;

import java.math.BigDecimal;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * A codec for {@link BigDecimal}s.
 * 
 * @author michael.lafite
 */
public class BigDecimalJsonCodec implements Codec<BigDecimal, JSONValue> {

	@Override
	public BigDecimal decode(JSONValue jsonValue) throws CodecException {
		if (jsonValue == null || jsonValue instanceof JSONNull)
			return null;
		
		JSONString jsonString = jsonValue.isString();
		
		if (jsonString == null)
			throw new CodecException("found wrong json type when decoding decimal type: " + jsonValue.getClass());
		
		String valueAsString = jsonString.stringValue();
		String valueAsTrimmedString = valueAsString.trim();

		if (valueAsTrimmedString.length() > 0) {
			return new BigDecimal(valueAsTrimmedString);
		}
		return null;
	}

	@Override
	public JSONValue encode(BigDecimal obj) throws CodecException {
		if (obj != null) {
			return new JSONString(obj.toString());
		}
		else {
			return JSONNull.getInstance();
		}
	}

	@Override
	public Class<BigDecimal> getValueClass() {
		return BigDecimal.class;
	}
}
