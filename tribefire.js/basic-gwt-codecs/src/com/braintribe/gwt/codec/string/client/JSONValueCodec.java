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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class JSONValueCodec implements Codec<JSONValue, String> {
	@Override
	public JSONValue decode(String encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue.length() == 0)
			return null;
		else 
			return JSONParser.parseLenient(encodedValue);
	}
	@Override
	public String encode(JSONValue value) throws CodecException {
		if (value == null || value.isNull() != null)
			return "";
		else
			return value.toString();
	}
	
	@Override
	public Class<JSONValue> getValueClass() {
		return JSONValue.class;
	}

}
