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
package com.braintribe.model.processing.smart.query.planner.tools;

import static com.braintribe.utils.lcd.CollectionTools2.first;
import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.util.Set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.reflection.GmReflectionTools;
import com.braintribe.model.processing.smart.query.planner.splitter.DisambiguatedQuery;
import com.braintribe.model.processing.smart.query.planner.structure.ModelExpert;
import com.braintribe.model.query.From;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.smartqueryplan.SmartQueryPlan;
import com.braintribe.model.smartqueryplan.set.DelegateQueryAsIs;

/**
 * @author peter.gazdik
 */
public class DelegateAsIsTools {

	public static SmartQueryPlan buildDelegateAsIsPlan(DisambiguatedQuery dq) {
		IncrementalAccess access = first(dq.fromMapping.values());

		SelectQuery query = GmReflectionTools.makeDeepCopy(dq.originalQuery);

		DelegateQueryAsIs delegateAsIs = DelegateQueryAsIs.T.create();
		delegateAsIs.setDelegateQuery(query);
		delegateAsIs.setDelegateAccess(access);

		SmartQueryPlan result = SmartQueryPlan.T.create();
		result.setTupleSet(delegateAsIs);
		result.setTotalComponentCount(query.getSelections().size());

		return result;
	}

	public static boolean canDelegateAsIs(DisambiguatedQuery dq, ModelExpert modelExpert) {
		return new CanDelegateResolver(dq, modelExpert).resolve();
	}

	private static class CanDelegateResolver {

		private final DisambiguatedQuery dq;
		private final ModelExpert modelExpert;

		private IncrementalAccess access;

		public CanDelegateResolver(DisambiguatedQuery dq, ModelExpert modelExpert) {
			this.dq = dq;
			this.modelExpert = modelExpert;
		}

		public boolean resolve() {
			return determineSingleDelegateAccess() && //
					eachFromIsFullyDelegatable();
		}

		private boolean determineSingleDelegateAccess() {
			Set<IncrementalAccess> accesses = newSet(dq.fromMapping.values());

			if (accesses.size() > 1)
				return false;

			access = first(accesses);
			return true;
		}

		private boolean eachFromIsFullyDelegatable() {
			for (From from : dq.fromMapping.keySet())
				if (!isFullyDelegatable(from))
					return false;

			return true;
		}

		private boolean isFullyDelegatable(From from) {
			return modelExpert.isMappedAsIs(from.getEntityTypeSignature(), access);
		}

	}

}
