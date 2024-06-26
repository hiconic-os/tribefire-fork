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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * The JsonJavaCodec transforms a JSON structure into a normal Java objects.
 * The mappings are as follows.
 * 
 * JSONNull : null
 * JSONString: java.lang.String
 * JSONBoolean: java.lang.Boolean
 * JSONNumber: java.lang.Double
 * JSONObject: java.util.Map<String, Object>
 * JSONArray: java.util.List<Object>
 * 
 * @author dirk.scheffler
 *
 */
public class JsonJavaCodec implements Codec<Object, JSONValue> {
	@Override
	public Object decode(JSONValue encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue instanceof JSONNull) return null;
		else if (encodedValue instanceof JSONString) return encodedValue.isString().stringValue();
		else if (encodedValue instanceof JSONBoolean) return encodedValue.isBoolean().booleanValue();
		else if (encodedValue instanceof JSONNumber) return encodedValue.isNumber().doubleValue();
		else if (encodedValue instanceof JSONObject) {
			JSONObject jsonObject = encodedValue.isObject();
			Map<String, Object> map = new HashMap<String, Object>();
			for (String name: jsonObject.keySet()) {
				Object value = decode(jsonObject.get(name));
				map.put(name, value);
			}
			return map;
		}
		else if (encodedValue instanceof JSONArray) {
			JSONArray jsonArray = encodedValue.isArray();
			List<Object> list = new ArrayList<Object>(jsonArray.size());
			for (int i = 0; i < jsonArray.size(); i++) {
				Object value = decode(jsonArray.get(i));
				list.add(value);
			}
			return list;
		}
		else throw new CodecException("found unsupported JSON value type " + encodedValue.getClass());
	}
	
	@Override
	public JSONValue encode(Object value) throws CodecException {
		if (value == null) return JSONNull.getInstance();
		else if (value instanceof Boolean) {
			return JSONBoolean.getInstance((Boolean)value);
		}
		else if (value instanceof String) {
			return new JSONString((String)value);
		}
		else if (value instanceof Number) {
			return new JSONNumber(((Number)value).doubleValue());
		}
		else if (value instanceof Collection) {
			Collection<?> collection = (Collection<?>)value;
			JSONArray jsonArray = new JSONArray();
			int index = 0;
			for (Object object: collection) {
				jsonArray.set(index++, encode(object));
			}
			return jsonArray;
		}
		else if (value instanceof Map) {
			Map<String, Object> map = (Map<String, Object>)value;
			JSONObject jsonObject = new JSONObject();
			for (Map.Entry<String, Object> entry: map.entrySet()) {
				String name = entry.getKey();
				JSONValue jsonValue = encode(entry.getValue());
				jsonObject.put(name, jsonValue);
			}
			return jsonObject;
		}
		else throw new CodecException("found unsupported Java value type " + value.getClass());
	}
	
	@Override
	public Class<Object> getValueClass() {
		return Object.class;
	}
}
