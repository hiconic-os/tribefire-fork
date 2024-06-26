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
package com.braintribe.codec.string;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class GzipCodecTest {

	@Test
	public void testNull() throws Exception {
		GzipCodec codec = new GzipCodec();
		Assert.assertNull(codec.encode(null));
		Assert.assertNull(codec.decode(null));
	}
	
	@Test
	public void testCodecSimple() throws Exception {
		
		GzipCodec codec = new GzipCodec();
		
		String test = "Hello, world.";
		byte[] testBytes = test.getBytes("UTF-8");
		
		byte[] encodedBytes = codec.encode(testBytes);
		
		byte[] decodedBytes = codec.decode(encodedBytes);
		
		String decodedString = new String(decodedBytes, "UTF-8");
		
		Assert.assertEquals(test, decodedString);
		
	}
	

	@Test
	public void testCodecEmpty() throws Exception {
		
		GzipCodec codec = new GzipCodec();
		
		String test = "";
		byte[] testBytes = test.getBytes("UTF-8");
		
		byte[] encodedBytes = codec.encode(testBytes);
		
		byte[] decodedBytes = codec.decode(encodedBytes);
		
		String decodedString = new String(decodedBytes, "UTF-8");
		
		Assert.assertEquals(test, decodedString);
		
	}
	

	@Test
	public void testCodecNull() throws Exception {
		
		GzipCodec codec = new GzipCodec();
		
		byte[] encodedBytes = codec.encode(null);
		Assert.assertNull(encodedBytes);
		
		byte[] decodedBytes = codec.decode(null);
		Assert.assertNull(decodedBytes);
				
	}
	
	@Test
	public void testCodecRandomBytes() throws Exception {
		
		GzipCodec codec = new GzipCodec();

		Random rnd = new Random();
		int count = 10000;
		ByteArrayOutputStream baos = new ByteArrayOutputStream(count);
		for (int i=0; i<count; ++i) {
			baos.write(rnd.nextInt());
		}
		baos.close();
		
		byte[] testBytes = baos.toByteArray();
		
		byte[] encodedBytes = codec.encode(testBytes);
		
		byte[] decodedBytes = codec.decode(encodedBytes);
		
		Assert.assertArrayEquals(testBytes, decodedBytes);
				
	}
	
	@Test
	public void testCodecLargeText() throws Exception {
		
		GzipCodec codec = new GzipCodec();

		int count = 10000;
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<count; ++i) {
			sb.append("The quick brown fox jumps over the lazy dog. ");
		}
		String testString = sb.toString();
		
		byte[] testBytes = testString.getBytes("UTF-8");
		
		byte[] encodedBytes = codec.encode(testBytes);
		
		byte[] decodedBytes = codec.decode(encodedBytes);
		
		String decodedString = new String(decodedBytes, "UTF-8");
		
		Assert.assertEquals(testString, decodedString);
				
	}
	
	@Test
	public void testValueClass() throws Exception {
		GzipCodec codec = new GzipCodec();
		Class<byte[]> valueClass = codec.getValueClass();
		Assert.assertEquals(byte[].class, valueClass);
	}
}
