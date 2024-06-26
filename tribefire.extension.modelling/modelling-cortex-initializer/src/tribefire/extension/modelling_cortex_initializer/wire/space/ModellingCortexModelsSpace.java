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
package tribefire.extension.modelling_cortex_initializer.wire.space;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.modelling.commons.ModellingConstants;
import tribefire.extension.modelling_cortex_initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.modelling_cortex_initializer.wire.contract.ModellingCortexModelsContract;

@Managed
public class ModellingCortexModelsSpace extends AbstractInitializerSpace implements ModellingCortexModelsContract, ModellingConstants {

	@Import
	private ExistingInstancesContract existingInstances;
	
	@Import
	private CoreInstancesContract coreInstances;
	
	@Managed
	@Override
	public GmMetaModel managementWbModel() {
		GmMetaModel bean = create(GmMetaModel.T);		
		
		bean.setName(NAME_MANAGEMENT_WB_MODEL);
		
		bean.getDependencies().add(existingInstances.managementModel());		
		bean.getDependencies().add(existingInstances.managementApiModel());		
		
		bean.getDependencies().add(coreInstances.workbenchModel());
		
		return bean;
	}
	
	@Managed
	@Override
	public GmMetaModel projectWbModel() {
		GmMetaModel bean = create(GmMetaModel.T);		
		
		bean.setName(NAME_MODELLING_WB_MODEL);
		
		bean.getDependencies().add(existingInstances.modellingApiModel());		
		
		bean.getDependencies().add(coreInstances.workbenchModel());
		
		return bean;
	}
	
	@Managed
	@Override
	public GmMetaModel projectModel() {
		GmMetaModel bean = create(GmMetaModel.T);		
		
		bean.setName(NAME_MODELLING_MODEL);
		bean.getDependencies().add(existingInstances.modellingProjectModel());
		
		return bean;
	}
}
