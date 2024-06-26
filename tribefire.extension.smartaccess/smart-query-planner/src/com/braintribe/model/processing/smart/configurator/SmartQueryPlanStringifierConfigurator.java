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
package com.braintribe.model.processing.smart.configurator;

import com.braintribe.config.configurator.Configurator;
import com.braintribe.config.configurator.ConfiguratorException;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlanPrinter;
import com.braintribe.model.smartqueryplan.SmartQueryPlan;

/**
 * @author peter.gazdik
 */
public class SmartQueryPlanStringifierConfigurator implements Configurator {

	@Override
	public void configure() throws ConfiguratorException {
		GMF.platform().registerStringifier(SmartQueryPlan.T, SmartQueryPlanPrinter::printSafe);
	}

	@Override
	public String toString() {
		return "SmartQueryPlanStringifierConfigurator (registering stringifier for SmartQueryPlan)";
	}
}
