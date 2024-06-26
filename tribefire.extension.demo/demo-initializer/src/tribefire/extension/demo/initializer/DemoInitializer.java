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
package tribefire.extension.demo.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.demo.initializer.wire.DemoInitializerWireModule;
import tribefire.extension.demo.initializer.wire.contract.DemoInitializerContract;
import tribefire.extension.demo.initializer.wire.contract.DemoInitializerMainContract;
import tribefire.extension.demo.initializer.wire.contract.DemoInitializerModelsContract;

/**
 * <p>
 * This {@link AbstractInitializer initializer} initializes targeted accesses
 * with our custom instances available from initializer's contracts.
 * </p>
 */
public class DemoInitializer extends AbstractInitializer<DemoInitializerMainContract> {

	@Override
	protected WireTerminalModule<DemoInitializerMainContract> getInitializerWireModule() {
		return DemoInitializerWireModule.INSTANCE;
	}

	@Override
	protected void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<DemoInitializerMainContract> initializerContext,
			DemoInitializerMainContract initializerMainContract) {

		DemoInitializerModelsContract models = initializerMainContract.initializerModelsContract();
		CoreInstancesContract coreInstances = initializerMainContract.coreInstancesContract();

		GmMetaModel cortexModel = coreInstances.cortexModel();
		GmMetaModel cortexServiceModel = coreInstances.cortexServiceModel();

		cortexModel.getDependencies().add(models.configuredDemoDeploymentModel());
		cortexServiceModel.getDependencies().add(models.configuredDemoServiceModel());
		cortexServiceModel.getDependencies().add(models.configuredDemoCortexServiceModel());

		models.demoWorkbenchModel();

		DemoInitializerContract initializer = initializerMainContract.initializerContract();

		initializer.demoAccess();
		initializer.demoTransientSmoodAccess();
		initializer.revenueNotificationProcessor();
		initializer.departmentRiskProcessor();
		initializer.auditProcessor();
		initializer.newEmployeeProcessor();
		initializer.getOrphanedEmployeesProcessor();
		initializer.getEmployeesByGenderProcessor();
		initializer.getPersonsByNameProcessor();
		initializer.findByTextProcessor();
		initializer.resourceStreamingProcessor();
		initializer.testAccessProcessor();
		initializer.healthChecks();
		initializer.demoApp();
		initializer.orphanedEmployeesJobScheduler();

		initializerMainContract.metaData();
	}
}
