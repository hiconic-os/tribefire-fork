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

import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.common.lcd.Pair;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.xmi.converter.coding.differentiator.ModelDifferentiator;
import com.braintribe.model.processing.xmi.converter.coding.differentiator.ModelDifferentiatorContext;
import com.braintribe.testing.category.KnownIssue;
@Category(KnownIssue.class)
public class ModelDifferentiatorTest implements HasCommonFilesystemNode{
	
	private File input;
	private File output;
	
	{
		Pair<File,File> pair = filesystemRoots("diff");
		input = pair.first;
		output = pair.second;
	}
	
	
	private YamlMarshaller marshaller = new YamlMarshaller();
	
	@Before
	public void before() {
		TestUtils.ensure(output);
	}
	
	private GmMetaModel loadModel( File file) {
		try ( InputStream in = new FileInputStream( file)) {
			return (GmMetaModel) marshaller.unmarshall( in);
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail("cannot load model from [" + file.getAbsolutePath() + "]");
		}
		return null;
	}

	private ModelDifferentiatorContext runDiff( String modelName) {
		File baseModelFile = new File( input, modelName + ".yaml");
		File touchedModelFile = new File( input, modelName + ".touched.yaml");
		
		GmMetaModel baseModel = loadModel(baseModelFile);
		GmMetaModel touchedModel = loadModel( touchedModelFile);
		ModelDifferentiatorContext context = new ModelDifferentiatorContext();
		
		ModelDifferentiator.differentiate(context, touchedModel, baseModel);
		
		return context;
	}
	
	@Test
	public void testVersionModel() {
		ModelDifferentiatorContext context = runDiff( "version-model");
		String o = context.asString();
		System.out.println( o);
	}

}
