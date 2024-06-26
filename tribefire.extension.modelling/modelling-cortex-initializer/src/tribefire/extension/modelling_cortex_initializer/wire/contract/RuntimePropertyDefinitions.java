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
package tribefire.extension.modelling_cortex_initializer.wire.contract;

import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;


public interface RuntimePropertyDefinitions extends PropertyLookupContract {

	@Default("configuration_core-dev")
	String REPOSITORY_CONFIGURATION_NAME();
	
	@Default("https://artifactory.EXAMPLE.com/artifactory/core-dev/")
	String REPOSITORY_CONFIGURATION_URL();

	String REPOSITORY_CONFIGURATION_USER();
	
	@Decrypt
	String REPOSITORY_CONFIGURATION_PASSWORD();
	
	@Default("https://artifactory.EXAMPLE.com/Ravenhurst/rest/core-dev")
	String REPOSITORY_RAVENHURST_URL();
	
	@Default("/tribefire-explorer")
	String TRIBEFIRE_EXPLORER_URL();
	
}
