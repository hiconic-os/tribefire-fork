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

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

public class GzipBase64CodecTest {

	@Test
	public void testCodecSimple() throws Exception {
		
		GzipBase64Codec codec = new GzipBase64Codec();
		
		String test = "Hello, world.";
		
		String encodedString = codec.encode(test);
		
		String decodedString = codec.decode(encodedString);
		
		Assert.assertEquals(test, decodedString);
		
	}
	

	@Test
	public void testCodecEmpty() throws Exception {
		
		GzipBase64Codec codec = new GzipBase64Codec();
		
		String test = "";

		String encodedString = codec.encode(test);
		
		String decodedString = codec.decode(encodedString);
		
		Assert.assertEquals(test, decodedString);
		
	}
	

	@Test
	public void testCodecNull() throws Exception {
		
		GzipBase64Codec codec = new GzipBase64Codec();
		
		String encodedString = codec.encode(null);
		Assert.assertNull(encodedString);
		
		String decodedString = codec.decode(null);
		Assert.assertNull(decodedString);
				
	}
	
	@Test
	public void testCodecRandomString() throws Exception {
		
		GzipBase64Codec codec = new GzipBase64Codec();

		int count = 10000;
		
		String test = RandomStringUtils.randomAlphanumeric(count);
		
		String encodedString = codec.encode(test);
		
		String decodedString = codec.decode(encodedString);
		
		Assert.assertEquals(test, decodedString);
				
	}
	
	@Test
	public void testCodecLargeText() throws Exception {
		
		GzipBase64Codec codec = new GzipBase64Codec();

		int count = 10000;
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<count; ++i) {
			sb.append("The quick brown fox jumps over the lazy dog. ");
		}
		String test = sb.toString();
		
		String encodedString = codec.encode(test);
		
		int testLength = test.length();
		int encodedStringLength = encodedString.length();
		Assert.assertTrue(encodedStringLength < testLength);
		
		String decodedString = codec.decode(encodedString);
		
		Assert.assertEquals(test, decodedString);
				
	}
	
	@Test
	public void testValueClass() throws Exception {
		GzipBase64Codec codec = new GzipBase64Codec();
		Class<String> valueClass = codec.getValueClass();
		Assert.assertEquals(String.class, valueClass);
	}

}
