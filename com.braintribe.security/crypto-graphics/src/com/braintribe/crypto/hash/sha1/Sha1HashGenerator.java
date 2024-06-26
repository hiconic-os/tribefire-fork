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
package com.braintribe.crypto.hash.sha1;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.braintribe.crypto.CryptoServiceException;
import com.braintribe.crypto.hash.HashGenerator;
import com.braintribe.crypto.utils.TextUtils;

public class Sha1HashGenerator extends HashGenerator {
	
	public static String SHA1( String text, String encoding) throws CryptoServiceException  {
		try {
			if (encoding != null)
				return SHA1( text.getBytes( encoding));
			else
				return SHA1( text.getBytes());
		} catch (UnsupportedEncodingException e) {
			throw new CryptoServiceException( e);
		}
	}
	
	public static String SHA1(byte [] bytes) throws CryptoServiceException  {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance( "SHA1");
			byte[] sha1hash = new byte[40];
			md.update(bytes, 0, bytes.length);
			sha1hash = md.digest();
			return TextUtils.convertToHex( sha1hash);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoServiceException( e);
		}
	}
	
	public static String SHA1( Serializable object) throws CryptoServiceException {
		byte [] bytes = convertObjectToBytes(object);
		return SHA1( bytes);
	}
}
