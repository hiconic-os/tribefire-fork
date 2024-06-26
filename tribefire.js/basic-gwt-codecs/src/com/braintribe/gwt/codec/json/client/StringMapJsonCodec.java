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

import java.util.HashMap;
import java.util.Map;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class StringMapJsonCodec<T> implements Codec<Map<String, T>, JSONValue> {
	private Codec<T, JSONValue> elementCodec;
	
	public StringMapJsonCodec(Codec<T, JSONValue> elementCodec) {
		this.elementCodec = elementCodec;
	}
	
	@Override
	public Map<String, T> decode(JSONValue encodedValue) throws CodecException {
		JSONObject jsonObject = (JSONObject)encodedValue;
		Map<String, T> map = new HashMap<String, T>();
		
		for (String key: jsonObject.keySet()) {
			JSONValue encodedElement = jsonObject.get(key);
			T value = elementCodec.decode(encodedElement);
			map.put(key, value);
		}
		
		return map;
	}
	
	@Override
	public JSONValue encode(Map<String, T> value) throws CodecException {
		if (value == null) return JSONNull.getInstance();
		
		JSONObject jsonObject = new JSONObject();
		
		for (Map.Entry<String, T> entry: value.entrySet()) {
			T mapValue = entry.getValue();
			JSONValue jsonValue = elementCodec.encode(mapValue);
			jsonObject.put(entry.getKey(), jsonValue);
		}
		
		return jsonObject;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<Map<String,T>> getValueClass() {
		return (Class) Map.class;
	}

}
