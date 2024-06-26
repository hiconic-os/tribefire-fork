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
package com.braintribe.model.processing.library.job;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.library.service.vulnerabilities.UpdateNvdMirror;
import com.braintribe.model.processing.library.LibraryConstants;
import com.braintribe.model.processing.service.api.ServiceProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.job_scheduling.api.api.JobRequest;
import tribefire.extension.job_scheduling.api.api.JobResponse;

public class UpdateNvdMirrorScheduledJobImpl implements ServiceProcessor<JobRequest, JobResponse> {

	private static final Logger logger = Logger.getLogger(UpdateNvdMirrorScheduledJobImpl.class);

	private Evaluator<ServiceRequest> systemServiceRequestEvaluator;

	@Override
	public JobResponse process(ServiceRequestContext requestContext, JobRequest request) {

		logger.debug(() -> "Initiating periodic NVD Update service.");

		UpdateNvdMirror req = UpdateNvdMirror.T.create();
		req.setDomainId(LibraryConstants.LIBRARY_ACCESS_ID);
		req.eval(systemServiceRequestEvaluator).get();

		return JobResponse.T.create();
	}

	@Required
	@Configurable
	public void setSystemServiceRequestEvaluator(Evaluator<ServiceRequest> systemServiceRequestEvaluator) {
		this.systemServiceRequestEvaluator = systemServiceRequestEvaluator;
	}

}
