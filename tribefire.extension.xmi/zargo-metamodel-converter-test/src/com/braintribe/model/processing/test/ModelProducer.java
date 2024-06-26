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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.codec.marshaller.yaml.YamlMarshaller;
import com.braintribe.common.lcd.Pair;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.Model;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.testing.category.KnownIssue;
@Category(KnownIssue.class)
public class ModelProducer implements HasCommonFilesystemNode{	
	private File input;
	private File output;
	
	{
		Pair<File,File> pair = filesystemRoots("models");
		input = pair.first;
		output = pair.second;
	}
	
	private GenericModelTypeReflection reflection = GMF.getTypeReflection();
	private YamlMarshaller marshaller = new YamlMarshaller();
	
	
	private void produceModelYaml(String name, File file) {
		Model model = reflection.getModel(name);
		if (model == null) {
			Assert.fail("no model found named [" + name + "]");
			return;
		}
		GmMetaModel metaModel = model.getMetaModel();
		
		try ( OutputStream out = new FileOutputStream( file)) {
			marshaller.marshall(out, metaModel);
		}
		catch (IOException e) {
			Assert.fail("cannot marshall model [" + name + "] to file [" + file.getAbsolutePath() + "]");
		}		
	}
	
	@Test
	public void versionModel() {
		produceModelYaml("com.braintribe.gm:version-model", new File( output, "version-model.yaml"));
	}
	@Test
	public void compiledArtifactModel() {
		produceModelYaml("com.braintribe.devrock:compiled-artifact-model", new File( output, "compiled-artifact-model.yaml"));
	}
	
	

}
