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

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.archives.Archives;
import com.braintribe.utils.archives.ArchivesException;

public class ZipEmptyDirectoryTest {
	private File source = new File("res/empty/source");
	private File target = new File( "res/empty/target.zip");

	@Test
	public void emptyDirectoryTest() {
		if (target.exists())
			target.delete();
		
		try {
			Archives.zip().pack( source).to( target).close();
		} catch (ArchivesException e) {
			Assert.fail( String.format( "cannot pack the empty directory structure as [%s]", e));
		}
	}

}
