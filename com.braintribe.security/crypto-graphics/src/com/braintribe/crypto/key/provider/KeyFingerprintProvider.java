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

import com.braintribe.crypto.hash.HashProvider;


/**
 * <p>A {@link Supplier}/{@link Function} which generates fingerprints for {@link Key}(s).
 * 
 * <p>{@link #get()} generates a fingerprint for the key previously configured through {@link #setKey(Key)}.
 * 
 * <p>{@link #apply(Key)} generates a fingerprint for the key passed as the method argument.
 * 
 *
 */
public class KeyFingerprintProvider implements Supplier<String>, Function<Key, String> {
	
	private Function<byte[], String> hashProvider = new HashProvider();
	private byte[] keyValue;
	
	public void setHashProvider(Function<byte[], String> hashProvider) {
		Objects.requireNonNull(hashProvider, "hashProvider cannot be set to null");
		this.hashProvider = hashProvider;
	}
	
	public void setKey(Key key) {
		Objects.requireNonNull(key, "key cannot be set to null");
		this.keyValue = key.getEncoded();
	}
	
	@Override
	public String get() throws RuntimeException {
		
		if (keyValue == null) {
			throw new RuntimeException("No key is configured.");
		}
		
		return hashProvider.apply(keyValue);
	}

	@Override
	public String apply(Key key) throws RuntimeException {

		if (key == null) {
			throw new RuntimeException("Key cannot be null.");
		}
		
		return hashProvider.apply(key.getEncoded());
	}
	
}
