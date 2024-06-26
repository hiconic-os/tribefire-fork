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
package tribefire.cortex.module.loading;

import com.braintribe.model.processing.meta.configuration.ConfigurationModelBuilderManagedImpl;
import com.braintribe.model.processing.meta.configured.ConfigurationModelBuilder;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;

import tribefire.module.api.ConfigurationModelFactory;

/**
 * @author peter.gazdik
 */
/* package */ class SessionBasedConfigurationModelFactory implements ConfigurationModelFactory {

	private final ManagedGmSession session;

	public SessionBasedConfigurationModelFactory(ManagedGmSession session) {
		this.session = session;
	}

	@Override
	public ConfigurationModelBuilder create(String groupId, String artifactId, String version) {
		return create(groupId + ":" + artifactId, version);
	}

	@Override
	public ConfigurationModelBuilder create(String name, String version) {
		return new ConfigurationModelBuilderManagedImpl(session, name, version);
	}

}
