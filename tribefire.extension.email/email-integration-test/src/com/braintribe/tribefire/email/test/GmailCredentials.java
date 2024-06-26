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
package com.braintribe.tribefire.email.test;

import com.braintribe.model.processing.bootstrapping.TribefireRuntime;
import com.braintribe.utils.encryption.Cryptor;
import com.braintribe.utils.lcd.StringTools;

public class GmailCredentials {

	private static String user = "email.cartridge@gmail.com";

	public static String getEmail() {
		String plainValue = TribefireRuntime.getProperty("GMAIL_EMAIL");
		if (!StringTools.isBlank(plainValue)) {
			return plainValue;
		}
		return user;
	}

	public static String getPassword() {
		String plainValue = TribefireRuntime.getProperty("GMAIL_PASSWORD");
		if (!StringTools.isBlank(plainValue)) {
			return plainValue;
		}
		String encryptedValue = TribefireRuntime.getProperty("GMAIL_PASSWORD_ENCRYPTED");
		if (StringTools.isBlank(encryptedValue)) {
			throw new IllegalStateException("Neither the property GMAIL_PASSWORD nor GMAIL_PASSWORD_ENCRYPTED are set.");
		}

		return Cryptor.decrypt(TribefireRuntime.DEFAULT_TRIBEFIRE_DECRYPTION_SECRET, null, null, null, encryptedValue);
	}
}
