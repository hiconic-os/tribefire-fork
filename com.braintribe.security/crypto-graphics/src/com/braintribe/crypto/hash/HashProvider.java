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

import java.security.MessageDigest;
import java.util.function.Function;



/**
 * <p>Provides a hash computation (hexadecimal representation) for a given byte array.
 * 
 * <p>By default, the digest algorithm used is SHA-256.
 * 
 * <p>The digest algorithm to be used may be changed through {@link #setDigestAlgorithm(String)}.
 * 
 *
 */
public class HashProvider implements Function<byte[], String> {
	
	final protected static char[] hexArray = "0123456789abcdef".toCharArray();
	
	private String digestAlgorithm = "SHA-256";
	private char separator = ':';

	/**
	 * <p>Sets the digest algorithm used to compute the hash of the inputs.
	 * 
	 * <p>By default, {@code SHA-256} will be used. 
	 * 
	 * @param digestAlgorithm The digest algorithm used to compute the hash of the inputs
	 */
	public void setDigestAlgorithm(String digestAlgorithm) {
		this.digestAlgorithm = digestAlgorithm;
	}

	/**
	 * <p>Gets the digest algorithm used to compute the hash of the inputs.
	 * 
	 * @return The digest algorithm used to compute the hash of the inputs.
	 */
	public String getDigestAlgorithm() {
		return this.digestAlgorithm;
	}

	/**
	 * <p>Defines the character used to separate the bytes in the resulting hexadecimal representation String.
	 * 
	 * <p>By default, {@code :} will be used. 
	 * 
	 * <p>Set to {@code \u0000} to use no separator.
	 * 
	 * @param separator The character used to separate the bytes in the resulting hexadecimal representation String
	 */
	public void setSeparator(char separator) {
		this.separator = separator;
	}
	
	@Override
	public String apply(byte[] value) throws RuntimeException {
		return generate(value);
	}
	
	protected String generate(byte[] source) throws RuntimeException {
		
		if (digestAlgorithm != null) {
			
			MessageDigest digest = null;
			try {
				digest = MessageDigest.getInstance(digestAlgorithm);
			} catch (Exception e) {
				throw new RuntimeException("Failed to obtain a MessageDigest"+(e.getMessage() != null ? ": "+e.getMessage() : ""), e);
			}
			
			digest.reset();
			digest.update(source);
			source = digest.digest();
		}
		
		if (separator != '\u0000') {
			return toHex(source, separator);
		} else {
			return toHex(source);
		}
	}
	
	protected static String toHex(byte[] s, char separator) {
		char[] f = new char[s.length * 3 - 1];
		for (int j = 0; j < s.length; j++) {
			int v = s[j] & 0xFF;
			f[j * 3] = hexArray[v >>> 4];
			f[j * 3 + 1] = hexArray[v & 0x0F];
			if (j != s.length-1) {
				f[j * 3 + 2] = separator;
			}
		}
		return new String(f);
	}
	
	protected static String toHex(byte[] s) {
		char[] f = new char[s.length * 2];
	    for (int j = 0; j < s.length; j++) {
	        int v = s[j] & 0xFF;
	        f[j * 2] = hexArray[v >>> 4];
	        f[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(f);
	}
	
}
