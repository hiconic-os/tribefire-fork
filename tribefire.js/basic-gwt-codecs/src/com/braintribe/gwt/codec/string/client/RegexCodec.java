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
import com.braintribe.gwt.ioc.client.Configurable;

public class RegexCodec<T> implements Codec<T, String> {
	
	private Codec<T,String> valueCodec;
	private String pattern;
	
	@Configurable
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	@Configurable
	public void setValueCodec(Codec<T,String> valueCodec) {
		this.valueCodec = valueCodec;
	}

	@Override
	public T decode(String encodedValue) throws CodecException {
		if (validate(encodedValue))
			return valueCodec.decode(encodedValue);
		else
			throw new CodecException("Failed encoding string value: '" + encodedValue+"'. It doesn't match the given pattern: '" + pattern + "'.");
	}

	@Override
	public String encode(T value) throws CodecException {
		return valueCodec.encode(value);
	}
	
	public boolean validate(String validationValue) {
		return validationValue.matches(pattern);
	}

	@Override
	public Class<T> getValueClass() {
		return valueCodec.getValueClass();
	}
	
}
