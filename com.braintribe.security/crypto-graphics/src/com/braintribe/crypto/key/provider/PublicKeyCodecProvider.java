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
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;


/**
 * <p>Provides {@link com.braintribe.codec.Codec}(s) capable of importing/exporting 
 *    {@link PublicKey}(s) from/to byte arrays.
 *    
 * @see KeyCodecProvider
 *
 */
public class PublicKeyCodecProvider extends KeyCodecProvider<PublicKey> {

	@Override
	public Codec<PublicKey, byte[]> apply(String algorithm) throws RuntimeException {
		return provide(PublicKey.class, algorithm);
	}

	@Override
	protected Codec<PublicKey, byte[]> createExporter(final String algorithm) {
		return new Codec<PublicKey, byte[]>() {

			@Override
			public byte[] encode(PublicKey value) {
				return value.getEncoded();
			}

			@Override
			public PublicKey decode(byte[] encodedValue) throws CodecException {
				try {
					X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedValue);
					KeyFactory factory = KeyFactory.getInstance(algorithm);
					return factory.generatePublic(spec);
				} catch (Exception e) {
					 throw new CodecException("Failed to decode public key: "+e.getMessage(), e);
				}
			}

			@Override
			public Class<PublicKey> getValueClass() {
				return PublicKey.class;
			}
			
		};
	}

}
