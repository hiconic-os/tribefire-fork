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
package com.braintribe.crypto.key;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.braintribe.crypto.CryptoServiceException;
import com.braintribe.crypto.base64.Base64;
import com.braintribe.crypto.stream.CryptoStream;
import com.braintribe.logging.Logger;

/**
 * little class to decrypt / encrypt with a password.. 
 * 
 * @author pit
 *
 */
public class PasswordEncryptor {

	private final static Logger log = Logger.getLogger(CryptoStream.class);
	
	private PBEKeySpec pbeKeySpec = null;
    private PBEParameterSpec pbeParamSpec = null;
    private SecretKeyFactory keyFac = null;
    private SecretKey pbeKey = null;
    private static final String algo = "PBEWithMD5AndDES";

    // Salt
    private byte[] salt = {
        (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
        (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
    };

    // Iteration count
    private int count = 20;
    private String charset = null;
    	
	
	
	public void setCharset(String charset) {
		this.charset = charset;
	}

	public PasswordEncryptor( String pwd) throws CryptoServiceException {
		 try {
				// Create PBE parameter set
				pbeParamSpec = new PBEParameterSpec(salt, count);	    
				pbeKeySpec = new PBEKeySpec( pwd.toCharArray());
				keyFac = SecretKeyFactory.getInstance( algo);
				pbeKey = keyFac.generateSecret(pbeKeySpec);
			} 
		    catch (NoSuchAlgorithmException e) {
				String msg = "cannot instantiate password base encryption as " + e;
				log.error( msg, e);
				throw new CryptoServiceException(msg, e);
			} 
		    catch (InvalidKeySpecException e) {
		    	String msg = "cannot instantiate password base encryption as " + e;
				log.error( msg, e);
				throw new CryptoServiceException(msg, e);
			}		
	}
	
	/**
	 * generates cipher 
	 * @param mode 
	 * @return
	 * @throws CryptoException
	 */
	private Cipher getCipher( int mode) throws CryptoServiceException{
	    // Create PBE Cipher
	    Cipher cipher;
		try {
			cipher = Cipher.getInstance( algo);

			// Initialize PBE Cipher with key and parameters
			cipher.init( mode, pbeKey, pbeParamSpec);
		} 
		catch (InvalidKeyException e) {		
			e.printStackTrace();
			throw new CryptoServiceException("cannot get cipher as " + e, e);
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new CryptoServiceException("cannot get cipher as " + e, e);
		} 
		catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new CryptoServiceException("cannot get cipher as " + e, e);
		} 
		catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new CryptoServiceException("cannot get cipher as " + e, e);
		}
	    
	    return cipher;
	}
	
	public byte [] encrypt( byte [] input) throws CryptoServiceException {
		try {
			Cipher cipher = getCipher( Cipher.ENCRYPT_MODE);
			return cipher.doFinal( input);
		} catch (IllegalBlockSizeException e) {
			String msg = "cannot encrypt input as " + e;
			log.error(msg, e);
			throw new CryptoServiceException(msg, e);
		} catch (BadPaddingException e) {
			String msg = "cannot encrypt input as " + e;
			log.error(msg, e);
			throw new CryptoServiceException(msg, e);
		} 
	}

	
	public String encrypt( String input) throws CryptoServiceException {
		try {		
			byte[] inputBytes = null;
			if (charset != null) 
				inputBytes = input.getBytes( charset);
			else
				inputBytes = input.getBytes();			
			return Base64.encodeBytes( encrypt( inputBytes), Base64.DONT_BREAK_LINES);
		} catch (UnsupportedEncodingException e) {
			String msg ="cannot extract bytes from string as " + e;
			log.error( msg, e);
			throw new CryptoServiceException( msg, e);
		} 
	}
	
	public byte [] decrypt( byte [] input) throws CryptoServiceException {
		try {
			Cipher cipher = getCipher( Cipher.DECRYPT_MODE);
			return cipher.doFinal( input);
		} catch (IllegalBlockSizeException e) {
			String msg = "cannot decrypt input as " + e;
			log.error(msg, e);
			throw new CryptoServiceException(msg, e);
		} catch (BadPaddingException e) {
			String msg = "cannot decrypt input as " + e;
			log.error(msg, e);
			throw new CryptoServiceException(msg, e);
		}
	}
	
	public String decrypt( String input) throws CryptoServiceException {
		try {
			byte[] bytes = Base64.decode( input);
			byte [] recoveredBytes = decrypt( bytes);
			 String recovered = null;
			 if (charset != null)
				 recovered = new String(recoveredBytes, charset);
			 else
				 recovered = new String(recoveredBytes);
			 return recovered;
		}  catch (UnsupportedEncodingException e) {
			String msg = "cannot create output string as " + e;
			log.error(msg, e);
			throw new CryptoServiceException(msg, e);
		} 
	}
	
	
}
