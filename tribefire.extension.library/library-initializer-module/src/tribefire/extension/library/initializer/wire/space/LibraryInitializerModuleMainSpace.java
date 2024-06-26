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
package tribefire.extension.library.initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.job_scheduling.processing.QuartzScheduling;
import tribefire.extension.library.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.library.initializer.wire.contract.LibraryInitializerModuleContract;
import tribefire.extension.library.initializer.wire.contract.LibraryInitializerModuleMainContract;
import tribefire.extension.library.initializer.wire.contract.LibraryInitializerModuleModelsContract;
import tribefire.extension.library.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.module.wire.contract.ModelApiContract;

@Managed
public class LibraryInitializerModuleMainSpace implements LibraryInitializerModuleMainContract {

	@Import
	private LibraryInitializerModuleContract initializer;

	@Import
	private LibraryInitializerModuleModelsContract models;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private RuntimePropertiesContract properties;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private ModelApiContract modelApi;

	@Override
	public LibraryInitializerModuleContract initializer() {
		return initializer;
	}

	@Override
	public LibraryInitializerModuleModelsContract models() {
		return models;
	}

	@Override
	public ExistingInstancesContract existingInstances() {
		return existingInstances;
	}

	@Override
	public RuntimePropertiesContract properties() {
		return properties;
	}

	@Override
	public CoreInstancesContract coreInstances() {
		return coreInstances;
	}

	@Override
	public ModelApiContract modelApi() {
		return modelApi;
	}
}
