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
import com.braintribe.crypto.base64.Base64;
import com.braintribe.logging.Logger;

/**
 * a codec to en- or decrypt base64 encoded strings 
 * 
 * set key - symmetric or asymmetric key
 * set cipher - "DeSede" (symmetric) or "RSA" (asymmetric)
 * 
 * if you're using RSA cipher, note the following:
 * 	encode - you need to specify the private key 
 *  decode - you need to specify the public key
 *   
 * @author pit
 *
 */
public class CryptoStringCodec implements Codec<String, String> {
	
	private Logger log = Logger.getLogger( CryptoBytesCodec.class);

	private Key key = null;
	private String cipherName = null;
	private String encoding = null;
	
	public void setKey(Key key) {
		this.key = key;
	}
	public void setCipher(String cipher) {
		this.cipherName = cipher;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public String encode(String value) throws CodecException {
		try {
			Cipher cipher = Cipher.getInstance( cipherName);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] inputBytes = null;
			if (encoding != null) 
				inputBytes = value.getBytes( encoding);
			else
				inputBytes = value.getBytes();
			return Base64.encodeBytes( cipher.doFinal(inputBytes), Base64.DONT_BREAK_LINES);			
		} catch (Exception e) {
			String msg = "cannot encrypt message as " + e; 
			log.error( msg, e);
			throw new CodecException( msg, e);
		} 
		
	}

	@Override
	public String decode(String encodedValue) throws CodecException {
		 try {
			 byte[] bytes = Base64.decode( encodedValue);
			 Cipher cipher = Cipher.getInstance( cipherName);
			 cipher.init(Cipher.DECRYPT_MODE, key);
			 byte[] recoveredBytes = cipher.doFinal( bytes);
			 String recovered = null;
			 if (encoding != null)
				 recovered = new String(recoveredBytes, encoding);
			 else
				 recovered = new String(recoveredBytes);
			 return recovered;
		} catch (Exception e) {
			String msg = "cannot decrypt message as " + e; 
			log.error( msg, e);
			throw new CodecException( msg, e);
		} 
	}

	@Override
	public Class<String> getValueClass() {
		return String.class;
	}

}
