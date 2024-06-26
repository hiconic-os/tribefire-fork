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
package com.braintribe.crypto.cipher;

import java.io.InputStream;
import java.io.OutputStream;

import com.braintribe.crypto.BidiCryptor;
import com.braintribe.crypto.Cryptor;
import com.braintribe.crypto.CryptorException;
import com.braintribe.crypto.Decryptor;
import com.braintribe.crypto.Encryptor;

/**
 * <p>
 * A {@link BidiCryptor} based on {@link javax.crypto.Cipher}(s).
 * 
 */
public class BidiCipherCryptor implements CipherCryptor, BidiCryptor {

	private Encryptor encryptor;
	private Decryptor decryptor;

	public BidiCipherCryptor(Encryptor encryptor, Decryptor decryptor) {
		this.encryptor = encryptor;
		this.decryptor = decryptor;
	}

	@Override
	public Encryptor forEncrypting() {
		return encryptor;
	}

	@Override
	public Decryptor forDecrypting() {
		return decryptor;
	}

	@Override
	public Encryptor.Processor encrypt(byte[] input) throws CryptorException {
		return encryptor.encrypt(input);
	}

	@Override
	public Encryptor.StringProcessor encrypt(String input) throws CryptorException {
		return encryptor.encrypt(input);
	}

	@Override
	public OutputStream wrap(OutputStream outputStream) throws CryptorException {
		return encryptor.wrap(outputStream);
	}

	@Override
	public Cryptor.Matcher is(byte[] input) throws CryptorException {
		try {
			return encryptor.is(input);
		} catch (UnsupportedOperationException e) {
			return decryptor.is(input);
		}
	}

	@Override
	public Cryptor.StringMatcher is(String input) throws CryptorException {
		try {
			return encryptor.is(input);
		} catch (UnsupportedOperationException e) {
			return decryptor.is(input);
		}
	}

	@Override
	public boolean isDeterministic() {
		return encryptor.isDeterministic();
	}

	@Override
	public Decryptor.Processor decrypt(byte[] input) throws CryptorException {
		return decryptor.decrypt(input);
	}

	@Override
	public Decryptor.StringProcessor decrypt(String input) throws CryptorException {
		return decryptor.decrypt(input);
	}

	@Override
	public InputStream wrap(InputStream inputStream) throws CryptorException {
		return decryptor.wrap(inputStream);
	}

}
