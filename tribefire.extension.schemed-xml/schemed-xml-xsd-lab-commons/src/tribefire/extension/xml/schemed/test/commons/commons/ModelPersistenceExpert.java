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
package tribefire.extension.xml.schemed.test.commons.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;

import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.FsBasedModelArtifactBuilder;


public class ModelPersistenceExpert {

	private static FsBasedModelArtifactBuilder artifactBuilder = new FsBasedModelArtifactBuilder();
	private static StaxMarshaller marshaller = new StaxMarshaller();

	public static File dumpModelJar(GmMetaModel model, File folder) {
		try {
			artifactBuilder.setModel(model);
			artifactBuilder.setVersionFolder(folder);
			artifactBuilder.publish();

			for (File file : folder.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith("sources.jar"))
						return false;
					if (!name.endsWith("jar"))
						return false;

					return true;
				}
			})) {
				return file;
			}
			;

		} catch (Exception e) {
			throw new IllegalStateException("cannot write model [" + model.getName() + "] as " + e);
		}
		return null;
	}

	public static File dumpMappingModel(GmMetaModel mappingModel, File output) {
		String fileName = mappingModel.getName().replace(':', '.') + ".model.xml";
		File file = new File(output, fileName);
		try (OutputStream out = new FileOutputStream(file)) {

			marshaller.marshall(out, mappingModel, GmSerializationOptions.deriveDefaults().outputPrettiness(OutputPrettiness.high).build());
			return file;
		} catch (Exception e) {
			throw new IllegalStateException("cannot write model [" + mappingModel.getName() + "] as " + e);
		}
	}

}
