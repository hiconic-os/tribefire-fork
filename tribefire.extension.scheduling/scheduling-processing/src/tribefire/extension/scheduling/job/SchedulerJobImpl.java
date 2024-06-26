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
package tribefire.extension.scheduling.job;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.job.api.JobRequest;
import com.braintribe.model.job.api.JobResponse;
import com.braintribe.model.processing.service.api.Job;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.scheduling.model.api.UpdateRegistry;

public class SchedulerJobImpl implements Job {

	private static final Logger logger = Logger.getLogger(SchedulerJobImpl.class);

	private Evaluator<ServiceRequest> systemServiceRequestEvaluator;
	private String accessId;

	@Override
	public JobResponse process(ServiceRequestContext requestContext, JobRequest request) {
		logger.debug(() -> "Initiating periodic Cleanup service.");

		UpdateRegistry req = UpdateRegistry.T.create();
		req.setDomainId(accessId);

		req.eval(systemServiceRequestEvaluator).get();

		return JobResponse.T.create();
	}

	@Required
	@Configurable
	public void setSystemServiceRequestEvaluator(Evaluator<ServiceRequest> systemServiceRequestEvaluator) {
		this.systemServiceRequestEvaluator = systemServiceRequestEvaluator;
	}

	@Required
	@Configurable
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

}
