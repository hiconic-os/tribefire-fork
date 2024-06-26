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

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public class FidesLab {

	private static File contents = new File( "res/fides");
	private static File xsdFides = new File( contents, "FIDES Data Model V2.1.xsd");
	private static File xmlFidesPayorder = new File( contents, "res/payorder.xml");
	private static File xmlFidesStatement = new File( contents, "statementgroup.xml");
	
	
	
	private Document load(File name) {
		try {
			Document document = DomParser.load().setNamespaceAware().from(name);
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
	
	//@Test
	public void testPayorder() {
		Document xmlPayorderDocument = load( xmlFidesPayorder);
		Document xsdDocument = load( xsdFides);
		try {
			boolean validate = DomParser.validate().from( xmlPayorderDocument).schema( xsdDocument).makeItSo();
			System.out.println("validates [" + validate + "]");
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}	
	}
	@Test
	public void testStatementGroup() {
		Document xmlPayorderDocument = load( xmlFidesStatement);
		Document xsdDocument = load( xsdFides);
		try {
			boolean validate = DomParser.validate().from( xmlPayorderDocument).schema( xsdDocument).makeItSo();
			System.out.println("validates [" + validate + "]");
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}	
	}
	
	

}
