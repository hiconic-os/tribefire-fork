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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.utils.Base64;
import com.braintribe.utils.lcd.StringTools;

public class GzipBase64Codec implements Codec<String,String> {

	protected GzipCodec gzipCodec = new GzipCodec();

	@Override
	public String encode(String value) throws CodecException {
		if (value == null) {
			return null;
		}

		byte[] valueBytes = null;
		try {
			valueBytes = value.getBytes("UTF-8");
		} catch(Exception e) {
			throw new CodecException("Could not get the bytes of String "+StringTools.getFirstNCharacters(value, 100), e);
		}

		byte[] zipBytes = this.gzipCodec.encode(valueBytes);

		String base64Encoded = Base64.encodeBytes(zipBytes);

		return base64Encoded;
	}

	@Override
	public String decode(String encodedValue) throws CodecException {
		if (encodedValue == null) {
			return null;
		}

		try {
			byte[] encodedBytes = encodedValue.getBytes("UTF-8");
			byte[] decodedBytes = Base64.decode(encodedBytes, 0, encodedBytes.length);

			byte[] unzippedBytes = this.gzipCodec.decode(decodedBytes);

			String decodedString = new String(unzippedBytes, "UTF-8");

			return decodedString;
		} catch(Exception e) {
			throw new CodecException("Could not decode "+StringTools.getFirstNCharacters(encodedValue, 100), e);
		}
	}

	@Override
	public Class<String> getValueClass() {
		return String.class;
	}

}
