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
package com.braintribe.model.processing.query.test;

import org.junit.Rule;
import org.junit.rules.TestName;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.fluent.SelectQueryBuilder;
import com.braintribe.model.processing.query.planner.QueryPlanner;
import com.braintribe.model.processing.query.test.check.QueryPlanAssemblyChecker;
import com.braintribe.model.processing.query.test.debug.TupleSetViewer;
import com.braintribe.model.processing.query.test.model.Address;
import com.braintribe.model.processing.query.test.model.Company;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.processing.query.test.repository.IndexConfiguration;
import com.braintribe.model.processing.query.test.repository.RepositoryMock;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.TupleSet;

/**
 * 
 */
public class AbstractQueryPlannerTests {

	private static QueryPlanner standardQueryPlanner;

	protected QueryPlanner queryPlanner = getQueryPlanner();

	protected TupleSet queryPlan;

	static {
		IndexConfiguration indexConfiguration = new IndexConfiguration();

		// Person (metric)
		indexConfiguration.addMetricIndex(Person.T, "id");
		indexConfiguration.addMetricIndex(Person.T, "indexedName");
		indexConfiguration.addMetricIndex(Person.T, "indexedInteger");
		indexConfiguration.addMetricIndex(Person.T, "indexedDate");
		// Person (lookup)
		indexConfiguration.addLookupIndex(Person.T, "indexedCompany");

		// ############################################################################################################

		// Company (metric)
		indexConfiguration.addMetricIndex(Company.T, "id");
		indexConfiguration.addMetricIndex(Company.T, "indexedDate");
		// Company (lookup)
		indexConfiguration.addLookupIndex(Company.T, "indexedName");

		indexConfiguration.addMetricIndex(Address.T, "id");

		standardQueryPlanner = new QueryPlanner(new RepositoryMock(indexConfiguration));
	}

	protected QueryPlanner getQueryPlanner() {
		return standardQueryPlanner;
	}

	protected SelectQueryBuilder query() {
		return new SelectQueryBuilder();
	}

	protected void runTest(SelectQuery selectQuery) {
		queryPlan = queryPlanner.buildQueryPlan(selectQuery).getTupleSet();
		TupleSetViewer.view(getTestName(), queryPlan);
	}

	protected QueryPlanAssemblyChecker assertQueryPlan() {
		return new QueryPlanAssemblyChecker(queryPlan);
	}

	protected <T extends GenericEntity> T instance(EntityType<T> entityType, long id) {
		return instance(entityType, id, null);
	}

	protected <T extends GenericEntity> T instance(EntityType<T> entityType, long id, String partition) {
		T result = entityType.create();
		result.setId(id);
		result.setPartition(partition);

		return result;
	}

	@Rule
	public TestName testName = new TestName();

	private String getTestName() {
		return testName.getMethodName();
	}

}
