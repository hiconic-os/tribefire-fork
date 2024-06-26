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
package com.braintribe.codec.string;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class EnumCodec<T extends Enum<T>> implements Codec<T, String> {
	private Class<T> enumClass;

	@Configurable @Required
	public void setEnumClass(Class<T> enumClass) {
		this.enumClass = enumClass;
	}
	
	@Override
	public String encode(T value) throws CodecException {
		if (value == null) return "";
		else return value.toString();
	}
	
	@Override
	public T decode(String encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue.length() == 0)
			return null;
		
		try {
			T enumValue = Enum.valueOf(enumClass, encodedValue);
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
