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
package com.braintribe.crypto;

import java.nio.charset.Charset;

/**
 * <p>
 * A component capable of performing cryptographic operations.
 * 
 */
public interface Cryptor {

	enum Encoding {
		base64, hex
	}

	/**
	 * <p>
	 * Enables encryption operations on this {@code Cryptor}, if supported.
	 * 
	 * @return A {@code Encryptor} instance.
	 * @throws UnsupportedOperationException
	 *             If the {@code Cryptor} does not support encrypting.
	 */
	Encryptor forEncrypting();

	/**
	 * <p>
	 * Enables decryption operations on this {@code Cryptor}, if supported.
	 * 
	 * @return A {@code Decryptor} instance.
	 * @throws UnsupportedOperationException
	 *             If the {@code Cryptor} does not support decrypting.
	 */
	Decryptor forDecrypting();

	/**
	 * <p>
	 * Obtains a {@link Encryptor.Matcher} for matching the given input against encrypted values.
	 * 
	 * @param input
	 *            The input to be compared with the encrypted value.
	 * @return A {@link Cryptor.Matcher} for matching the given input against encrypted values.
	 * @throws CryptorException
	 *             If the creation of a {@link Cryptor.Matcher} for matching the given input fails.
	 * @throws UnsupportedOperationException
	 *             If the {@code Cryptor} does not support matching.
	 */
	Cryptor.Matcher is(byte[] input) throws CryptorException, UnsupportedOperationException;

	/**
	 * <p>
	 * Obtains a {@link Encryptor.Matcher} for matching the given input against encrypted values.
	 * 
	 * 
	 * @param input
	 *            The input to be compared with the encrypted value.
	 * @return A {@link Cryptor.Matcher} for matching the given input against encrypted values.
	 * @throws CryptorException
	 *             If the creation of a {@link Cryptor.Matcher} for matching the given input fails.
	 * @throws UnsupportedOperationException
	 *             If the {@code Cryptor} does not support matching.
	 */
	Cryptor.StringMatcher is(String input) throws CryptorException, UnsupportedOperationException;

	interface Matcher {

		boolean equals(byte[] encryptedValue) throws CryptorException;

		boolean equals(String encryptedValue) throws CryptorException;

		boolean equals(String encryptedValue, Cryptor.Encoding encoding) throws CryptorException;

	}

	interface StringMatcher extends Matcher {

		StringMatcher charset(String charsetName) throws CryptorException;

		StringMatcher charset(Charset charset) throws CryptorException;

	}

	interface Processor<T extends Response> {

		T result() throws CryptorException;

	}

	interface Response {

		String asString() throws CryptorException;

		byte[] asBytes() throws CryptorException;

	}

}
