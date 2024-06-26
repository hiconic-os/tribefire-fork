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
package com.braintribe.xml.parser.sax.test;

import java.io.File;

import org.junit.Test;
import org.xml.sax.SAXParseException;

import com.braintribe.xml.parser.sax.test.framework.TestRunner;

public class CsParseLab {

	@Test
	public void testPain1AutoXsd() {
		String name = "res/parse/cs/pain.001.001.03.a.xml";
		TestRunner.runTest( 5, 55, new File(name), null);				
	}	
	@Test
	public void testPain1SpecifiedXsd() {
		String name = "res/parse/cs/pain.001.001.03.a.xml";
		String schema = "res/parse/cs/pain.001.001.03.ISO.xsd";		
		TestRunner.runTest( new File(name), new File( schema), null);
	}	
		
	@Test
	public void testPacs() {
		String name = "res/parse/cs/pacs.008.001.01.a.xml";
		String schema = "res/parse/cs/pacs.008.001.01.ISO.xsd";		
		TestRunner.runTest( 5, 105, new File(name), new File( schema));				
	}
	
	@Test
	public void testDumpedPacs() {
		String name = "res/parse/cs/dump/pacs.008.01.01.a.out.xml";
		String schema = "res/parse/cs/pacs.008.001.01.ISO.xsd";		
		TestRunner.runTest( 5, 105, new File(name), new File( schema));				
	}
	
	@Test
	public void testMissingXsdPacs() {
		String name = "res/parse/cs/pacs.008.001.01.missing.xml";
		String schema = "res/parse/cs/pacs.008.001.01.ISO.xsd";
		TestRunner.runTest( new File(name), new File( schema), null);
	}

	
	@Test
	public void testCrookedPacsSpecifiedXsd() {
		String name = "res/parse/cs/pacs.008.001.01.crooked.xml";
		String schema = "res/parse/cs/pacs.008.001.01.ISO.xsd";		
		TestRunner.runTest( new File(name), new File( schema), com.braintribe.utils.xml.parser.sax.SaxParserException.class);		
	}
	@Test
	public void testCrookedPacsAutoXsd() {
		String name = "res/parse/cs/pacs.008.001.01.crooked.xml";			
		TestRunner.runTest( new File(name), null, com.braintribe.utils.xml.parser.sax.SaxParserException.class);		
	}
	@Test
	public void testCrookedPainSpecifiedXsd() {
		String name = "res/parse/cs/pain.001.001.03.crooked.xml";
		String schema = "res/parse/cs/pain.001.001.03.ISO.xsd";
		TestRunner.runTest( new File(name), new File( schema), com.braintribe.utils.xml.parser.sax.SaxParserException.class);				
	}
	
	@Test
	public void testCrookedPainAutoXsd() {
		String name = "res/parse/cs/pain.001.001.03.crooked.xml";		
		TestRunner.runTest( new File(name), null, com.braintribe.utils.xml.parser.sax.SaxParserException.class);				
	}

}
