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
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.braintribe.utils.archives.Archives;
import com.braintribe.utils.archives.ArchivesException;
import com.braintribe.utils.archives.zip.ZipContext;
import com.braintribe.utils.archives.zip.ZipContextEntry;

/**
 * tests the interactive building of a zip file
 *  
 * @author pit
 *
 */
public class ZipBuildingTest {
	String base = "res/adding";
	String source = base + "/source";
	String target = base + "/target";

	enum Mode {file, stream, zipcontextentry}
	
	
	public void runAddingTest( Mode mode) {
		ZipContext zPackContext = Archives.zip();
		File sourceDirectory = new File( source);		
		File [] files = sourceDirectory.listFiles();
		for (File file : files) {
			try {
				System.out.println("adding : " + file.getAbsolutePath());
				switch (mode) {
				case file:
					zPackContext.add( file.getName(), file);
					break;
				case stream: {
					try {
						FileInputStream inputStream = new FileInputStream(file);
						zPackContext.add( file.getName(), inputStream);
						inputStream.close();
					} catch (FileNotFoundException e) {
						Assert.fail( String.format( "Exception [%s] thrown while building input stream", e.getMessage()));
					} catch (IOException e) {
						Assert.fail( String.format( "Exception [%s] thrown while closing input stream", e.getMessage()));
					}
					
					break;
				}
				case zipcontextentry: {
					String name = file.getName();
					ZipContext toAdd = Archives.zip().add(name, file);
					ZipContextEntry entry = toAdd.getEntry(name);
					zPackContext.add(entry);
					break;
				}
				
				default:
					Assert.fail( String.format( "unsupported mode [%s]", mode.name()));
					break;				
				}
				
			} catch (ArchivesException e) {
				String msg=String.format("cannot add file [%s] to zip", file.getAbsolutePath());
				Assert.fail(msg);
			}
		}
		// store 
		File zipFile = new File( base + "/zipped.zip");
		try {			
			zPackContext.to( zipFile).close();
		} catch (ArchivesException e) {
			String msg=String.format("cannot store file [%s] to zip", zipFile.getAbsolutePath());
			Assert.fail(msg);
		}
		// unpack
		
		File targetDirectory = new File( target);
		TestHelper.delete(targetDirectory);
		targetDirectory.mkdirs();
		
		try {
			ZipContext zUnpackContext = Archives.zip().from(zipFile);
			zUnpackContext.unpack(targetDirectory).close();
		} catch (ArchivesException e) {
			String msg=String.format("cannot unpack to [%s]", target);
			Assert.fail(msg);
		}
		
		if (!TestHelper.compareDirectory( sourceDirectory.toURI(), targetDirectory.toURI(), sourceDirectory, targetDirectory)) {
			Assert.fail("Result is not identical");
		}
		
	}
	
	@Test
	public void runAddPerFileTest() {
		runAddingTest( Mode.file);		
	}
	@Test
	public void runAddPerStreamTest() {
		runAddingTest( Mode.stream);		
	}
	
	@Test
	public void runAddPerEntryTest() {
		runAddingTest( Mode.zipcontextentry);		
	}
}
