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


import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public class LoadBuilderLabs {

	private static String name = "res/fides/FIDES Data Model V2.1.xsd";
	
	@Test
	public void testSimpleLoad() {
		try {
			Document document = DomParser.load().fromFilename(name);
			if (document == null) {
				Assert.fail("No document returned");
			}
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
	}
	// cannot be reached anymore 2019.01.08, pit 
	public void testUrlLoad() {
		String urlAsString = "http://nvd.nist.gov/schema/scap-core_0.1.xsd";
		try {
			Document doc = DomParser.load().from( new URL( urlAsString));
			if (doc == null) {
				Assert.fail( "No document returned");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
	}

	// pointless : cannot be reached anymore 2019.01.08, pit 
	public void testUnresolvedUrlLoad() {
		String urlAsString = "http://nvd.nist.gov/schema/scap-core_0.1.xsd.x";
		try {
			Document doc = DomParser.load().from( new URL( urlAsString));
			if (doc != null) {
				Assert.fail( "Document returned");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		} catch (DomParserException e) {
			return;
		}
		Assert.fail("No Exception thrown ");
	}

}
