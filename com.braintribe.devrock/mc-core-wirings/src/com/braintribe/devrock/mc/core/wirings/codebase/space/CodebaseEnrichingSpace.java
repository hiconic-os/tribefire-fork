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
package com.braintribe.devrock.mc.core.wirings.codebase.space;

import com.braintribe.devrock.mc.core.resolver.codebase.RepositoryConfigurationCodebaseEnricher;
import com.braintribe.devrock.mc.core.wirings.codebase.contract.CodebaseEnrichingConfigurationContract;
import com.braintribe.devrock.mc.core.wirings.resolver.contract.RepositoryConfigurationEnrichingContract;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContextConfiguration;
import com.braintribe.wire.api.space.WireSpace;

/**
 * @author pit / dirk
 *
 */
@Managed
public class CodebaseEnrichingSpace implements WireSpace {
	
	@Import
	private RepositoryConfigurationEnrichingContract repositoryConfigurationEnriching;
	
	@Import
	private CodebaseEnrichingConfigurationContract codebaseEnrichingConfiguration;
	
	@Override
	public void onLoaded(WireContextConfiguration configuration) {
		repositoryConfigurationEnriching.enrichRepositoryConfiguration(this::repositoryConfigurationEnricher);
	}
	
	/**
	 * @return - instrumented {@link RepositoryConfigurationCodebaseEnricher}
	 */
	@Managed
	private RepositoryConfigurationCodebaseEnricher repositoryConfigurationEnricher() {
		RepositoryConfigurationCodebaseEnricher bean = new RepositoryConfigurationCodebaseEnricher();
		bean.setCodebaseRepositories(codebaseEnrichingConfiguration.codebaseRepositories());
		return bean;
	}
}
