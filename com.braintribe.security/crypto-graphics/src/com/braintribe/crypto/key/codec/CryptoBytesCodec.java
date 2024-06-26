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
package com.braintribe.crypto.key.codec;

import java.security.Key;

import javax.crypto.Cipher;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.logging.Logger;

/**
 * de- or en-crypts bytes
 * 
 * if you're using RSA cipher, note the following:
 * 	encode - you need to specify the private key 
 *  decode - you need to specify the public key 
 * 
 * @author pit
 *
 */
public class CryptoBytesCodec implements Codec<byte[], byte[]> {
	
	private Logger log = Logger.getLogger( CryptoBytesCodec.class);

	private Key key = null;
	private String cipherName = null;	
	
	public void setKey(Key key) {
		this.key = key;
	}

	public void setCipher(String cipher) {
		this.cipherName = cipher;
	}

	@Override
	public byte[] encode(byte[] value) throws CodecException {
		try {
			Cipher cipher = Cipher.getInstance( cipherName);
			cipher.init(Cipher.ENCRYPT_MODE, key);			
			return cipher.doFinal(value);			
		} catch (Exception e) {
			String msg = "cannot encrypt message as " + e; 
			log.error( msg, e);
			throw new CodecException( msg, e);
		} 		
	}

	@Override
	public byte[] decode(byte[] encodedValue) throws CodecException {
		try {
			Cipher cipher = Cipher.getInstance( cipherName);
			cipher.init(Cipher.DECRYPT_MODE, key);			
			return cipher.doFinal( encodedValue);			
		} catch (Exception e) {
			String msg = "cannot encrypt message as " + e; 
			log.error( msg, e);
			throw new CodecException( msg, e);
		} 		
	}

	@Override
	public Class<byte[]> getValueClass() {

		return byte[].class;
	}

}
