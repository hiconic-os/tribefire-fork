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
package tribefire.extension.antivirus.templates.wire.space;

import com.braintribe.logging.Logger;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.antivirus.AntivirusConstants;
import tribefire.extension.antivirus.model.deployment.service.AntivirusProcessor;
import tribefire.extension.antivirus.model.service.request.AntivirusRequest;
import tribefire.extension.antivirus.templates.api.AntivirusTemplateContext;
import tribefire.extension.antivirus.templates.util.AntivirusTemplateUtil;
import tribefire.extension.antivirus.templates.wire.contract.AntivirusMetaDataContract;
import tribefire.extension.antivirus.templates.wire.contract.AntivirusTemplatesContract;

@Managed
public class AntivirusMetaDataSpace implements WireSpace, AntivirusMetaDataContract {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(AntivirusMetaDataSpace.class);

	@Import
	private AntivirusTemplatesContract antivirusTemplates;

	@Override
	@Managed
	public GmMetaModel serviceModel(AntivirusTemplateContext context) {
		GmMetaModel model = context.create(GmMetaModel.T, InstanceConfiguration.currentInstance());
		GmMetaModel rawServiceModel = (GmMetaModel) context.lookup("model:" + AntivirusConstants.SERVICE_MODEL_QUALIFIEDNAME);
		setModelDetails(model, AntivirusTemplateUtil.resolveServiceModelName(context), rawServiceModel);
		return model;
	}

	@Override
	public void metaData(AntivirusTemplateContext context) {
		// -----------------------------------------------------------------------
		// DATA MODEL
		// -----------------------------------------------------------------------

		// nothing

		// -----------------------------------------------------------------------
		// SERVICE MODEL
		// -----------------------------------------------------------------------

		GmMetaModel serviceModel = serviceModel(context);
		BasicModelMetaDataEditor serviceModelEditor = new BasicModelMetaDataEditor(serviceModel);

		serviceModelEditor.onEntityType(AntivirusRequest.T).addMetaData(processWithAntivirusRequest(context));
	}

	// --------------------------–

	// -----------------------------------------------------------------------
	// META DATA - PROCESS WITH
	// -----------------------------------------------------------------------

	@Managed
	@Override
	public ProcessWith processWithAntivirusRequest(AntivirusTemplateContext context) {
		ProcessWith bean = context.create(ProcessWith.T, InstanceConfiguration.currentInstance());

		AntivirusProcessor antivirusProcessor = antivirusTemplates.antivirusServiceProcessor(context);
		bean.setProcessor(antivirusProcessor);
		return bean;
	}

	// -----------------------------------------------------------------------
	// HELPER METHODS
	// -----------------------------------------------------------------------

	private static void setModelDetails(GmMetaModel targetModel, String modelName, GmMetaModel... dependencies) {
		targetModel.setName(modelName);
		targetModel.setVersion(AntivirusConstants.MAJOR_VERSION + ".0");
		if (dependencies != null) {
			for (GmMetaModel dependency : dependencies) {
				if (dependency != null) {
					targetModel.getDependencies().add(dependency);
				}
			}
		}
	}
}
