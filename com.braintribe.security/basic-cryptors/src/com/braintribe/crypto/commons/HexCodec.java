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
package com.braintribe.crypto.commons;

import java.util.Arrays;

import com.braintribe.codec.Codec;

/**
 * <p>
 * An hexadecimal {@link Codec} which supports Strings with byte separator characters.
 * 
 */
public class HexCodec implements Codec<byte[], String> {

	final protected static char[] hexArray = "0123456789abcdef".toCharArray();

	private char separator = '\u0000';

	public HexCodec() {
	}

	public HexCodec(char separator) {
		if (Arrays.binarySearch(hexArray, separator) >= 0) {
			throw new IllegalArgumentException(String.valueOf(separator) + " cannot be used as a hexadecimal separator");
		}
		this.separator = separator;
	}

	@Override
	public String encode(byte[] b) {
		if (separator != '\u0000') {
			return toHex(b, separator);
		} else {
			return toHex(b);
		}
	}

	@Override
	public byte[] decode(String s) {
		if (separator != '\u0000') {
			return toByteArray(s, separator);
		} else {
			return toByteArray(s);
		}
	}

	protected static String toHex(byte[] s, char separator) {
		char[] f = new char[s.length * 3 - 1];
		for (int j = 0; j < s.length; j++) {
			int v = s[j] & 0xFF;
			f[j * 3] = hexArray[v >>> 4];
			f[j * 3 + 1] = hexArray[v & 0x0F];
			if (j != s.length - 1) {
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

	protected static byte[] toByteArray(String s) {
		int l = s.length();
		byte[] data = new byte[l / 2];
		for (int i = 0; i < l; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	protected static byte[] toByteArray(String s, @SuppressWarnings("unused") char separator) {
		int l = s.length();
		byte[] data = new byte[l / 3 + 1];
		for (int i = 0, t = 0; i < l; i += 3, t += 2) {
			data[t / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	@Override
	public Class<byte[]> getValueClass() {
		return byte[].class;
	}

}
