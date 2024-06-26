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

import java.util.HashSet;
import java.util.Set;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class StringSetJsonCodec implements Codec<Set<String>, JSONArray> {
	@Override
	public Set<String> decode(JSONArray encodedValue) throws CodecException {
		Set<String> strings = new HashSet<String>();
		for (int i = 0; i < encodedValue.size(); i++) {
			JSONValue value = encodedValue.get(i);
			JSONString string = value.isString();
			if (string != null) {
				strings.add(string.stringValue());
			}
		}
		return strings;
	}
	
	@Override
	public JSONArray encode(Set<String> value) throws CodecException {
		JSONArray array = new JSONArray();
		int index = 0;
		for (String string: value) {
			array.set(index++, new JSONString(string));
		}
		return array;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<Set<String>> getValueClass() {
		return (Class) Set.class;
	}

}
