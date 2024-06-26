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
package com.braintribe.xml.parser.sax.test.framework;

import java.io.File;
import java.util.Date;

import org.junit.Assert;

import com.braintribe.utils.xml.parser.sax.SaxParser;
import com.braintribe.utils.xml.parser.sax.SaxParserException;
import com.braintribe.utils.xml.parser.sax.builder.SaxContext;

public class TestRunner {
	private static SaxContext sc;
	
	public static float runTest( int prime, int num, File content, File schema) {
		sc = null;
		long time = 0; 
		for (int i = 0; i < num; i++) {			
			long dif = doTest(content, schema, null);
			if (i >= prime) {
				time += dif;
			}
		}
		float average = new Float( (float) time / (num-prime));
		if (schema == null) {
			System.out.println("averaging over [" + (num-prime) + "] runs, parsing of [" + content.getAbsolutePath() + "] took [" + average + "] ms");
		}
		else {
			System.out.println("averaging over [" + (num-prime) + "] runs, parsing of [" + content.getAbsolutePath() + "] while validating with [" + schema.getAbsolutePath() + "] took [" + average + "] ms");
		}
		return average;
	}

	@SuppressWarnings("rawtypes")
	private static long doTest(File content, File schema, Class expectedException) {
		if (sc == null) {
			sc = SaxParser.parse();
			if (schema != null) {
				sc.schema(schema);
			} 
			
			else {
				sc.setValidating();
				sc.setNamespaceAware();
			}			
			sc.setHandler( new TestContentHandler());
		}		
		Date before = new Date();
		boolean exceptionThrown = false;
		try {
			sc.parse(content);
		} catch (SaxParserException e) {
			if (expectedException == null) {
				Assert.fail( "Exception thrown " + e.getMessage());
				return 0L;
			}
			else {
				if (e.getClass() != expectedException) {
					Assert.fail( "unexpected exception thrown " + e.getMessage());
				}
				else {
					exceptionThrown = true;
				}
			}
		}
		
		if (expectedException != null && exceptionThrown == false) {
			Assert.fail("expected exception not thrown :" +  expectedException.getName());
		}
		Date after = new Date();
		long dif = after.getTime() - before.getTime();		
		return dif;
	}
	
	public static void runTest( File content, File schema, Class expectedException) {
		sc = null;
		long dif = doTest(content, schema, expectedException);
		if (schema == null) {
			System.out.println("parsing of [" + content.getAbsolutePath() + "] took [" + dif + "] ms");
		}
		else {
			System.out.println("parsing of [" + content.getAbsolutePath() + "] while validating with [" + schema.getAbsolutePath() + "] took [" + dif + "] ms");
		}
	}
}
