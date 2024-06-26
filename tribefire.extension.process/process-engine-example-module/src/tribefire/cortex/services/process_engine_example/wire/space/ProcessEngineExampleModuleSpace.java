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
package tribefire.cortex.services.process_engine_example.wire.space;

import com.braintribe.model.goofydeployment.GoofyClearanceChecker;
import com.braintribe.model.goofydeployment.GoofyClearedChecker;
import com.braintribe.model.goofydeployment.GoofyDecoder;
import com.braintribe.model.goofydeployment.GoofyErrorProducer;
import com.braintribe.model.goofydeployment.GoofyHasher;
import com.braintribe.model.goofydeployment.GoofyOutputer;
import com.braintribe.model.goofydeployment.GoofyValidator;
import com.braintribe.model.goofydeployment.GoofyWatcher;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.process.module.wire.contract.ProcessBindersContract;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class ProcessEngineExampleModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;
	
	@Import
	private ProcessEngineExampleDeployablesSpace deployables;
	
	@Import
	private ProcessBindersContract processBinders;
	
	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(GoofyWatcher.T) //
				.component(tfPlatform.binders().worker()) //
				.expertFactory(deployables::goofyWatcher);

		bindings.bind(GoofyClearanceChecker.T) //
				.component(processBinders.conditionProcessor()) //
				.expertSupplier(deployables::goofyClearanceChecker);

		bindings.bind(GoofyClearedChecker.T) //
				.component(processBinders.conditionProcessor()) //
				.expertSupplier(deployables::goofyClearedChecker);

		bindings.bind(GoofyOutputer.T) //
				.component(processBinders.transitionProcessor()) //
				.expertFactory(deployables::goofyOutputer);

		bindings.bind(GoofyHasher.T) //
				.component(processBinders.transitionProcessor()) //
				.expertSupplier(deployables::goofyHashProcessor);

		bindings.bind(GoofyValidator.T) //
				.component(processBinders.transitionProcessor()) //
				.expertSupplier(deployables::goofyValidateProcessor);

		bindings.bind(GoofyDecoder.T) //
				.component(processBinders.transitionProcessor()) //
				.expertSupplier(deployables::goofyDecoder);

		bindings.bind(GoofyErrorProducer.T) //
				.component(processBinders.transitionProcessor()) //
				.expertSupplier(deployables::goofyErrorProducer);
	}
}
