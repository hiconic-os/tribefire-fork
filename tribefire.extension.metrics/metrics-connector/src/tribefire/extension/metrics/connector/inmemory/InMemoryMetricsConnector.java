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
package tribefire.extension.metrics.connector.inmemory;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;

import tribefire.extension.metrics.connector.api.AbstractMetricsConnector;

/**
 *
 */
public class InMemoryMetricsConnector extends AbstractMetricsConnector {

	private final static Logger logger = Logger.getLogger(InMemoryMetricsConnector.class);

	private tribefire.extension.metrics.model.deployment.connector.InMemoryMetricsConnector deployable;

	// -----------------------------------------------------------------------
	// LifecycleAware
	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	@Override
	public void initialize() {

		// ---------
		// TODO: remove
		//@formatter:off
//		Counter counter = registry.counter("test", "region", "test");

//	    Counter featureCounter = registry().counter("feature", "region", "test");
//	    featureCounter.increment();
//	    
//	    String type = "";
//	    
//        Counter counter = registry().counter("feature.2", "type", type);
//        counter.increment();
//        counter.increment(2.0);
//        
//        Search find = registry().find("feature");
//        Collection<Counter> counters = find.counters();
//        
//        
//        
//        
//        System.out.println("--->>>> " + counter.count());
//        System.out.println("--->>>> " + featureCounter.count());
		//@formatter:on

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
	public void setDeployable(tribefire.extension.metrics.model.deployment.connector.InMemoryMetricsConnector deployable) {
		this.deployable = deployable;
	}
}
