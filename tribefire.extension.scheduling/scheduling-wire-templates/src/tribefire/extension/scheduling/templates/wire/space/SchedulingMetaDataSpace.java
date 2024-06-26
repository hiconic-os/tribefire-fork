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
package tribefire.extension.scheduling.templates.wire.space;

import com.braintribe.logging.Logger;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.scheduling.model.api.SchedulingRequest;
import tribefire.extension.scheduling.templates.api.SchedulingTemplateContext;
import tribefire.extension.scheduling.templates.wire.contract.ExistingInstancesContract;
import tribefire.extension.scheduling.templates.wire.contract.SchedulingMetaDataContract;
import tribefire.extension.scheduling.templates.wire.contract.SchedulingModelsContract;
import tribefire.extension.scheduling.templates.wire.contract.SchedulingTemplatesContract;

@Managed
public class SchedulingMetaDataSpace implements WireSpace, SchedulingMetaDataContract {

	private static final Logger logger = Logger.getLogger(SchedulingMetaDataSpace.class);

	@Import
	private SchedulingModelsContract models;

	@Import
	private SchedulingTemplatesContract templates;

	@Import
	private ExistingInstancesContract existing;

	@Override
	public void configureMetaData(SchedulingTemplateContext context) {

		BasicModelMetaDataEditor editor = new BasicModelMetaDataEditor(models.configuredSchedulingApiModel());
		editor.onEntityType(SchedulingRequest.T).addMetaData(templates.processWithSchedulingServiceProcessor(context));

		/* editor.onEntityType(CommunicationError.T).addMetaData(initializer.httpStatus502Md(), initializer.logReasonTrace());
		 * editor.onEntityType(NotFound.T).addMetaData(initializer.httpStatus404Md(), initializer.logReasonTrace());
		 * editor.onEntityType(InternalError.T).addMetaData(initializer.httpStatus500Md(), initializer.logReasonTrace());
		 * editor.onEntityType(ConfigurationMissing.T).addMetaData(initializer.httpStatus501Md(),
		 * initializer.logReasonTrace()); */
	}
}
