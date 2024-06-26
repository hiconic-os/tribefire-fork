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
package tribefire.extension.opentracing.initializer.wire.space;

import com.braintribe.model.extensiondeployment.meta.AroundProcessWith;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.opentracing.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.opentracing.initializer.wire.contract.OpentracingInitializerContract;
import tribefire.extension.opentracing.initializer.wire.contract.OpentracingInitializerModelsContract;
import tribefire.extension.opentracing.model.deployment.service.OpentracingAspect;
import tribefire.extension.opentracing.model.deployment.service.OpentracingServiceProcessor;
import tribefire.extension.opentracing.model.service.OpentracingRequest;

@Managed
public class OpentracingInitializerSpace extends AbstractInitializerSpace implements OpentracingInitializerContract {

	@Import
	private OpentracingInitializerModelsContract models;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Override
	public void setupDefaultConfiguration() {
		// TODO Auto-generated method stub

		processWithGenericOpentracingServiceExecutionRequest();
		aroundProcessWithOpentracingAspect();

		// -----------------------

		BasicModelMetaDataEditor serviceModelEditor = new BasicModelMetaDataEditor(existingInstances.serviceModel());
		BasicModelMetaDataEditor deploymentModelEditor = new BasicModelMetaDataEditor(existingInstances.deploymentModel());

		serviceModelEditor.onEntityType(OpentracingRequest.T).addMetaData(processWithGenericOpentracingServiceExecutionRequest());
		serviceModelEditor.onEntityType(OpentracingRequest.T).addMetaData(aroundProcessWithOpentracingAspect());

	}

	@Managed
	@Override
	public OpentracingServiceProcessor opentracingServiceProcessor() {
		OpentracingServiceProcessor bean = create(OpentracingServiceProcessor.T);
		bean.setModule(existingInstances.module());
		bean.setAutoDeploy(true);
		bean.setName("OpentracingServiceProcessor");
		bean.setExternalId("opentracing.service.processor");

		return bean;
	}

	@Managed
	@Override
	public OpentracingAspect opentracingAspect() {
		OpentracingAspect bean = create(OpentracingAspect.T);
		bean.setModule(existingInstances.module());
		bean.setAutoDeploy(true);
		bean.setName("OpentracingAspect");
		bean.setExternalId("opentracing.aspect");

		return bean;
	}

	// -----------------------------------------------------------------------
	// META DATA - PROCESS WITH
	// -----------------------------------------------------------------------

	@Managed
	public ProcessWith processWithGenericOpentracingServiceExecutionRequest() {
		ProcessWith bean = create(ProcessWith.T);
		OpentracingServiceProcessor opentracingServiceProcessor = opentracingServiceProcessor();
		bean.setProcessor(opentracingServiceProcessor);
		return bean;
	}

	@Managed
	public AroundProcessWith aroundProcessWithOpentracingAspect() {
		AroundProcessWith bean = create(AroundProcessWith.T);
		OpentracingAspect opentracingAspect = opentracingAspect();
		bean.setProcessor(opentracingAspect);
		return bean;
	}
}
