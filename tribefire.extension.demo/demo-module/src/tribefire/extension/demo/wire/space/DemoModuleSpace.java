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
package tribefire.extension.demo.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.demo.model.deployment.AuditProcessor;
import tribefire.extension.demo.model.deployment.DemoAccess;
import tribefire.extension.demo.model.deployment.DemoApp;
import tribefire.extension.demo.model.deployment.DemoHealthCheckProcessor;
import tribefire.extension.demo.model.deployment.DemoTransientSmoodAccess;
import tribefire.extension.demo.model.deployment.DepartmentRiskProcessor;
import tribefire.extension.demo.model.deployment.EntityMarshallingProcessor;
import tribefire.extension.demo.model.deployment.FindByTextProcessor;
import tribefire.extension.demo.model.deployment.GetEmployeesByGenderProcessor;
import tribefire.extension.demo.model.deployment.GetOrphanedEmployeesProcessor;
import tribefire.extension.demo.model.deployment.GetPersonsByNameProcessor;
import tribefire.extension.demo.model.deployment.NewEmployeeProcessor;
import tribefire.extension.demo.model.deployment.OrphanedEmployeesWatchJob;
import tribefire.extension.demo.model.deployment.ResourceStreamingProcessor;
import tribefire.extension.demo.model.deployment.RevenueNotificationProcessor;
import tribefire.extension.demo.model.deployment.TestAccessProcessor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;


/**
 * @author peter.gazdik
 */
@Managed
public class DemoModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private DemoDeployablesSpace deployables;
	
	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		// @formatter:off
		bindings.bind(DemoAccess.T)
			.component(tfPlatform.binders().incrementalAccess())
			.expertFactory(deployables::demoAccess);
		
		bindings.bind(DemoTransientSmoodAccess.T)
			.component(tfPlatform.binders().incrementalAccess())
			.expertFactory(deployables::demoTransientSmoodAccess);
		
		bindings.bind(DepartmentRiskProcessor.T)
			.component(tfPlatform.binders().stateChangeProcessor())
			.expertSupplier(deployables::departmentRiskProcessor);
		
		bindings.bind(AuditProcessor.T)
			.component(tfPlatform.binders().stateChangeProcessor())
			.expertSupplier(deployables::auditProcessor);
		
		bindings.bind(GetEmployeesByGenderProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertSupplier(deployables::getEmployeesByGenderProcessor);
		
		bindings.bind(GetOrphanedEmployeesProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertSupplier(deployables::getOrphanedEmployeesProcessor);
		
		bindings.bind(GetPersonsByNameProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertSupplier(deployables::getPersonsByNameProcessor);
	
		bindings.bind(DemoApp.T)
			.component(tfPlatform.binders().webTerminal())
			.expertFactory(deployables::listUserApp);
		
		bindings.bind(FindByTextProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertSupplier(deployables::findByTextProcessor);
		
		bindings.bind(EntityMarshallingProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertSupplier(deployables::entityMarshallingProcessor);
		
		bindings.bind(TestAccessProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertFactory(deployables::testAccessProcessor);
		
		bindings.bind(OrphanedEmployeesWatchJob.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::orphanedEmployeesWatcher);
		
		bindings.bind(NewEmployeeProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertSupplier(deployables::newEmployeeProcessor);
		
		bindings.bind(ResourceStreamingProcessor.T)
			.component(tfPlatform.binders().accessRequestProcessor())
			.expertSupplier(deployables::resourceStreamingProcessor);
		
		bindings.bind(RevenueNotificationProcessor.T)
			.component(tfPlatform.binders().stateChangeProcessor())
			.expertFactory(deployables::revenueNotificationProcessor);
		
		bindings.bind(DemoHealthCheckProcessor.T)
			.component(tfPlatform.binders().checkProcessor())
			.expertSupplier(deployables::demoHealthCheckProcessor);
		// @formatter:on
	}
}
