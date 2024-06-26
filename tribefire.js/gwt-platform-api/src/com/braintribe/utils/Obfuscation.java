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
package com.braintribe.utils;

import java.nio.charset.StandardCharsets;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;

/**
 * This utility can be used to visually hide secret text like passwords, codes, etc... The obfuscation algorithm is not
 * meant to be secure but create results that protects secret text from casual viewing. <br />
 * This class contains static methods while acting as {@link Codec} at the same time.
 */
public class Obfuscation implements Codec<String, String> {

	public static final String OBFUSCATION_PREFIX = "OBF:";

	/**
	 * Returns true if the passed string starts with the default obfuscation prefix ({@link #OBFUSCATION_PREFIX}).
	 */
	public static boolean isObfuscated(String s) {
		return isObfuscated(s, OBFUSCATION_PREFIX);
	}

	/**
	 * Returns true if the passed string starts with the passed prefix. If a null string is passed this method returns
	 * false.
	 */
	public static boolean isObfuscated(String s, String obfuscationPrefix) {
		return s != null && s.startsWith(obfuscationPrefix);
	}

	/**
	 * Obfuscates passed string and adds passed the default prefix ( {@link #OBFUSCATION_PREFIX} at the beginning of the
	 * string.
	 */
	public static String obfuscate(String s) {
		return obfuscate(s, OBFUSCATION_PREFIX);
	}

	/**
	 * Obfuscates passed string and adds passed prefix at the beginning of the string. If a null prefix is passed the
	 * prefix will be ignored.
	 */
	public static String obfuscate(String s, String obfuscationPrefix) {
		StringBuilder buf = new StringBuilder();
		byte b[] = s.getBytes(StandardCharsets.UTF_8);
		if (obfuscationPrefix != null) {
			buf.append(obfuscationPrefix);
		}
		for (int i = 0; i < b.length; i++) {
			byte b1 = b[i];
			byte b2 = b[b.length - (i + 1)];
			if (b1 < 0 || b2 < 0) {
				int i0 = (255 & b1) * 256 + (255 & b2);
				String x = Integer.toString(i0, 36).toLowerCase();
				buf.append("U0000", 0, 5 - x.length());
				buf.append(x);
			} else {
				int i1 = 127 + b1 + b2;
				int i2 = (127 + b1) - b2;
				int i0 = i1 * 256 + i2;
				String x = Integer.toString(i0, 36).toLowerCase();
				buf.append("000", 0, 4 - x.length());
				buf.append(x);
			}
		}
		return buf.toString();
	}

	/**
	 * Deobfuscates the passed string. A may existing default obfuscation prefix ({@link #OBFUSCATION_PREFIX}) will be
	 * ignored.
	 */
	public static String deobfuscate(String s) {
		return deobfuscate(s, OBFUSCATION_PREFIX);
	}

	/**
	 * Deobfuscates the passed string. The passed obfuscation prefix will be ignored.
	 */
	public static String deobfuscate(String s, String obfuscationPrefix) {
		if (isObfuscated(s, obfuscationPrefix)) {
			s = s.substring(obfuscationPrefix.length());
		}
		byte b[] = new byte[s.length() / 2];
		int l = 0;
		for (int i = 0; i < s.length(); i += 4) {
			if (s.charAt(i) == 'U') {
				i++;
				String x = s.substring(i, i + 4);
				int i0 = Integer.parseInt(x, 36);
				byte bx = (byte) (i0 >> 8);
				b[l++] = bx;
			} else {
				String x = s.substring(i, i + 4);
				int i0 = Integer.parseInt(x, 36);
				int i1 = i0 / 256;
				int i2 = i0 % 256;
				byte bx = (byte) (((i1 + i2) - 254) / 2);
				b[l++] = bx;
			}
		}

		return new String(b, 0, l, StandardCharsets.UTF_8);
	}

	// **************************************
	// Methods to support the Codec interface
	// **************************************

	/**
	 * @see #obfuscate(String)
	 */
	@Override
	public String encode(String value) throws CodecException {
		return obfuscate(value);
	}

	/**
	 * @see #deobfuscate(String)
	 */
	@Override
	public String decode(String encodedValue) throws CodecException {
		return deobfuscate(encodedValue);
	}

	@Override
	public Class<String> getValueClass() {
		return String.class;
	}

	// **************************************
	// Simple Test method
	// **************************************

	public static void main(String[] args) {

		String[] plains = null;
		if (args == null || args.length == 0) {
			plains = new String[] { "operating", "access4U" };
		} else {
			plains = args;
		}

		for (String plain : plains) {

			String obfuscated = obfuscate(plain);
			System.out.println("Password: " + plain + " obfuscated to : " + obfuscated + " deobfuscated to : " + deobfuscate(obfuscated));
		}

	}

}
