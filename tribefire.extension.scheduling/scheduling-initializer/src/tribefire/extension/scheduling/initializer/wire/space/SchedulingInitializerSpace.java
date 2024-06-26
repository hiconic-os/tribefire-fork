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
package tribefire.extension.scheduling.initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.scheduling.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.scheduling.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.extension.scheduling.initializer.wire.contract.SchedulingInitializerContract;
import tribefire.extension.scheduling.initializer.wire.contract.SchedulingInitializerModelsContract;
import tribefire.extension.scheduling.templates.api.SchedulingTemplateContext;
import tribefire.extension.scheduling.templates.wire.contract.SchedulingTemplatesContract;

@Managed
public class SchedulingInitializerSpace extends AbstractInitializerSpace implements SchedulingInitializerContract {

	private static final String GLOBAL_ID_SCHEDULING_DB_CONNECTION_POOL = "scheduling-db-connection-pool";

	@Import
	private SchedulingInitializerModelsContract models;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private RuntimePropertiesContract runtime;

	@Import
	private SchedulingTemplatesContract templates;

	@Override
	public void configure() {
		if (runtime.SCHEDULING_DEFAULT_CONFIG()) {
			SchedulingTemplateContext context = defaultContext();
			templates.configure(context);
		}
	}

	@Managed
	@Override
	public SchedulingTemplateContext defaultContext() {

		//@formatter:off
		SchedulingTemplateContext context = SchedulingTemplateContext.builder()
			.setDatabaseConnectionGlobalId(GLOBAL_ID_SCHEDULING_DB_CONNECTION_POOL)
			.setDatabaseUrl(runtime.SCHEDULING_DB_DATA_URL())
			.setDatabaseUser(runtime.SCHEDULING_DB_DATA_USER())
			.setDatabasePassword(runtime.SCHEDULING_DB_DATA_PASSWORD_ENCRYPTED())
			.setModule(existingInstances.module())
			.setName("Scheduling")
			.setIdPrefix("scheduling")
			.setEntityFactory(super::create)
			.setLookupFunction(super::lookup)
			.setLookupExternalIdFunction(super::lookupExternalId)
			.build();
		//@formatter:on
		return context;
	}
}
