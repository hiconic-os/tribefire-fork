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
package tribefire.extension.metrics.connector.newrelic;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;

import tribefire.extension.metrics.connector.api.AbstractMetricsConnector;

/**
 *
 */
public class NewRelicMetricsConnector extends AbstractMetricsConnector {

	private final static Logger logger = Logger.getLogger(NewRelicMetricsConnector.class);

	private tribefire.extension.metrics.model.deployment.connector.NewRelicMetricsConnector deployable;

	// -----------------------------------------------------------------------
	// LifecycleAware
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	@Override
	public void initialize() {

		// registry().
	}

	@Override
	protected CheckResultEntry actualHealth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		return defaultName(deployable);
	}

	// -----------------------------------------------------------------------
	// HELPERS
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setDeployable(tribefire.extension.metrics.model.deployment.connector.NewRelicMetricsConnector deployable) {
		this.deployable = deployable;
	}

}
