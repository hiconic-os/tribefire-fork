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
package com.braintribe.model.access.sql.query.planner;

import com.braintribe.model.access.sql.query.oracle.JdbcQuery;
import com.braintribe.model.sql.plan.JdbcQueryPlan;

/**
 * @author peter.gazdik
 */
public class JdbcQueryPlanner {

	public static JdbcQueryPlan plan(JdbcQuery query, JdbcPlannerContext context) {
		return new JdbcQueryPlanner(query, context).plan();
	}

	private final JdbcQuery query;
	private final JdbcPlannerContext context;

	private JdbcQueryPlanner(JdbcQuery query, JdbcPlannerContext context) {
		this.query = query;
		this.context = context;
	}

	private JdbcQueryPlan plan() {
		throw new UnsupportedOperationException("Method 'JdbcQueryPlanner.plan' is not implemented yet!");
	}

}
