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
package tribefire.extension.simple.initializer.wire.space;

import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.meta.data.constraint.NonInstantiable;
import com.braintribe.model.meta.data.prompt.Hidden;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.simple.SimpleConstants;
import tribefire.extension.simple.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.simple.initializer.wire.contract.SimpleInitializerContract;
import tribefire.extension.simple.initializer.wire.contract.SimpleInitializerModelsContract;
import tribefire.extension.simple.model.deployment.access.SimpleInMemoryAccess;
import tribefire.extension.simple.model.deployment.service.SimpleEchoService;
import tribefire.extension.simple.model.deployment.terminal.SimpleWebTerminal;

@Managed
public class SimpleInitializerSpace extends AbstractInitializerSpace implements SimpleInitializerContract {

	@Import
	private SimpleInitializerModelsContract models;
	
	@Import
	private ExistingInstancesContract existingInstances;
	
	@Import
	private CoreInstancesContract coreInstances;
	
	@Managed
	@Override
	public SimpleInMemoryAccess simpleInMemoryAccess() {
		SimpleInMemoryAccess bean = create(SimpleInMemoryAccess.T);
		
		bean.setModule(existingInstances.simpleModule());
		bean.setName("Simple In Memory Access");
		bean.setExternalId(SimpleConstants.SIMPLE_ACCESS_EXTERNALID);
		bean.setMetaModel(models.configuredSimpleDataModel());
		bean.setServiceModel(models.configuredSimpleServiceModel());
		bean.setInitializeWithExampleData(true);
		
		return bean;
	}

	@Managed
	@Override
	public SimpleEchoService simpleEchoProcessor() {
		SimpleEchoService bean = create(SimpleEchoService.T);
		bean.setExternalId(SimpleConstants.SIMPLE_SERVICE_EXTERNALID);
		
		bean.setName(SimpleEchoService.T.getShortName());
		bean.setModule(existingInstances.simpleModule());

		bean.setDelay(0l);
		bean.setEchoCount(1);
		return bean;
	}
	
	
	@Managed
	@Override
	public MetaData processWithSimpleEchoProcessor() {
		ProcessWith bean = create(ProcessWith.T);
		bean.setProcessor(simpleEchoProcessor());
		return bean;
	}
	
	@Managed
	@Override
	public SimpleWebTerminal simpleWebTerminal() {
		SimpleWebTerminal bean = create(SimpleWebTerminal.T);
		
		bean.setModule(existingInstances.simpleModule());
		bean.setExternalId(SimpleConstants.SIMPLE_WEBTERMINAL_EXTERNALID);
		bean.setName(SimpleWebTerminal.T.getShortName());
		bean.setPathIdentifier("simpleWebTerminal");
		
		// configure web terminal to show headers and parameters
		bean.setPrintHeaders(true);
		bean.setPrintParameters(true);
		
		return bean;
	}
	
	@Managed
	@Override
	public MetaData hidden() {
		Hidden bean = create(Hidden.T);
		bean.setInherited(false);
		
		return bean;
	}

	@Override
	public MetaData nonInstantiable() {
		NonInstantiable bean = create(NonInstantiable.T);
		bean.setInherited(false);
		
		return bean;
	}
}
