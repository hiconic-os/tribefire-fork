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
package com.braintribe.test.validate;

import java.io.File;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public class ModelDeclarationLab {

	private static File contents = new File("res/model-declaration");
	
	private static File xsdModelDeclaration = new File( contents, "model-declaration.xsd");
	private static File xmlModelDeclaration = new File( contents, "model-declaration.xml");

	
	
	
	private Document load(File file) {
		try {
			Document document = DomParser.load().setNamespaceAware().from( file);
			if (document == null) {
				Assert.fail("No document returned");
			}
			return document;
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
		return null;
	}
	
	@Test
	public void testBooleanOutput() {
		Document xmlPayorderDocument = load( xmlModelDeclaration);
		Document xsdDocument = load( xsdModelDeclaration);
		try {
			boolean validate = DomParser.validate().from( xmlPayorderDocument).schema( xsdDocument).makeItSo();
			Assert.assertTrue( "file is not valid", validate);
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}	
	}
	
	@Test
	public void testStringOutput() {
		Document xsdDocument = load( xsdModelDeclaration);
		try {		
			String validate = DomParser.validate().from( xmlModelDeclaration).schema( xsdDocument).makeItToString();
			System.out.println("validates [" + validate + "]");
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}	
	}
	
	@Test
	public void testWriterOutput() {
		Document xsdDocument = load( xsdModelDeclaration);
		try {
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			boolean validate = DomParser.validate().from( xmlModelDeclaration).schema( xsdDocument).makeItSo(result);
			Assert.assertTrue( "file is not valid", validate);			
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}	
	}
	
	

}
