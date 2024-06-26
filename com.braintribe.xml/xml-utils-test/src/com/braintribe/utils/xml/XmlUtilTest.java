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

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.braintribe.utils.FileTools;
import com.braintribe.utils.IOTools;

public class XmlUtilTest {

	@Test
	public void testWriteSave() throws Exception {

		Document doc = XmlTools.createDocument();
		Element element = doc.createElement("Hello");
		doc.appendChild(element);
		File tempDir = FileTools.createNewTempDir("testWriteSave");
		File tmpFile = new File(tempDir, "testWriteSave.xml");

		try {
			XmlTools.writeXml(doc, tmpFile, "UTF-8", true);
			String content = IOTools.slurp(tmpFile, "UTF-8");
			Assert.assertTrue(content.contains("<Hello"));
			
			File[] files = tempDir.listFiles();
			Assert.assertEquals(1, files.length);
			
		} finally {
			tmpFile.delete();
			tempDir.delete();
		}

	}

	@Test
	public void testWriteUnsave() throws Exception {

		Document doc = XmlTools.createDocument();
		Element element = doc.createElement("Hello");
		doc.appendChild(element);

		File tmpFile = File.createTempFile("testWriteSave", ".xml");

		try {
			XmlTools.writeXml(doc, tmpFile, "UTF-8", false);

			String content = IOTools.slurp(tmpFile, "UTF-8");

			Assert.assertTrue(content.contains("<Hello"));
		} finally {
			tmpFile.delete();
		}

	}

}
