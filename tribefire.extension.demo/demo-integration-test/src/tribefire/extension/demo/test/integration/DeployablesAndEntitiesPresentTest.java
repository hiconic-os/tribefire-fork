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
package tribefire.extension.demo.test.integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.model.accessdeployment.smood.CollaborativeSmoodAccess;
import com.braintribe.model.resource.SimpleIcon;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.testing.internal.suite.GenericDeployablesPresentTest;

import tribefire.extension.demo.model.data.Address;
import tribefire.extension.demo.model.data.Company;
import tribefire.extension.demo.model.data.Department;
import tribefire.extension.demo.model.data.Person;
import tribefire.extension.demo.model.deployment.AuditProcessor;
import tribefire.extension.demo.model.deployment.DemoAccess;
import tribefire.extension.demo.model.deployment.DemoApp;
import tribefire.extension.demo.model.deployment.DepartmentRiskProcessor;
import tribefire.extension.demo.model.deployment.FindByTextProcessor;
import tribefire.extension.demo.model.deployment.GetEmployeesByGenderProcessor;
import tribefire.extension.demo.model.deployment.NewEmployeeProcessor;
import tribefire.extension.demo.model.deployment.RevenueNotificationProcessor;
import tribefire.extension.demo.test.integration.utils.AbstractDemoTest;

/**
 * checks if all expected deployables are present and deployed, as well as expected demo entities are present
 *
 */
// TODO CWI have a look as soon as deploymentStatus flag is fixed
@Category(KnownIssue.class)
public class DeployablesAndEntitiesPresentTest extends AbstractDemoTest {

	@Test
	public void testDeployablesDeployed() {
		logger.info("Making sure that all expected deployables are there and deployed...");

		GenericDeployablesPresentTest test = new GenericDeployablesPresentTest(globalCortexSessionFactory);

		test.assertThatDeployableIsPresentAndDeployed("statechangeProcessor.audit", AuditProcessor.T);
		test.assertThatDeployableIsPresentAndDeployed("access.demo", DemoAccess.T);
		test.assertThatDeployableIsPresentAndDeployed("app.demo", DemoApp.T);
		test.assertThatDeployableIsPresentAndDeployed("access.demo.wb", CollaborativeSmoodAccess.T);
		test.assertThatDeployableIsPresentAndDeployed("statechangeProcessor.departmentRisk", DepartmentRiskProcessor.T);
		test.assertThatDeployableIsPresentAndDeployed("serviceProcessor.findByText", FindByTextProcessor.T);
		test.assertThatDeployableIsPresentAndDeployed("serviceProcessor.getEmployeeByGender", GetEmployeesByGenderProcessor.T);
		test.assertThatDeployableIsPresentAndDeployed("serviceProcessor.newEmployee", NewEmployeeProcessor.T);
		test.assertThatDeployableIsPresentAndDeployed("statechangeProcessor.revenueNotification", RevenueNotificationProcessor.T);

		logger.info("Test finished successfully!");
	}

	/**
	 * makes sure there is at least one company and one person stored in demo access
	 */
	@Test
	public void testEntitiesPresent() {
		logger.info("Test if at least one instance of each expected entity type was instantiated...");

		GenericDeployablesPresentTest.testEntitiesPresent(demoAccessSession, Company.T, Person.T, Department.T, Address.T, SimpleIcon.T);

		logger.info("Test succeeded!");

	}

}
