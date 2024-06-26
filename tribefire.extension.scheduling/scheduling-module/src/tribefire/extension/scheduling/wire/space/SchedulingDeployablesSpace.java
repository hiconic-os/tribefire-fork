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
package tribefire.extension.scheduling.wire.space;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.scheduling.job.SchedulerJobImpl;
import tribefire.extension.scheduling.model.deployment.SchedulerJob;
import tribefire.extension.scheduling.model.deployment.SchedulingProcessor;
import tribefire.extension.scheduling.service.SchedulingServiceProcessor;
import tribefire.module.wire.contract.ModuleResourcesContract;
import tribefire.module.wire.contract.PlatformResourcesContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class SchedulingDeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ModuleResourcesContract moduleResources;

	@Import
	private PlatformResourcesContract resources;

	@Managed
	public SchedulerJobImpl cleanupScheduledJob(@SuppressWarnings("unused") ExpertContext<SchedulerJob> context) {
		SchedulerJob deployable = context.getDeployable();
		SchedulerJobImpl bean = new SchedulerJobImpl();
		bean.setSystemServiceRequestEvaluator(tfPlatform.systemUserRelated().evaluator());
		bean.setAccessId(deployable.getAccessId());
		return bean;
	}

	@Managed
	public SchedulingServiceProcessor serviceProcessor(ExpertContext<? extends SchedulingProcessor> context) {
		SchedulingProcessor deployable = context.getDeployable();
		SchedulingServiceProcessor bean = new SchedulingServiceProcessor();

		bean.setDataSessionSupplier(() -> tfPlatform.systemUserRelated().sessionFactory().newSession(deployable.getAccessId()));
		bean.setRequestEvaluator(tfPlatform.systemUserRelated().evaluator());
		bean.setLockManager(tfPlatform.locking().manager());

		return bean;
	}

}
