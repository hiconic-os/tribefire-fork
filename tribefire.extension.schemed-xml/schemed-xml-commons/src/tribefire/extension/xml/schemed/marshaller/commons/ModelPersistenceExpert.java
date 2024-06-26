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

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.exchange.ExchangePackage;
import com.braintribe.model.exchange.GenericExchangePayload;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.processing.meta.FsBasedModelArtifactBuilder;
import com.braintribe.utils.FileTools;

public class ModelPersistenceExpert {

	private static FsBasedModelArtifactBuilder artifactBuilder = new FsBasedModelArtifactBuilder();
	private static StaxMarshaller marshaller = new StaxMarshaller();

	public static File dumpModelJar(GmMetaModel model, File folder) {
		try {
			artifactBuilder.setModel(model);
			artifactBuilder.setVersionFolder(folder);
			artifactBuilder.publish();

			File[] jars = folder.listFiles((dir, name) -> isJar(name));
			return jars.length > 0 ? jars[0] : null;

		} catch (Exception e) {
			throw new IllegalStateException("cannot write model [" + model.getName() + "] as " + e);
		}
	}

	private static boolean isJar(String name) {
		if (name.endsWith("sources.jar"))
			return false;

		if (!name.endsWith("jar"))
			return false;

		return true;
	}

	public static File dumpMappingModel(GmMetaModel mappingModel, File output) {
		String fileName = mappingModel.getName().replace(':', '.') + ".model.xml";
		return dumpMappingModel(mappingModel, output, fileName);
	}

	public static File dumpMappingModel(GmMetaModel mappingModel, File output, String fileName) {
		File file = new File(output, fileName);
		return FileTools.write(file) //
				.usingOutputStream(out -> marshaller.marshall(out, mappingModel, prettyOptions()));
	}

	public static File dumpExchangePackage(File output, String exchangePackageName, List<GmType> shallowSkeletonTypes, GmMetaModel skeletonModel,
			GmMetaModel... enrichmentModels) {

		ExchangePackage exchangePackage = ExchangePackage.T.create();
		exchangePackage.setExportedBy(ModelPersistenceExpert.class.getName());
		exchangePackage.setExported(new Date());
		List<GmMetaModel> models = asList(enrichmentModels);
		models.add(skeletonModel);

		for (GmMetaModel model : models) {
			GenericExchangePayload skPayload = GenericExchangePayload.T.create();
			skPayload.setAssembly(model);
			//
			// add all shallow types of the skeleton model, as the enrichment models don't have any
			if (model == skeletonModel) {
				skPayload.setExternalReferences(newSet(shallowSkeletonTypes));
			}

			exchangePackage.getPayloads().add(skPayload);
		}

		File file = new File(output, exchangePackageName);
		return FileTools.write(file) //
				.usingOutputStream(out -> marshaller.marshall(out, exchangePackage, prettyOptions()));
	}

	private static GmSerializationOptions prettyOptions() {
		return GmSerializationOptions.deriveDefaults() //
				.outputPrettiness(OutputPrettiness.high) //
				.build();
	}

}
