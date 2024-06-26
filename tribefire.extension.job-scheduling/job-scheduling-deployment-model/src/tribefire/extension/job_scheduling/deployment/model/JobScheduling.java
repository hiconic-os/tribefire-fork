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
package tribefire.extension.job_scheduling.deployment.model;

import com.braintribe.model.descriptive.HasCredentials;
import com.braintribe.model.extensiondeployment.ServiceProcessor;
import com.braintribe.model.extensiondeployment.Worker;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * <p>
 * This interface is designed to provide an extension point for running scheduled {@link Job Jobs} in an interval
 * defined by {@link #setCronExpression(String)}.
 * 
 * <p>
 * As {@link HasCredentials} is defined, meaning that user credentials can be configured to define the execution scope
 * (in case user and password credentials are left empty, the internal default user session scope is used).
 * 
 * <p>
 * In case the scheduler is unable to execute scheduled jobs (e.g. the scheduler shut down),
 * {@link #setCoalescing(boolean)} defines, if job executions are coalesced to avoid execution several times in
 * succession.
 */
@Abstract
public interface JobScheduling extends Worker, HasCredentials {

	EntityType<JobScheduling> T = EntityTypes.T(JobScheduling.class);

	/**
	 * The task to be executed by the scheduler.
	 */
	void setJobRequestProcessor(ServiceProcessor jobRequestProcessor);
	ServiceProcessor getJobRequestProcessor();

	/**
	 * If set to <code>true</code> missed job executions will be coalesced to avoid execution several times in
	 * succession.
	 */
	void setCoalescing(boolean coalescing);
	boolean getCoalescing();
}
