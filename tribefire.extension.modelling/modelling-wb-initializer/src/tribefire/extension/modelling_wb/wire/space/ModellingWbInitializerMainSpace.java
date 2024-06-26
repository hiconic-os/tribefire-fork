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
package tribefire.extension.modelling_wb.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.modelling_wb.wire.contract.ExistingInstancesContract;
import tribefire.extension.modelling_wb.wire.contract.ModellingWbInitializerContract;
import tribefire.extension.modelling_wb.wire.contract.ModellingWbInitializerMainContract;

@Managed
public class ModellingWbInitializerMainSpace extends AbstractInitializerSpace implements ModellingWbInitializerMainContract {

	@Import
	private ModellingWbInitializerContract initializer;
	
	@Import
	private ExistingInstancesContract existingInstances;
	
	@Import
	private DefaultWbContract workbench;
	
	@Override
	public ModellingWbInitializerContract initializerContract() {
		return initializer;
	}

	@Override
	public ExistingInstancesContract existingInstancesContract() {
		return existingInstances;
	}
	
	@Override
	public DefaultWbContract workbenchContract() {
		return workbench;
	}
}
