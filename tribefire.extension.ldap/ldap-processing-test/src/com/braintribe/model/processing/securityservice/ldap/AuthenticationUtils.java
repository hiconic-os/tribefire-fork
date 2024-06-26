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
package com.braintribe.model.processing.securityservice.ldap;

import org.junit.Ignore;

import com.braintribe.crypto.key.SymmetricKeyGenerator;
import com.braintribe.crypto.key.codec.CryptoStringCodec;

@Ignore
public class AuthenticationUtils {

	private static CryptoStringCodec codec = null;

	private final static String FULLDN = "CN=Web Connector,OU=ServiceAccounts,OU=Accounts,OU=BTT,DC=Braintribe";
	private final static String LOGINNAME = "web.connect";

	// Used SymmetricKeyGenerator of CryptoUtils to generate the key 
	// SymmetricKeyGenerator skg = new SymmetricKeyGenerator();
	// String newKey = skg.getKeyAsString();

	private final static String KEY = "Ydqo+99XReP+hdDNl6IlNwS5ka5dYUWY";

	// Used CryptoStringCodec to create this password
	// CryptoStringCodec codec = new CryptoStringCodec();
	// codec.setCipher("DESede/ECB/PKCS5Padding");
	// codec.setEncoding("UTF-8");
	// codec.setKey(SymmetricKeyGenerator.importKeyFromString(newKey));
	// String encodedPassword = codec.encode("...originalPassword...");


	private final static String WEB_CONNECT_PASSWORD = "FCK8Inh/nhCdieuVkmnAXw==";

	private static void initialize() throws Exception {
		if (codec != null) {
			return;
		}
		codec = new CryptoStringCodec();
		codec.setCipher("DESede/ECB/PKCS5Padding");
		codec.setEncoding("UTF-8");
		codec.setKey(SymmetricKeyGenerator.importKeyFromString(KEY));
	}

	protected static String getWebConnectPassword() throws Exception {
		initialize();
		return codec.decode(WEB_CONNECT_PASSWORD);
	}

	protected static String getFullDn() {
		return FULLDN;
	}
	protected static String getLoginName() {
		return LOGINNAME;
	}
}
