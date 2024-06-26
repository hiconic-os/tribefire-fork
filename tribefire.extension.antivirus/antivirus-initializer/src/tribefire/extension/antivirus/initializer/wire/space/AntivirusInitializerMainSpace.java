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
package tribefire.extension.antivirus.initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.antivirus.initializer.wire.contract.AntivirusInitializerContract;
import tribefire.extension.antivirus.initializer.wire.contract.AntivirusInitializerMainContract;
import tribefire.extension.antivirus.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.antivirus.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.extension.antivirus.templates.wire.contract.AntivirusTemplatesContract;

@Managed
public class AntivirusInitializerMainSpace implements AntivirusInitializerMainContract {

	@Import
	private AntivirusInitializerContract initializer;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private AntivirusTemplatesContract antivirusTemplate;
	
	@Import
	private RuntimePropertiesContract properties;

	@Override
	public AntivirusInitializerContract initializer() {
		return initializer;
	}

	@Override
	public ExistingInstancesContract existingInstances() {
		return existingInstances;
	}

	@Override
	public CoreInstancesContract coreInstances() {
		return coreInstances;
	}

	@Override
	public AntivirusTemplatesContract antivirusTemplate() {
		return antivirusTemplate;
	}

	@Override
	public RuntimePropertiesContract properties() {
		return properties;
	}
}
