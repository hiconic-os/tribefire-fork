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
package tribefire.extension.xml.schemed.marshaller.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.exchange.ExchangePackage;
import com.braintribe.model.exchange.GenericExchangePayload;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.processing.meta.ModelArtifactBuilder;
import com.braintribe.model.processing.meta.ModelArtifactBuilderException;

public class ModelPersistenceExpert {

	private static ModelArtifactBuilder artifactBuilder = new ModelArtifactBuilder();
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

		} catch (ModelArtifactBuilderException e) {
			throw new IllegalStateException("cannot write model [" + model.getName() + "] as " + e);
		}
		return null;
	}

	public static File dumpMappingModel(GmMetaModel mappingModel, File output) {
		String fileName = mappingModel.getName().replace(':', '.') + ".model.xml";
		return dumpMappingModel(mappingModel, output, fileName);
	}
	public static File dumpMappingModel(GmMetaModel mappingModel, File output, String fileName) {
		File file = new File(output, fileName);
		try (OutputStream out = new FileOutputStream(file)) {

			marshaller.marshall(out, mappingModel, GmSerializationOptions.deriveDefaults().outputPrettiness(OutputPrettiness.high).build());
			return file;
		} catch (Exception e) {
			throw new IllegalStateException("cannot write model [" + mappingModel.getName() + "] as " + e);
		}
	}

	public static File dumpExchangePackage(File output, String exchangePackageName, List<GmType> shallowSkeletonTypes, GmMetaModel skeletonModel,
			GmMetaModel... enrichmentModels) {

		ExchangePackage exchangePackage = ExchangePackage.T.create();
		exchangePackage.setExportedBy(ModelPersistenceExpert.class.getName());
		exchangePackage.setExported(new Date());
		List<GmMetaModel> models = new ArrayList<>(Arrays.asList(enrichmentModels));
		models.add(skeletonModel);

		for (GmMetaModel model : models) {
			GenericExchangePayload skPayload = GenericExchangePayload.T.create();
			skPayload.setAssembly(model);
			//
			// add all shallow types of the skeleton model, as the enrichment models don't have any
			if (model == skeletonModel) {
				skPayload.setExternalReferences(new HashSet<>(shallowSkeletonTypes));
			}

			exchangePackage.getPayloads().add(skPayload);
		}

		File file = new File(output, exchangePackageName);
		try (OutputStream out = new FileOutputStream(file)) {

			marshaller.marshall(out, exchangePackage, GmSerializationOptions.deriveDefaults().outputPrettiness(OutputPrettiness.high).build());
			return file;
		} catch (Exception e) {
			throw new IllegalStateException("cannot write exchange package to [" + file.getAbsolutePath() + "] as " + e);
		}

	}

}
