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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import com.braintribe.crypto.base64.Base64;

public class Compressor {
	
	public static String compress( String in) throws IOException {
		byte [] data = in.getBytes();
		byte [] compressedData = compress( data);
		return Base64.encodeBytes(compressedData);
	}
	
	public static byte [] compress( byte [] data) throws IOException {
				
		Deflater deflater = new Deflater();
		deflater.setLevel( Deflater.BEST_COMPRESSION);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
		DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
		deflaterOutputStream.write( data);
		deflaterOutputStream.flush();
		deflaterOutputStream.close();
		byte [] compressedData = byteArrayOutputStream.toByteArray();
		return compressedData;
		
	}
	public static String decompress( String in) throws IOException {
		byte [] data = Base64.decode( in);
		byte [] uncompressedData = decompress( data);
		return new String( uncompressedData);
	}
	
	public static byte[] decompress( byte [] data) throws IOException {
	
		Inflater inflater = new Inflater();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
		InflaterOutputStream inflaterOutputStream = new InflaterOutputStream(byteArrayOutputStream, inflater);
		inflaterOutputStream.write( data);
		inflaterOutputStream.flush();
		inflaterOutputStream.close();		
		byte [] uncompressedData = byteArrayOutputStream.toByteArray();
		return uncompressedData;
		
	}
}
