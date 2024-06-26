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
package com.braintribe.model.processing.email;

import java.util.List;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckRequest;
import com.braintribe.model.check.service.CheckResult;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;
import com.braintribe.model.email.service.CheckConnections;
import com.braintribe.model.email.service.ConnectionCheckResult;
import com.braintribe.model.email.service.ConnectionCheckResultEntry;
import com.braintribe.model.email.service.reason.ConfigurationMissing;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.check.api.CheckProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

public class HealthCheckProcessor implements CheckProcessor {

	private static final Logger logger = Logger.getLogger(HealthCheckProcessor.class);

	private Evaluator<ServiceRequest> systemServiceRequestEvaluator;

	@Override
	public CheckResult check(ServiceRequestContext requestContext, CheckRequest request) {

		CheckResult response = CheckResult.T.create();
		List<CheckResultEntry> entries = response.getEntries();

		CheckConnections cc = CheckConnections.T.create();
		Maybe<? extends ConnectionCheckResult> reasoned = cc.eval(systemServiceRequestEvaluator).getReasoned();

		if (reasoned.isUnsatisfied()) {
			if (reasoned.isUnsatisfiedBy(ConfigurationMissing.T)) {
				logger.debug(() -> "Check is ok only because there is no email connector configured.");
				CheckResultEntry cre = CheckResultEntry.T.create();
				cre.setCheckStatus(CheckStatus.ok);
				cre.setName("Email Connnectors");
				cre.setMessage("No connectors available.");
				entries.add(cre);
			} else {
				final String explanation = reasoned.whyUnsatisfied().stringify();
				logger.debug(() -> "Check is not ok because of: " + explanation);
				CheckResultEntry cre = CheckResultEntry.T.create();
				cre.setCheckStatus(CheckStatus.fail);
				cre.setName("Email Connnectors");
				cre.setMessage("Error while trying to establish the connection status.");
				cre.setDetails(explanation);
				entries.add(cre);
			}
		} else {
			ConnectionCheckResult checkResult = reasoned.get();
			for (ConnectionCheckResultEntry entry : checkResult.getEntries()) {
				CheckResultEntry cre = CheckResultEntry.T.create();
				cre.setName(entry.getName() + " (" + entry.getType().name().toLowerCase() + ")");
				if (entry.getSuccess()) {
					cre.setCheckStatus(CheckStatus.ok);
					cre.setDetails(entry.getDetails());
				} else {
					cre.setCheckStatus(CheckStatus.fail);
					cre.setDetails(entry.getErrorMessage());
				}
				entries.add(cre);
			}
		}

		return response;
	}

	@Configurable
	@Required
	public void setSystemServiceRequestEvaluator(Evaluator<ServiceRequest> systemServiceRequestEvaluator) {
		this.systemServiceRequestEvaluator = systemServiceRequestEvaluator;
	}
}
