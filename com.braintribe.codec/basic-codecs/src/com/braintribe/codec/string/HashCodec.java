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

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

public class HashCodec implements Codec<String,String> {

	protected String algorithm = "SHA-1";
	
	public HashCodec(String algorithm) {
		if (algorithm != null && !algorithm.isEmpty()) {
			this.algorithm = algorithm;
		}
	}
	public HashCodec() {
	}
	
	@Override
	public String encode(String value) throws CodecException {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance(this.algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new CodecException("Could not get the "+this.algorithm+" message digest.", e);
		}
		messageDigest.reset();
		messageDigest.update(value.getBytes(Charset.forName("UTF8")));
		final byte[] resultByte = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : resultByte) {
			sb.append(String.format("%02x", b & 0xff));
		}
		return sb.toString();
	}

	@Override
	public String decode(String encodedValue) throws CodecException {
		throw new CodecException("Cannot decode an MD5 hashsum.");
	}

	@Override
	public Class<String> getValueClass() {
		return String.class;
	}

}
