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
package com.braintribe.crypto.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import com.braintribe.logging.Logger;

/**
 * primitive text/file functions 
 * 
 * @author pit
 *
 */
public class TextUtils {
	
	private static Logger log = Logger.getLogger(TextUtils.class);
	
	public static String readContentsFromFile( File aFile) {
		return readContentsFromFile(aFile, null);
	}
	/**
	 * super fast read function for string 
	 * - depends on javas correct handling of byte to string conversion 
	 * @param aFile - the file to read
	 * @return - the file's content as string
	 */
	public static String readContentsFromFile( File aFile, String encoding) {
		byte[] bytes = getFileBytes(aFile);
		if (bytes == null)
			return "";
		try {
			if (encoding != null)
				return new String( bytes, encoding);
			else
				return new String( bytes);
		} catch (UnsupportedEncodingException e) {
			log.error("can't read from file [" + aFile + "] as " + e, e);
			return null;
		}
	}
	
	public static byte [] getFileBytes( File aFile) {
		byte[] bytes = new byte[(int) aFile.length()];
		try {
			RandomAccessFile ref = new RandomAccessFile( aFile, "r");
			ref.readFully( bytes);
			ref.close();
			return bytes;
		} catch (FileNotFoundException e) {
			log.error("can't read from file [" + aFile + "] as " + e, e);
			return null;
		} catch (IOException e) {
			log.error("can't read from file [" + aFile + "] as " + e, e);
			return null;
		}
	}
	
	public static void writeContentsToFile( String content, File aFile){
		writeContentsToFile( content, aFile, null, false);
	}
	
	public static void writeContentsToFile( String content, File aFile, String encoding){
		writeContentsToFile( content, aFile, encoding, false);
	}
	
	public static void writeContentsToFile( String content, File aFile, String encoding, boolean append) {		
		try {
			OutputStream stream = new FileOutputStream( aFile, append);
			OutputStreamWriter writer = null;
			if (encoding != null)
				writer = new OutputStreamWriter(  stream, encoding);
			else
				writer = new OutputStreamWriter( stream);
			writer.write( content);
			writer.close();
		} catch (FileNotFoundException e) {
			log.error("can't write to file [" + aFile + "] as " + e, e);			
		} catch (UnsupportedEncodingException e) {
			log.error("can't write to file [" + aFile + "] as " + e, e);			
		} catch (IOException e) {
			log.error("can't write to file [" + aFile + "] as " + e, e);			
		}		
	}
	
	/**
	 * converts an array of bytes to a hex string 
	 * @param data - the bytes to convert. 
	 * @return
	 */
	public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	int halfbyte = (data[i] >>> 4) & 0x0F;
        	int two_halfs = 0;
        	do {
	        	if ((0 <= halfbyte) && (halfbyte <= 9))
	                buf.append((char) ('0' + halfbyte));
	            else
	            	buf.append((char) ('a' + (halfbyte - 10)));
	        	halfbyte = data[i] & 0x0F;
        	} while(two_halfs++ < 1);
        }
        return buf.toString();
    }
	
	/**
	 * converts an array of bytes to a hex string 
	 * @param data - the bytes to convert. 
	 * @return
	 */
	public static String convertToHex2(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
        	buf.append( String.format("%02X", data[i]));                
        }
        return buf.toString();
    }
	
	public void main(String [] args) {
		
	}
	
	
}
