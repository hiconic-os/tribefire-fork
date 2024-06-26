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
package com.braintribe.model.processing.query.smart.test.debug;

import com.braintribe.model.processing.query.test.debug.TupleSetViewer;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlanPrinter;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.smartqueryplan.SmartQueryPlan;

public class SmartTupleSetViewer extends TupleSetViewer {

	public static void view(String testName, SelectQuery query, SmartQueryPlan plan) {
		printTestName(testName);
		System.out.println("Query: " + SmartQueryPlanPrinter.print(query) + "\n");
		System.out.println("Total component count: " + plan.getTotalComponentCount());
		System.out.println(SmartQueryPlanPrinter.print(plan.getTupleSet(), true));
		printTestBottomBorder();
	}

	public static void view(String testName, TupleSet set) {
		printTestName(testName);
		System.out.println(SmartQueryPlanPrinter.print(set, true));
		printTestBottomBorder();
	}

}
