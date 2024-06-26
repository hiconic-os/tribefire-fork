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
package com.braintribe.model.processing.library.service.util;

import com.braintribe.crypto.key.SymmetricKeyGenerator;
import com.braintribe.crypto.key.codec.CryptoStringCodec;

public class AuthenticationUtils {

	private static CryptoStringCodec codec = null;

	// Used SymmetricKeyGenerator of CryptoUtils to generate the key 
	// SymmetricKeyGenerator skg = new SymmetricKeyGenerator();
	// String newKey = skg.getKeyAsString();

	private final static String KEY = "GYxMWEPBkmvWnfGFax8CxLBwpOP4m/HL";

	// Used CryptoStringCodec to create this password
	// CryptoStringCodec codec = new CryptoStringCodec();
	// codec.setCipher("DESede/ECB/PKCS5Padding");
	// codec.setEncoding("UTF-8");
	// codec.setKey(SymmetricKeyGenerator.importKeyFromString(newKey));
	// String encodedPassword = codec.encode("...originalPassword...");

	private static void initialize() throws Exception {
		if (codec != null) {
			return;
		}
		codec = new CryptoStringCodec();
		codec.setCipher("DESede/ECB/PKCS5Padding");
		codec.setEncoding("UTF-8");
		codec.setKey(SymmetricKeyGenerator.importKeyFromString(KEY));
	}

	public static String decrypt(String encryptedText) throws Exception {
		initialize();
		return codec.decode(encryptedText);
	}

}
