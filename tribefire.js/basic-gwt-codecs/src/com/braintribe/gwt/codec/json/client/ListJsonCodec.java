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
import java.util.List;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONValue;

public class ListJsonCodec<T> implements Codec<List<T>, JSONValue> {
	private Codec<T, JSONValue> elementCodec;
	
	public ListJsonCodec(Codec<T, JSONValue> elementCodec) {
		this.elementCodec = elementCodec;
	}
	
	@Override
	public List<T> decode(JSONValue encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue.isNull() != null)
			return null;

		List<T> list = new ArrayList<T>();
		if (encodedValue instanceof JSONArray) {
			JSONArray array = (JSONArray)encodedValue;
			
			for (int i = 0; i < array.size(); i++) {
				JSONValue encodedElement = array.get(i);
				list.add(elementCodec.decode(encodedElement));
			}
		}
		
		return list;
	}
	
	@Override
	public JSONValue encode(List<T> value) throws CodecException {
		if (value == null) return JSONNull.getInstance();
		
		JSONArray array = new JSONArray();
		
		int i = 0;
		for (T element: value) {
			array.set(i++, elementCodec.encode(element));
		}
		
		return array;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<List<T>> getValueClass() {
		return (Class) List.class;
	}

}
