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
package com.braintribe.model.processing.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Pair;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.zargo.ZargoToMetaModelConverterWorker;
import com.braintribe.model.processing.zargo.wire.ZargoConverterModule;
import com.braintribe.model.processing.zargo.wire.contract.WorkerContract;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;

@Category(KnownIssue.class)
public class ZargoDecoderTest implements HasCommonFilesystemNode{
	
	private File input;
	private File output;	
	{
		Pair<File,File> pair = filesystemRoots("z2m");
		input = pair.first;
		output = pair.second;
	}

	
	@Before
	public void before() {
		TestUtils.ensure(output);
	}
	
	/**
	 * @param file
	 * @return
	 */
	private GmMetaModel test(File file) {
		
		try (				
				WireContext<WorkerContract> context = Wire.contextBuilder( ZargoConverterModule.INSTANCE).build();
				InputStream inputStream = new FileInputStream(file);
			) {						
				ZargoToMetaModelConverterWorker worker = context.contract().zargoToMetaModelWorker();
				GmMetaModel gmMetaModel = worker.execute(inputStream);
				System.out.println(gmMetaModel.getName());
				return gmMetaModel;
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
			return null;		
		}
	}
	
	
	@Test
	public void testVersionModel() {			
		File file = new File( input, "version-model.zargo");
		test( file);
	}
	@Test
	public void testCompiledArtifactModel() {			
		File file = new File( input, "compiled-artifact-model.zargo");
		test( file);
	}
	@Test
	public void testXmiTestModel() {			
		File file = new File( input, "xmi-test-model.zargo");
		test( file);		
	}
}
