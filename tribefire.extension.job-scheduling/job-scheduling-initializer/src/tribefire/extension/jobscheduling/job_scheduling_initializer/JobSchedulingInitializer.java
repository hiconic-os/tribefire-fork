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
package tribefire.extension.jobscheduling.job_scheduling_initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.jobscheduling.job_scheduling_initializer.wire.JobSchedulingInitializerWireModule;
import tribefire.extension.jobscheduling.job_scheduling_initializer.wire.contract.JobSchedulingInitializerContract;

public class JobSchedulingInitializer extends AbstractInitializer<JobSchedulingInitializerContract> {

	@Override
	public WireTerminalModule<JobSchedulingInitializerContract> getInitializerWireModule() {
		return JobSchedulingInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<JobSchedulingInitializerContract> initializerContext,
			JobSchedulingInitializerContract initializerMainContract) {

		GmMetaModel configuredPlatformApiModel = initializerMainContract.configuredPlatformApiModel();
		GmMetaModel jobApiModel = initializerMainContract.jobApiModel();

		configuredPlatformApiModel.getDependencies().add(jobApiModel);
	}
}
