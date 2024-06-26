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
package com.braintribe.test;




import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public class CreateBuilderLabs {
	private static String xsdFides = "res/fides/FIDES Data Model V2.1";
	
	@Test
	public void testDefaultCreation() {
		try {
			DomParser.create().makeItSo();
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail( "Exception thrown");
		}
	}

	public void testNamespaceAwareCreation(){
		try {
			DomParser.create().setNamespaceAware().makeItSo();
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail( "Exception thrown");
		}
	}
	@Test
	public void testCopy() {
		try {
			Document document = DomParser.load().setNamespaceAware().from( new File( xsdFides + ".xsd"));
			Document copy = DomParser.create().setNamespaceAware().makeItSo(document);		
			DomParser.write().from( copy).to( new File( xsdFides + ".copy.xsd"));
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail( "Exception thrown");
		}
		
	}
}
