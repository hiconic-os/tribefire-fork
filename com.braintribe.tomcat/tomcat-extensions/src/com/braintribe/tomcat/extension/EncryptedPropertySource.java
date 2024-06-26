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
package com.braintribe.tomcat.extension;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.tomcat.util.IntrospectionUtils.PropertySource;

/**
 * This is a minimal tool for decrypting system property values. It allows to use encrypted values in server.xml or other files. <br>
 * The prefix <code>AES/CBC/PKCS5Padding:</code>indicates that the {@link #getProperty(String)} method should decode the value. If this prefix is not
 * present, the default action (get the actual system property) is performed.
 */
public class EncryptedPropertySource implements PropertySource {

	private static final Logger logger = Logger.getLogger(EncryptedPropertySource.class.getName());

	protected final static String PREFIX = "AES/CBC/PKCS5Padding";

	@Override
	public String getProperty(String key) {

		logger.fine("getProperty called for key " + key);

		if (key != null) {

			if (key.startsWith(PREFIX + ":")) {

				logger.fine("Key " + key + " seems to be an encrypted value.");
				try {
					String passwordEncodedAndEncryptedWithKeyLength = key.substring(PREFIX.length() + 1);

					String passwordEncodedAndEncrypted;
					int index = passwordEncodedAndEncryptedWithKeyLength.indexOf(':');
					int keyLength = 256;
					if (index != -1) {
						passwordEncodedAndEncrypted = passwordEncodedAndEncryptedWithKeyLength.substring(index + 1);
						keyLength = Integer.parseInt(passwordEncodedAndEncryptedWithKeyLength.substring(0, index));
					} else {
						passwordEncodedAndEncrypted = passwordEncodedAndEncryptedWithKeyLength;
					}

					String text = Crypt.decrypt(passwordEncodedAndEncrypted, keyLength);

					logger.fine("Key " + key + " decrypted");

					return text;
				} catch (Exception e) {
					logger.log(Level.SEVERE, "Could not decrypt password: " + key, e);
				}

			} else if (key.startsWith(Cryptor.DECRYPTION_PREFIX)) {

				logger.fine("Key " + key + " seems to be an encrypted value (Jinni-encryption).");

				try {
					String passwordEncrypted = key.substring(Cryptor.DECRYPTION_PREFIX.length());

					String text = Cryptor.decrypt(null, null, null, null, passwordEncrypted);

					logger.fine("Key " + key + " decrypted");

					return text;
				} catch (Exception e) {
					logger.log(Level.SEVERE, "Could not decrypt password: " + key, e);
				}

			} else if ((key.startsWith("decrypt('") && key.endsWith("')")) || (key.startsWith("decrypt(\"") && key.endsWith("\")"))) {

				logger.fine("Key " + key + " seems to be an encrypted value (Jinni-encryption).");

				try {
					String quote = key.startsWith("${decrypt('") ? "'" : "\"";
					int idx1 = key.indexOf(quote);
					int idx2 = key.lastIndexOf(quote);
					if (idx1 > 0 && idx2 > idx1) {
						key = key.substring(("decrypt(" + quote).length(), key.length() - 2);
					}

					String text = Cryptor.decrypt(null, null, null, null, key);

					logger.fine("Key " + key + " decrypted");

					return text;
				} catch (Exception e) {
					logger.log(Level.SEVERE, "Could not decrypt password: " + key, e);
				}
			}
		}

		String value = System.getProperty(key);

		return value;
	}

}
