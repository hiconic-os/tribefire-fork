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
package tribefire.extension.demo.initializer.wire.contract;

import com.braintribe.model.extensiondeployment.check.HealthCheck;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.demo.model.deployment.AuditProcessor;
import tribefire.extension.demo.model.deployment.DemoAccess;
import tribefire.extension.demo.model.deployment.DemoApp;
import tribefire.extension.demo.model.deployment.DemoTransientSmoodAccess;
import tribefire.extension.demo.model.deployment.DepartmentRiskProcessor;
import tribefire.extension.demo.model.deployment.EntityMarshallingProcessor;
import tribefire.extension.demo.model.deployment.FindByTextProcessor;
import tribefire.extension.demo.model.deployment.GetEmployeesByGenderProcessor;
import tribefire.extension.demo.model.deployment.GetOrphanedEmployeesProcessor;
import tribefire.extension.demo.model.deployment.GetPersonsByNameProcessor;
import tribefire.extension.demo.model.deployment.NewEmployeeProcessor;
import tribefire.extension.demo.model.deployment.ResourceStreamingProcessor;
import tribefire.extension.demo.model.deployment.RevenueNotificationProcessor;
import tribefire.extension.demo.model.deployment.TestAccessProcessor;
import tribefire.extension.job_scheduling.deployment.model.JobScheduling;

/**
 * <p>
 * This {@link WireSpace Wire contract} exposes our custom instances (e.g.
 * instances of our deployables).
 * </p>
 */
public interface DemoInitializerContract extends WireSpace {

	DemoAccess demoAccess();

	DemoTransientSmoodAccess demoTransientSmoodAccess();

	RevenueNotificationProcessor revenueNotificationProcessor();

	DepartmentRiskProcessor departmentRiskProcessor();

	AuditProcessor auditProcessor();

	NewEmployeeProcessor newEmployeeProcessor();
	
	GetOrphanedEmployeesProcessor getOrphanedEmployeesProcessor();

	GetEmployeesByGenderProcessor getEmployeesByGenderProcessor();

	GetPersonsByNameProcessor getPersonsByNameProcessor();

	FindByTextProcessor findByTextProcessor();
	
	EntityMarshallingProcessor entityMarshallingProcessor();

	ResourceStreamingProcessor resourceStreamingProcessor();

	TestAccessProcessor testAccessProcessor();

	HealthCheck healthChecks();

	DemoApp demoApp();

	JobScheduling orphanedEmployeesJobScheduler();
	
	MetaData confidential();

	MetaData processWithGetOrphanedEmployees();
	
	MetaData processWithGetEmployeeByGender();

	MetaData processWithGetPersonsByName();

	MetaData processWithFindByText();
	
	MetaData processWithEntityMarshalling();

	MetaData processWithNewEmployee();

	MetaData processWithTestAccess();

	MetaData onChangeRevenue();

	MetaData onChangeProfitable();

	MetaData onChangeAudit();

	MetaData onCreateAudit();

	MetaData onDeleteAudit();

	MetaData dateClippingDay();

	MetaData processWithResourceStreaming();
	
	MetaData hidden();

}
