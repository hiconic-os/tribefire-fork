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
package com.braintribe.tomcat.extension.realm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import org.apache.catalina.CredentialHandler;

public class CryptorCredentialHandler implements CredentialHandler {

	private int saltLength = 16;

	public CryptorCredentialHandler(int saltLength) {
		this.saltLength = saltLength;
	}

	@Override
	public boolean matches(String inputCredentials, String storedCredentials) {

		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Could not access a SHA-56 hasher expert.", e);
		}

		byte[] storedCredentialBytes = Base64.getDecoder().decode(storedCredentials);
		byte[] salt = extractSalt(storedCredentialBytes);

		if (salt != null) {
			digest.update(salt);
		}

		digest.update(inputCredentials.getBytes(StandardCharsets.UTF_8));

		byte[] output = digest.digest();

		// salt is prepended to the digested result only if this hasher works with random salts
		if (salt != null && saltLength > 0) {
			output = concat(salt, output);
		}

		return Arrays.equals(storedCredentialBytes, output);
	}

	private byte[] extractSalt(byte[] encryptedValue) {

		if (saltLength < 1) {
			return null; // Shouldn't return null?
		}

		byte[] salt = Arrays.copyOf(encryptedValue, saltLength);

		return salt;
	}

	private static byte[] concat(byte[] first, byte[] second) {
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
	@Override
	public String mutate(String inputCredentials) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Could not access a SHA-56 hasher expert.", e);
		}
		byte[] hash = digest.digest(inputCredentials.getBytes(StandardCharsets.UTF_8));
		String encodedInput = Base64.getEncoder().encodeToString(hash);
		return encodedInput;
	}

}
