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

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;


/**
 * <p>Provides {@link com.braintribe.codec.Codec}(s) capable of importing/exporting 
 *    {@link PrivateKey}(s) from/to byte arrays.
 * 
 * @see KeyCodecProvider
 *
 */
public class PrivateKeyCodecProvider extends KeyCodecProvider<PrivateKey> {

	@Override
	public Codec<PrivateKey, byte[]> apply(String algorithm) throws RuntimeException {
		return provide(PrivateKey.class, algorithm);
	}

	@Override
	protected Codec<PrivateKey, byte[]> createExporter(final String algorithm) {
		return new Codec<PrivateKey, byte[]>() {

			@Override
			public byte[] encode(PrivateKey value) {
				return value.getEncoded();
			}

			@Override
			public PrivateKey decode(byte[] encodedValue) throws CodecException {
				try {
					PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encodedValue);
					KeyFactory factory = KeyFactory.getInstance(algorithm);
					return factory.generatePrivate(spec);
				} catch (Exception e) {
					 throw new CodecException("Failed to decode private key: "+e.getMessage(), e);
				}
			}

			@Override
			public Class<PrivateKey> getValueClass() {
				return PrivateKey.class;
			}
			
		};
	}
	
}
