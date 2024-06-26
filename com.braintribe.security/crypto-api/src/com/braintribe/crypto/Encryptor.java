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

import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * <p>
 * {@link com.braintribe.crypto.Cryptor} for encrypting data.
 * 
 */
public interface Encryptor extends Cryptor {

	/**
	 * <p>
	 * Returns a {@link Encryptor.Processor} for encrypting the given input.
	 * 
	 * <p>
	 * <b>Examples</b>
	 * 
	 * <p>
	 * <i>From bytes to bytes:</i>
	 * 
	 * <pre>
	 * {@code
	 * Encryptor encryptor = ...
	 * byte[] data = ...
	 * byte[] encryptedData = encryptor.encrypt(data).result().asBytes();
	 * }
	 * </pre>
	 * 
	 * <p>
	 * <i>From bytes to String:</i>
	 * 
	 * <pre>
	 * {@code
	 * Encryptor encryptor = ...
	 * byte[] data = ...
	 * String encryptedData = encryptor.encrypt(data).result().asString();
	 * }
	 * </pre>
	 * 
	 * @param input
	 *            The input to be processed by the returned {@link Encryptor.Processor}.
	 * @return The {@link Encryptor.Processor} for encrypting the given input.
	 * @throws CryptorException
	 *             If the creation of a {@link Encryptor.Processor} for encrypting the given input fails.
	 */
	Encryptor.Processor encrypt(byte[] input) throws CryptorException;

	/**
	 * <p>
	 * Returns a {@link Encryptor.StringProcessor} for encrypting the given String input.
	 * 
	 * <p>
	 * <b>Examples</b>
	 * 
	 * <p>
	 * <i>From String to bytes:</i>
	 * 
	 * <pre>
	 * {@code
	 * Encryptor encryptor = ...
	 * String data = ...
	 * byte[] encryptedData = encryptor.encrypt(data).result().asBytes();
	 * }
	 * </pre>
	 * 
	 * <p>
	 * <i>From String to String:</i>
	 * 
	 * <pre>
	 * {@code
	 * Encryptor encryptor = ...
	 * String data = ...
	 * String encryptedData = encryptor.encrypt(data).result().asString();
	 * }
	 * </pre>
	 * 
	 * @param input
	 *            The input to be processed by the returned {@link Encryptor.StringProcessor}.
	 * @return The {@link Encryptor.StringProcessor} for encrypting the given input.
	 * @throws CryptorException
	 *             If the creation of a {@link Encryptor.StringProcessor} for encrypting the given input fails.
	 */
	Encryptor.StringProcessor encrypt(String input) throws CryptorException;

	/**
	 * <p>
	 * Wraps a {@link OutputStream} for encrypting data.
	 * 
	 * @param outputStream
	 *            The {@code OutputStream} to be wrapped for encrypting data
	 * @return A {@code OutputStream} wrapped for encrypting data from the given {@code OutputStream}
	 * @throws CryptorException
	 *             If the wrapping of the given {@code OutputStream} for decrypting fails.
	 */
	OutputStream wrap(OutputStream outputStream) throws CryptorException;

	/**
	 * <p>
	 * Determines whether this {@code Encryptor} produces deterministic results.
	 * 
	 * @return Whether this {@code Encryptor} produces deterministic results.
	 */
	boolean isDeterministic();

	/**
	 * <p>
	 * A {@link com.braintribe.crypto.Cryptor.Processor} 
	 * for {@link com.braintribe.crypto.Encryptor}(s).
	 * 
	 */
	interface Processor extends Cryptor.Processor<Encryptor.Response> {

		@Override
		Encryptor.Response result() throws CryptorException;

		Encryptor.Processor withSaltFrom(byte[] encryptedValue) throws CryptorException;

	}

	/**
	 * <p>
	 * A String-handling {@link com.braintribe.crypto.Cryptor.Processor} 
	 * for {@link com.braintribe.crypto.Encryptor}(s).
	 * 
	 */
	interface StringProcessor extends Processor {

		Encryptor.StringProcessor charset(String charsetName) throws CryptorException;

		Encryptor.StringProcessor charset(Charset charset) throws CryptorException;

	}

	/**
	 * <p>
	 * A {@link com.braintribe.crypto.Cryptor.Response} with specialized methods 
	 * for obtaining data encrypted by a {@link com.braintribe.crypto.Encryptor}.
	 * 
	 */
	interface Response extends Cryptor.Response {

		String asString(Cryptor.Encoding encoding) throws CryptorException;

	}

}
