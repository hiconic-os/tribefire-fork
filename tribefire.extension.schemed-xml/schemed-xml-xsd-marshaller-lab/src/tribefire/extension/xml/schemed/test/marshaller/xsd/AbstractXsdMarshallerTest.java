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
package tribefire.extension.xml.schemed.test.marshaller.xsd;

import java.io.File;

import javax.xml.stream.XMLStreamException;

import org.junit.Assert;
import org.junit.experimental.categories.Category;
import org.xmlunit.diff.Diff;

import com.braintribe.testing.category.KnownIssue;

import tribefire.extension.xml.schemed.marshaller.xsd.SchemedXmlXsdMarshaller;
import tribefire.extension.xml.schemed.model.xsd.Schema;


@Category(KnownIssue.class)
public class AbstractXsdMarshallerTest  {
	protected static File contents = new File("res");
	protected File parentDirectory;
	
	private SchemedXmlXsdMarshaller marshaller = new SchemedXmlXsdMarshaller();
	
	public static void delete( File file) {
		if (file == null || file.exists() == false)
			return;
		for (File child : file.listFiles()) {
			if (child.isDirectory()) {
				delete( child);
			} 
			child.delete();			
		}
	}

	public static void ensure(String checkdir) {
		File output = new File(checkdir);
		if (output.exists())
			delete( output);
		output.mkdirs();
	}
	public static void ensure(File output) {	
		if (output.exists())
			delete( output);
		output.mkdirs();
	}
	

	protected Schema readFile( File file) {
		try {
			return marshaller.unmarshall(file);
		} catch (XMLStreamException e) {
			e.printStackTrace();
			Assert.fail("cannot unmarshall [" + file.getAbsolutePath() + "] as " + e);
		}
		return null;
	}
	
	protected void writeFile( File file, Schema schema) {
		try {
			marshaller.marshall(file, schema);
		} catch (XMLStreamException e) {
			e.printStackTrace();
			Assert.fail("cannot unmarshall [" + file.getAbsolutePath() + "] as " + e);
		}
	}
	
	protected void compare( File source, File target) {
		XsdComparator comparator = new XsdComparator();
		Diff myDiff = comparator.compare(source, target);
		Assert.assertTrue("XML isn't matching " + myDiff.toString(), !myDiff.hasDifferences());
	}
}
