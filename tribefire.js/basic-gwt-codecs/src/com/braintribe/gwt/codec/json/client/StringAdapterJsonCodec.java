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
import com.braintribe.gwt.ioc.client.Required;
import com.google.gwt.json.client.JSONNull;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

/**
 * This Codec uses an inner delegate codec for encoding/decoding from JSONValue to String
 * and then to a final T object. And vice versa.
 * @author michel.docouto
 */
public class StringAdapterJsonCodec<T> implements Codec<T, JSONValue> {
	
	private Codec<T, String> delegateCodec;
	
	/**
	 * Configures the required codec for decoding/encoding String to T, and vice versa.
	 */
	@Required
	public void setDelegateCodec(Codec<T, String> delegateCodec) {
		this.delegateCodec = delegateCodec;
	}
	
	@Override
	public JSONValue encode(T value) throws CodecException {
		if (value == null) return JSONNull.getInstance();
		else return new JSONString(delegateCodec.encode(value));
	}

	@Override
	public T decode(JSONValue jsonValue) throws CodecException {
		if (jsonValue == null || jsonValue.isNull() != null) return null;
		else {
			JSONString stringValue = jsonValue.isString();
			if (stringValue == null) 
				throw new CodecException("illegal JSON type " + jsonValue);
			
			return delegateCodec.decode(stringValue.stringValue());
		}
	}

	@Override
	public Class<T> getValueClass() {
		return delegateCodec.getValueClass();
	}

}
