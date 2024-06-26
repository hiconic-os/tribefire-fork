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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONValue;

public class LongJsonCodec implements Codec<Long, JSONValue> {
	@Override
	public JSONValue encode(Long value) throws CodecException {
		if (value == null) return JSONNull.getInstance();
		else return new JSONNumber(value);
	}
	
	@Override
	public Long decode(JSONValue jsonValue) throws CodecException {
		if (jsonValue == null || jsonValue.isNull() != null) return null;
		else {
			JSONNumber integerValue = jsonValue.isNumber();
			if (integerValue == null) 
				throw new CodecException("illegal JSON type " + jsonValue);
			
			return (long)integerValue.doubleValue();
		}
	}
	
	@Override
	public Class<Long> getValueClass() {
		return Long.class;
	}
}

