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
package tribefire.extension.metrics.health;

import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckRequest;
import com.braintribe.model.check.service.CheckResult;
import com.braintribe.model.processing.check.api.CheckProcessor;
import com.braintribe.model.processing.deployment.api.DeployRegistry;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class HealthCheckProcessor implements CheckProcessor {

	private static final Logger logger = Logger.getLogger(HealthCheckProcessor.class);

	private Supplier<PersistenceGmSession> cortexSessionSupplier;
	private DeployRegistry deployRegistry;

	@Override
	public CheckResult check(ServiceRequestContext requestContext, CheckRequest request) {
		return null;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Required
	@Configurable
	public void setCortexSessionSupplier(Supplier<PersistenceGmSession> cortexSessionSupplier) {
		this.cortexSessionSupplier = cortexSessionSupplier;
	}

	@Configurable
	@Required
	public void setDeployRegistry(DeployRegistry deployRegistry) {
		this.deployRegistry = deployRegistry;
	}

}
