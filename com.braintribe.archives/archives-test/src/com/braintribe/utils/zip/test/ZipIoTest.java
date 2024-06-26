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
import com.braintribe.utils.archives.zip.ZipContext;

/**
 * tests the packing / unpacking of directories and files 
 *  
 * @author pit
 *
 */
public class ZipIoTest {

	private File parent = new File("res/pack-unpack");
	
	
	public void packTest() {
		ZipContext zContext = Archives.zip();
		try {
			File source = new File( parent, "source");
			File target = new File( parent, "packed.zip");
			if (target.exists()) {
				target.delete();
			}
			zContext.pack( source).to(target).close();
		} catch (ArchivesException e) {
			String msg = String.format("Exception [%s] thrown", e);
			Assert.fail( msg);
		}		
	}
	
	
	
	public void unpackTest() {
		ZipContext zContext = Archives.zip();
		try {
			
			File source = new File( parent, "packed.zip");
			File target = new File( parent, "target");
			if (target.exists()) {
				TestHelper.delete( target);
			}			
			target.mkdirs();
			zContext.from( source).unpack(target).close();
		} catch (ArchivesException e) {
			String msg = String.format("Exception [%s] thrown", e);
			Assert.fail( msg);
		}		
	}

	
	@Test
	public void combinedTest() {
		packTest();
		unpackTest();
		File source = new File( parent, "source");
		File target = new File( parent, "target");
		if (!TestHelper.compareDirectory( source.toURI(), target.toURI(), source, target)) {
			Assert.fail("Result is not identical");
		}
	}

}
