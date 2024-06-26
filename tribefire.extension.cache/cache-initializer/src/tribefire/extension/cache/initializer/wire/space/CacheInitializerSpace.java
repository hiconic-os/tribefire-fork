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
package tribefire.extension.cache.initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.cache.initializer.wire.contract.CacheInitializerContract;
import tribefire.extension.cache.initializer.wire.contract.CacheInitializerModelsContract;
import tribefire.extension.cache.initializer.wire.contract.ExistingInstancesContract;

@Managed
public class CacheInitializerSpace extends AbstractInitializerSpace implements CacheInitializerContract {

	@Import
	private CacheInitializerModelsContract models;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Override
	public void setupDefaultConfiguration() {
		// BasicModelMetaDataEditor serviceModelEditor = new BasicModelMetaDataEditor(existingInstances.serviceModel());

	}

	// -----------------------------------------------------------------------
	// PROCESSOR
	// -----------------------------------------------------------------------

	// 'CacheAspectAdminServiceProcessor' will be created by the user

	// 'CacheDemoProcessor' will be created by the user

	// -----------------------------------------------------------------------
	// ASPECT
	// -----------------------------------------------------------------------

	// 'CacheAspect' will be created by the user

	// -----------------------------------------------------------------------
	// META DATA - PROCESS WITH
	// -----------------------------------------------------------------------

	// will be created by the user

}
