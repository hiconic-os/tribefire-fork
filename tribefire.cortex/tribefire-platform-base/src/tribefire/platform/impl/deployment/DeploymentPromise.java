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
package tribefire.platform.impl.deployment;

import java.util.function.Consumer;

import com.braintribe.model.deployment.Deployable;
import com.braintribe.processing.async.impl.HubPromise;

import tribefire.platform.impl.deployment.ParallelDeploymentStatistics.PromiseStatistics;

public class DeploymentPromise extends HubPromise<Void> {

	private QueueStatus queueStatus = QueueStatus.pending;
	private final Object statusMonitor = new Object();
	private final Deployable deployable;
	private final Deployable originalDeployable;
	private final Consumer<? super DeploymentPromise> eagerAccessCallback;
	private final PromiseStatistics promiseStats;

	public DeploymentPromise(Deployable deployable, Deployable originalDeployable, Consumer<? super DeploymentPromise> eagerAccessCallback,
			ParallelDeploymentStatistics stats) {
		this.deployable = deployable;
		this.originalDeployable = originalDeployable;
		this.eagerAccessCallback = eagerAccessCallback;
		this.promiseStats = stats.acquirePromiseStats(deployable);
	}

	public void setQueueStatus(QueueStatus queueStatus) {
		this.queueStatus = queueStatus;
	}

	public QueueStatus getQueueStatus() {
		return queueStatus;
	}

	public Deployable getDeployable() {
		return deployable;
	}

	public Deployable getOriginalDeployable() {
		return originalDeployable;
	}

	public Object getStatusMonitor() {
		return statusMonitor;
	}

	public void notifyEagerAccess() {
		eagerAccessCallback.accept(this);
	}

	public PromiseStatistics getPromiseStatistics() {
		return this.promiseStats;
	}
}
