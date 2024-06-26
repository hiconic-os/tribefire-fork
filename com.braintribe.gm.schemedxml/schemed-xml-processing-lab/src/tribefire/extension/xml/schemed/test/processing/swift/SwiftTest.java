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
package tribefire.extension.xml.schemed.test.processing.swift;



import java.io.File;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.test.processing.AbstractTest;


public class SwiftTest extends AbstractTest {
	private static File basic = new File( contents, "swift");
	private static File input = new File( basic, "input");
	private static File output = new File( basic, "output");
	
	@BeforeClass
	public static void before() {
		before( output);
	}
	
	@Test
	public void test_pain() {
		
		try {
			runTest( input, output, "pain.001.001.03.ISO.xsd", "com.braintribe.xml.swift.pain", "com.braintribe.xml.test.swift:pain#1.0");
		} catch (Exception e) {
			Assert.fail("exception [" + e + "] thrown");
		}
	}

	
	@Test
	public void test_pacs() {
		
		try {
			runTest( input, output, "pacs.008.001.01.ISO.xsd", "com.braintribe.xml.swift.pacs", "com.braintribe.xml.test.swift:pacs#1.0");
		} catch (Exception e) {
			Assert.fail("exception [" + e + "] thrown");
		}
	}

}
