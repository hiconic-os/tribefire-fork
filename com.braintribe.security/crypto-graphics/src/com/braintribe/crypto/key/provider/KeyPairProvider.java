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
package com.braintribe.crypto.key.provider;

import java.security.KeyPair;
import java.util.function.Supplier;

import com.braintribe.crypto.CryptoServiceException;
import com.braintribe.crypto.key.AsymmetricKeyGenerator;


/**
 * A {@link Supplier} compliant generator of {@link KeyPair}(s).
 * 
 */
public class KeyPairProvider implements Supplier<KeyPair> {
	
	private int length;
	private String generatorAlgo;
	private String randomAlgo;
	private String randomProvider;
	
	public void setLength(int length) {
		this.length = length;
	}

	public void setGeneratorAlgo(String generatorAlgo) {
		this.generatorAlgo = generatorAlgo;
	}

	public void setRandomAlgo(String randomAlgo) {
		this.randomAlgo = randomAlgo;
	}
	
	public void setRandomProvider(String randomProvider) {
		this.randomProvider = randomProvider;
	}
	

	@Override
	public KeyPair get() throws RuntimeException {
		
		if (length == 0) {
			throw new RuntimeException("No length was configured to this provider.");
		}
		
		try {
			AsymmetricKeyGenerator keyGen = null;
			
			if (this.generatorAlgo != null && this.randomAlgo != null && this.randomProvider != null) {
				keyGen = new AsymmetricKeyGenerator(generatorAlgo, randomAlgo, randomProvider, length);
			} else {
				keyGen = new AsymmetricKeyGenerator(length);
			}
			
			keyGen.generateKeyPair();
			
			return keyGen.getPair();
			
		} catch (CryptoServiceException e) {
			throw new RuntimeException("Failed to generate key pair: "+e.getMessage(), e);
		}
	}
	
}
