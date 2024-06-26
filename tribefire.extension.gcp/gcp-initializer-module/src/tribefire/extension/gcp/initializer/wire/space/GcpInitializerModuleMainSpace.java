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
package tribefire.extension.gcp.initializer.wire.space;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.gcp.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.gcp.initializer.wire.contract.GcpInitializerModuleContract;
import tribefire.extension.gcp.initializer.wire.contract.GcpInitializerModuleMainContract;
import tribefire.extension.gcp.initializer.wire.contract.GcpInitializerModuleModelsContract;
import tribefire.extension.gcp.initializer.wire.contract.RuntimePropertiesContract;

/**
 * @see GcpInitializerModuleMainContract
 */
@Managed
public class GcpInitializerModuleMainSpace extends AbstractInitializerSpace implements GcpInitializerModuleMainContract {

	@Import
	private GcpInitializerModuleContract initializer;
	
	@Import
	private GcpInitializerModuleModelsContract models;
	
	@Import
	private ExistingInstancesContract existingInstances;
	
	@Import
	private CoreInstancesContract coreInstances;
	
	@Import
	private RuntimePropertiesContract properties;
	
	@Override
	public GcpInitializerModuleContract initializerContract() {
		return initializer;
	}

	@Override
	public GcpInitializerModuleModelsContract initializerModelsContract() {
		return models;
	}

	@Override
	public ExistingInstancesContract existingInstancesContract() {
		return existingInstances;
	}
	
	@Override
	public CoreInstancesContract coreInstancesContract() {
		return coreInstances;
	}

	@Override
	public RuntimePropertiesContract propertiesContract() {
		return properties;
	}
}
