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
package com.braintribe.devrock.zarathud.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.devrock.zed.forensics.fingerprint.persistence.FingerPrintMarshaller;
import com.braintribe.testing.category.KnownIssue;

/**
 * unfinished for now.. 
 * @author pit
 *
 */
@Category(KnownIssue.class)

public class FingerPrintMarshallerLab {

	private File contents = new File( "res/fingerprints");
	
	public void test(File file) {
		FingerPrintMarshaller marshaller = new FingerPrintMarshaller();
		try (InputStream in = new FileInputStream(file)){
			
			Object obj = marshaller.unmarshall( in);
		}
		catch (Exception e) {
			
		}
	}
	
	@Test
	public void test() {
		File testFile = new File( contents, "simple.prints.txt");
		
	}
	

}
