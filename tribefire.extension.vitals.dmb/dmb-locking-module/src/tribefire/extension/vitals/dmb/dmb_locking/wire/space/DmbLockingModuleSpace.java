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
package tribefire.extension.vitals.dmb.dmb_locking.wire.space;

import com.braintribe.exception.Exceptions;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.model.processing.lock.api.LockingException;
import com.braintribe.model.processing.lock.dmb.impl.DmbLockManager;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.dmb.locking.model.deployment.DmbLocking;
import tribefire.module.wire.contract.ClusterBindersContract;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefirePlatformContract;

/**
 * This module's javadoc is yet be written.
 */
@Managed
public class DmbLockingModuleSpace implements TribefireModuleContract {

	@Import
	private TribefirePlatformContract tfPlatform;

	@Import
	private ClusterBindersContract clusterBinders;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(DmbLocking.T) //
				.component(clusterBinders.lockingManager()) //
				.expertFactory(this::lockManager);
	}

	private DmbLockManager lockManager(ExpertContext<DmbLocking> expertContext) {
		DmbLocking deployable = expertContext.getDeployable();

		DmbLockManager expert = null;
		try {
			expert = new DmbLockManager();
		} catch (LockingException e) {
			throw Exceptions.unchecked(e,
					"Cannot create an instance of lock manager: " + DmbLockManager.class.getName());
		}
		if (deployable.getEvictionThreshold() != null) {
			expert.setEvictionThreshold(deployable.getEvictionThreshold());
		}
		if (deployable.getEvictionInterval() != null) {
			expert.setEvictionInterval(deployable.getEvictionInterval());
		}

		return expert;
	}
}
