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
package com.braintribe.crypto.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.braintribe.codec.Codec;
import com.braintribe.crypto.Cryptor.Encoding;
import com.braintribe.crypto.CryptorException;
import com.braintribe.crypto.commons.HexCodec;

/**
 * <p>
 * Provides a hash computation (hexadecimal representation) for a given byte array.
 * 
 * <p>
 * By default, the digest algorithm used is SHA-256.
 * 
 * <p>
 * The digest algorithm to be used may be configured through {@link #setDigestAlgorithm(String)}.
 * 
 *
 */
public class HashFunction implements Function<byte[], String> {

	private String digestAlgorithm = "SHA-256";
	private char separator = ':';
	private Hasher hasher;

	public void initialize() throws CryptorException {

		Map<Encoding, Codec<byte[], String>> codecs = new HashMap<>(1);
		codecs.put(Encoding.hex, new HexCodec(separator));

		hasher = new Hasher(digestAlgorithm, null, codecs, null);

	}

	/**
	 * <p>
	 * Sets the digest algorithm used to compute the hash of the inputs.
	 * 
	 * <p>
	 * By default, {@code SHA-256} will be used.
	 * 
	 * @param digestAlgorithm
	 *            The digest algorithm used to compute the hash of the inputs
	 */
	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	/**
	 * <p>
	 * Gets the digest algorithm used to compute the hash of the inputs.
	 * 
	 * @return The digest algorithm used to compute the hash of the inputs.
	 */
	public String getDigestAlgorithm() {
		return this.digestAlgorithm;
	}

	/**
	 * <p>
	 * Defines the character used to separate the bytes in the resulting hexadecimal representation String.
	 * 
	 * <p>
	 * By default, {@code :} will be used.
	 * 
	 * <p>
	 * Set to {@code \u0000} to use no separator.
	 * 
	 * @param separator
	 *            The character used to separate the bytes in the resulting hexadecimal representation String
	 */
	public void setSeparator(char separator) {
		this.separator = separator;
	}

	@Override
	public String apply(byte[] t) {
		try {
			if (hasher == null)
				initialize();
			return hasher.encrypt(t).result().asString(Encoding.hex);
		} catch (CryptorException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

}
