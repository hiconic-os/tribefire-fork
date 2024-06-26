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
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class JsonStringCodec<T> implements Codec<T, String> {
	
	private Codec<T, JSONValue> jsonCodec;
	
	public JsonStringCodec(Codec<T, JSONValue> jsonCodec) {
		this.jsonCodec = jsonCodec;
	}
	
	protected JsonStringCodec() {
		
	}
	
	protected void setJsonCodec(Codec<T, JSONValue> jsonCodec) {
		this.jsonCodec = jsonCodec;
	}
	
	@Override
	public T decode(String encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue.length() == 0)
			return null;
		
		JSONValue jsonValue = JSONParser.parseLenient(encodedValue);
		
		return jsonCodec.decode(jsonValue);
	}
	
	@Override
	public String encode(T value) throws CodecException {
		JSONValue jsonValue = jsonCodec.encode(value);
		return jsonValue.toString();
	}
	
	@Override
	public Class<T> getValueClass() {
		return jsonCodec.getValueClass();
	}
}
