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

import javax.xml.transform.OutputKeys;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

public class WriteBuilderLab {
	
	private static String name = "res/fides/FIDES Data Model V2.1.xsd";
	private static String prettyPrint = "res/prettyPrint.xslt";
	
	private Document load(String name) {
		try {
			Document document = DomParser.load().setNamespaceAware().fromFilename(name);
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
	public void simpleWrite() {
		Document document = load( name);
		
		try {
			DomParser.write().from(document).to( new File( name + ".out.xml"));
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
	}
	
	@Test
	public void simpleStandaloneKeyWrite() {
		Document document = load( name);
		
		try {
			DomParser.write().from(document).setOutputProperty(OutputKeys.STANDALONE, "yes").to( new File( name + ".out.standalone.xml"));
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
	}

	@Test
	public void styledWrite() {
		Document document = load( name);
		
		try {
			DomParser.write().from(document).setStyleSheet( new File( prettyPrint)).to( new File( name + ".pretty.xml"));
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
	}
	
	@Test
	public void stringWrite() {
		Document document = load( name);		
		try {
			String value = DomParser.write().from(document).setStyleSheet( new File( prettyPrint)).to();
			System.out.println(value);
		} catch (DomParserException e) {
			e.printStackTrace();
			Assert.fail("Exception thrown " + e);
		}
	}

}

