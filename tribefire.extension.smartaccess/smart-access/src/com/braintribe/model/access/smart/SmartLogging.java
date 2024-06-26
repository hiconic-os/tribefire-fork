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
package com.braintribe.model.access.smart;

import com.braintribe.logging.Logger;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlanPrinter;
import com.braintribe.model.query.PropertyQuery;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.query.SelectQueryResult;
import com.braintribe.model.smartqueryplan.SmartQueryPlan;

/**
 * This exists to minimize the logging-related changes in normal logic code.
 * 
 * @author peter.gazdik
 */
public class SmartLogging {

	private static Logger smartAccessLogger = Logger.getLogger(SmartAccess.class);

	public static void selectQuery(SelectQuery query) {
		if (smartAccessLogger.isTraceEnabled())
			smartAccessLogger.trace("Planning smart select query: " + SmartQueryPlanPrinter.printSafe(query));
	}

	public static void propertyQuery(PropertyQuery query) {
		if (smartAccessLogger.isTraceEnabled())
			smartAccessLogger.trace("Smart PropetyQuery: " + SmartQueryPlanPrinter.print(query));
	}

	public static void queryPlan(SmartQueryPlan queryPlan) {
		if (smartAccessLogger.isTraceEnabled())
			smartAccessLogger.trace(SmartQueryPlanPrinter.printSafe(queryPlan));
	}

	public static void queryResult(SelectQueryResult result) {
		if (smartAccessLogger.isTraceEnabled())
			smartAccessLogger.trace("Query result rows count: " + result.getResults().size());
	}

}
