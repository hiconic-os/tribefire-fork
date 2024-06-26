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
package tribefire.extension.xml.schemed.xml.marshaller.test.include;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;

import com.braintribe.logging.Logger;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

import tribefire.extension.xml.schemed.test.commons.xsd.test.util.TestUtil;

@Category(KnownIssue.class)
public class IncludeLab {
	private static final File contents = new File("res/include");
	private static final File input = new File (contents, "input");
	private static final File output = new File (contents, "output");
	private static final String XML = "including.xml";
	private static Logger log = Logger.getLogger(IncludeLab.class);
	
	@Before
	public void before() {
		TestUtil.ensure(output);;
	}
	
	//@Test
	public void test() {
		File inputFile = new File( input, XML);
		Document doc;
		try {
			doc = DomParser.load().setIncludeAware().setNamespaceAware().from( inputFile);
		} catch (DomParserException e) {
			throw new IllegalStateException("cannot read file [" + inputFile + "]");
		}
		
		File outputFile = new File( output, XML);
		try {
			DomParser.write().from(doc).to( outputFile);
		} catch (DomParserException e) {
			throw new IllegalStateException( "cannot write file [" + outputFile + "]");
		}
	}

}
