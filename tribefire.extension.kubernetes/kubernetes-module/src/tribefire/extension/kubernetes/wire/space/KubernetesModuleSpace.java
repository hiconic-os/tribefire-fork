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
package tribefire.extension.kubernetes.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.kubernetes.processing.KubernetesWorker;
import tribefire.module.wire.contract.HardwiredWorkersContract;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.WebPlatformReflectionContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class KubernetesModuleSpace implements TribefireModuleContract {

	@Import
	private HardwiredWorkersContract hardwiredWorkers;

	@Import
	private WebPlatformReflectionContract platformReflection;

	@Override
	public void bindHardwired() {
		hardwiredWorkers.bindWorker("kubernetes-availability-watchdog", "Kubernetes Availability Watchdog", hardwiredWorker());
	}

	private KubernetesWorker hardwiredWorker() {
		KubernetesWorker worker = new KubernetesWorker();
		worker.setEnvironmentDenotations(platformReflection.environmentDenotations());
		return worker;
	}

}
