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
package com.braintribe.model.access.smood.distributed.codec;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.utils.Base64;
import com.braintribe.utils.IOTools;

public class DefaultJavaClassCodec implements Codec<InputStream,String>{

	@Override
	public String encode(InputStream inputStream) throws CodecException {
		byte[] bytes = null;
		try {
			bytes = IOTools.slurpBytes(inputStream);
		} catch (Exception e) {
			throw new CodecException("Could not read from InputStream.", e);
		}
		String encodedString = null;
		try {
			encodedString = Base64.encodeBytes(bytes);
		} catch(Exception e) {
			throw new CodecException("Could not Base64 encode bytes.", e);
		}
		return encodedString;
	}

	@Override
	public InputStream decode(String encodedValue) throws CodecException {
		if (encodedValue == null) {
			throw new CodecException("The encoded value is null.");
		}
		byte[] bytes = null;
		try { 
			bytes = Base64.decode(encodedValue);
		} catch(Exception e) {
			throw new CodecException("Could not decode encoded value", e);
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		return bais;
	}

	@Override
	public Class<InputStream> getValueClass() {
		return InputStream.class;
	}

}
