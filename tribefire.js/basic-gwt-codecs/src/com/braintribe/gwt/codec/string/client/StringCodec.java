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

/**
 * This simple string codec just returns the string when encoding
 * or decoding.
 * 
 * @author Dirk
 *
 */
public class StringCodec implements Codec<String, String> {
	@Override
	public String decode(String encodedValue) throws CodecException {
		return encodedValue;
	}
	
	@Override
	public String encode(String value) throws CodecException {
		if (value == null) return "";
		else return value;
	}
	
	@Override
	public Class<String> getValueClass() {
		return String.class;
	}
}
