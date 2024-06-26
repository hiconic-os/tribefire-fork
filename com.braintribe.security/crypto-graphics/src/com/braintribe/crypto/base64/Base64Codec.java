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
package com.braintribe.crypto.base64;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class Base64Codec implements Codec<byte[], String> {
	
	private int options = Base64.DONT_BREAK_LINES;

	@Override
	public String encode(byte[] value) throws CodecException {
		try {
			return Base64.encodeBytes(value, options);
		} catch (Exception e) {
			throw new CodecException("Failed to base64 encode: "+e.getMessage(), e);
		}
	}

	@Override
	public byte[] decode(String encodedValue) throws CodecException {
		try {
			return Base64.decode(encodedValue, options);
		} catch (Exception e) {
			throw new CodecException("Failed to base64 decode: "+e.getMessage(), e);
		}
	}

	@Override
	public Class<byte[]> getValueClass() {
		return byte[].class;
	}

}
