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
package com.braintribe.crypto.key.provider;

import java.security.Key;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;


/**
 * <p>A {@link Supplier}/{@link Function} which generates fingerprints for {@link Key}(s), based on the given base64 string representation of their encoded value.
 * 
 * <p>{@link Supplier#get()} generates a fingerprint for the key represented by the base64 string configured through {@link #setKeyString(String)}.
 * 
 * <p>{@link Function#apply(Object)} generates a fingerprint for the key represented by the base64 string passed as the method argument.
 * 
 *
 */
public class KeyStringFingerprintProvider implements Supplier<String>, Function<String, String> {
	
	private Function<byte[], String> hashProvider;
	private Codec<byte[], String> base64Codec;
	private String defaultKeyString;
	
	public void setHashProvider(Function<byte[], String> hashProvider) {
		Objects.requireNonNull(hashProvider, "hashProvider cannot be set to null");
		this.hashProvider = hashProvider;
	}

	public void setBase64Codec(Codec<byte[], String> base64Codec) {
		Objects.requireNonNull(base64Codec, "base64Codec cannot be set to null");
		this.base64Codec = base64Codec;
	}

	public void setKeyString(String keyString) {
		this.defaultKeyString = keyString;
	}

	public String getKeyString() {
		return this.defaultKeyString;
	}

	@Override
	public String apply(String keyString) throws RuntimeException {

		if (keyString == null) {
			throw new RuntimeException("No key string was provider");
		}
		if (hashProvider == null) {
			throw new RuntimeException("No hash provider was configured");
		}
		
		byte[] keyBytes;
		try {
			keyBytes = base64Codec.decode(keyString);
		} catch (CodecException e) {
			throw new RuntimeException("Failed to decode base64 input: "+e.getMessage(), e);
		}
		
		return hashProvider.apply(keyBytes);
	}

	@Override
	public String get() throws RuntimeException {
		return apply(defaultKeyString);
	}
	
}
