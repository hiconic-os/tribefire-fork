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
package com.braintribe.utils.xml;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.Test;
import org.w3c.dom.Document;

import com.braintribe.exception.Exceptions;
import com.braintribe.utils.FileTools;

public class XmlToolsTest {

	@Test
	public void testXXEAttack() throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("<?gm-xml version=\"3\"?>\n");
		sb.append("<!DOCTYPE lolz [\n");
		sb.append("<!ENTITY lol SYSTEM \"file:///\">\n");
		sb.append("]>\n");
		sb.append("<gm-data><string>&lol;</string></gm-data>\n");
		ByteArrayInputStream bais = new ByteArrayInputStream(sb.toString().getBytes("UTF-8"));

		@SuppressWarnings("unused")
		Document xml = null;
		try { 
			xml = XmlTools.loadXML(bais);
			throw new AssertionError("XXE attack was successful.");
		} catch(Exception e) {
			//This is expected
			return;
		}

		/* This was for testing... this point should not be reached anymore. Keeping this for future purposes. 
		BtAssertions.assertThat(xml).isNotNull();

		Element documentElement = xml.getDocumentElement();

		BtAssertions.assertThat(documentElement).isNotNull();

		Node stringChild = documentElement.getFirstChild();

		BtAssertions.assertThat(stringChild).isNotNull();
		BtAssertions.assertThat(stringChild.getNodeName()).isEqualTo("string");

		String textContent = stringChild.getTextContent();

		BtAssertions.assertThat(textContent).isNotNull();
		BtAssertions.assertThat(textContent).isNotEmpty();

		System.out.println(textContent);
		*/
	}

	@Test
	public void testLocalXInclude() throws Exception {
		Document document = XmlTools.loadXML(new File("res/Folder1/importing2.xml"));
		XmlTools.xinclude(document, null);

		String name = XmlTools.evaluateXPathToString(document, "/ContentHierarchy/node[@id='10000000']/node[@id='11000000']/@name", null);
		assertThat(name).isEqualTo("HL");
	}
	
	@Test
	public void testForeignXInclude() throws Exception {
		Document document = XmlTools.loadXML(new File("res/Folder1/importing1.xml"));
		XmlTools.xinclude(document, id -> {
			String filename = FileTools.getFilenameFromFullPath(id);
			File inputFile = new File("res/Folder2/"+filename);
			try {
				return new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {
				throw Exceptions.unchecked(e, "Could not open file "+inputFile.getAbsolutePath());
			}
		});

		String name = XmlTools.evaluateXPathToString(document, "/ContentHierarchy/node[@id='10000000']/node[@id='11000000']/@name", null);
		assertThat(name).isEqualTo("HL");
	}
	
}
