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
package tribefire.extension.demo.processing;

import com.braintribe.model.check.service.CheckRequest;
import com.braintribe.model.check.service.CheckResult;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;
import com.braintribe.model.processing.check.api.CheckProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;

public class DemoHealthCheckProcessor implements CheckProcessor {

	@Override
	public CheckResult check(ServiceRequestContext requestContext, CheckRequest request) {
		
		CheckResult response  = CheckResult.T.create();
		
		CheckResultEntry entry = CheckResultEntry.T.create();
		entry.setName("Demo Health Check");
		entry.setDetails("Successfully executed demo health check.");
		entry.setCheckStatus(CheckStatus.ok);
		
		response.getEntries().add(entry);
		
		return response;
		
	}

}
