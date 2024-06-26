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
package com.braintribe.utils.zip.test;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.archives.Archives;
import com.braintribe.utils.archives.ArchivesException;
import com.braintribe.utils.archives.zip.ZipContext;

public class ZipReadingTest {
	
	private File source = new File( "res/reading/source/packed.zip");

	@Test
	public void testFromFile() {
		try {
			ZipContext zC = Archives.zip().from(source);
			for (String header : zC.getHeaders()) {
				System.out.println("-> " + header);
			}
		} catch (ArchivesException e) {
			Assert.fail( String.format("cannot read per file from file [%s], exception [%s] thrown", source.getAbsolutePath(), e));
		}
	}
	

	@Test
	public void testFromFileInputStream() {
		try {
			FileInputStream inputStream = new FileInputStream( source);
			ZipContext zC = Archives.zip().from( inputStream);
			inputStream.close();
			for (String header : zC.getHeaders()) {
				System.out.println("-> " + header);
			}
		} catch (Exception e) {
			Assert.fail( String.format("cannot read per file from file [%s], exception [%s] thrown", source.getAbsolutePath(), e));
		}
	}
	
	
	
}
