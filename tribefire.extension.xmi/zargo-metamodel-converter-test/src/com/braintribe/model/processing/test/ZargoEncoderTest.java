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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.common.lcd.Pair;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.Model;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.zargo.MetaModelToZargoConverterWorker;
import com.braintribe.model.processing.zargo.wire.ZargoConverterModule;
import com.braintribe.model.processing.zargo.wire.contract.WorkerContract;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.IOTools;
import com.braintribe.wire.api.Wire;
import com.braintribe.wire.api.context.WireContext;

@Category(KnownIssue.class)
public class ZargoEncoderTest implements HasCommonFilesystemNode {
	
	private File input;
	private File output;
	
	{
		Pair<File,File> pair = filesystemRoots("m2z");
		input = pair.first;
		output = pair.second;
	}
	
	private GenericModelTypeReflection reflection = GMF.getTypeReflection();
	
	@Before
	public void before() {
		TestUtils.ensure(output);
	}
	
	private void test(String name, File out, File in) {
		
		Model model = reflection.getModel(name);
		if (model == null) {
			Assert.fail("no model found named [" + name + "]");
			return;
		}
		InputStream inputStream = null;
		if (in != null) {
			try {
				inputStream = new FileInputStream( in);
			} catch (FileNotFoundException e) {
				Assert.fail("file [" + in.getAbsolutePath() + "] for priming doesn't exist");
				return;
			}
		}
		
		GmMetaModel metaModel = model.getMetaModel();
		try (				
				WireContext<WorkerContract> context = Wire.contextBuilder( ZargoConverterModule.INSTANCE).build();
				OutputStream outputStream = new FileOutputStream(out);				
			) {						
				MetaModelToZargoConverterWorker worker = context.contract().metaModelToZargoWorker();
				worker.execute(metaModel, outputStream, inputStream);				
		}
		catch( Exception e) {
			e.printStackTrace();
			Assert.fail("exception thrown [" + e.getLocalizedMessage() + "]");		
		}
		finally {
			if (inputStream != null) {
				IOTools.closeQuietly(inputStream);
			}
		}
	}
	
	
	@Test
	public void testCleanVersionModel() {			
		File file = new File( output, "version-model.zargo");
		test( "com.braintribe.gm:version-model", file, null);
	}
	@Test
	public void testCleanCompiledArtifactModel() {			
		File file = new File( output, "compiled-artifact-model.zargo");
		test( "com.braintribe.devrock:compiled-artifact-model", file, null);
	}
	
	@Test
	public void testPrimedVersionModel() {			
		File file = new File( output, "version-model.zargo");
		File primer = new File( input, "version-model.zargo");
		test( "com.braintribe.gm:version-model", file, primer);
	}
	@Test
	public void testPrimedCompiledArtifactModel() {			
		File file = new File( output, "compiled-artifact-model.zargo");
		File primer = new File( input, "compiled-artifact-model.zargo");
		test( "com.braintribe.devrock:compiled-artifact-model", file, primer);
	}
	
	@Test
	public void testCleanModelWithMetadata() {
		File file = new File( output, "xmi-test-model.zargo");
		test( "tribefire.extension.xmi:xmi-test-model", file, null);
	}
	@Test
	public void testPrimedModelWithMetadata() {
		File file = new File( output, "xmi-test-model.zargo");
		File primer = new File( input, "xmi-test-model.zargo");
		test( "tribefire.extension.xmi:xmi-test-model", file, primer);
	}
}
