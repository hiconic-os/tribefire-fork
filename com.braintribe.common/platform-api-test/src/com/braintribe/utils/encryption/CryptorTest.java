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
package com.braintribe.utils.encryption;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CryptorTest {

	@Test
	public void testCryptor() {

		String key = "test";
		String orig = "Hello, world";
		String encrypted = Cryptor.encrypt(key, null, null, null, orig);
		String decrypted = Cryptor.decrypt(key, null, null, null, encrypted);

		assertThat(decrypted).isEqualTo(orig);

	}

}
