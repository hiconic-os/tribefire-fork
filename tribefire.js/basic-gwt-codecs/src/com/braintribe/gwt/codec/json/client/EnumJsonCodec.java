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
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class EnumJsonCodec<T extends Enum<T>> implements Codec<T, JSONValue> {
	private Class<T> enumClass;

	@Configurable @Required
	public void setEnumClass(Class<T> enumClass) {
		this.enumClass = enumClass;
	}
	
	@Override
	public JSONValue encode(T value) throws CodecException {
		if (value == null) return JSONNull.getInstance();
		else return new JSONString(value.toString());
	}
	
	@Override
	public T decode(JSONValue jsonValue) throws CodecException {
		if (jsonValue == null || jsonValue.isNull() != null)
			return null;
		
		JSONString jsonString = jsonValue.isString();
		
		if (jsonString == null)
			throw new CodecException("invalid type. must be string");

		try {
			T enumValue = Enum.valueOf(enumClass, jsonString.stringValue());
			return enumValue;
		} catch (Exception e) {
			throw new CodecException("error while creating enum from string", e);
		}
	}
	
	@Override
	public Class<T> getValueClass() {
		return enumClass;
	}
}
