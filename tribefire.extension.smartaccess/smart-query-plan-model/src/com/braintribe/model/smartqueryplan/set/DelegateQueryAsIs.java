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
package com.braintribe.model.smartqueryplan.set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.query.SelectQuery;
import com.braintribe.model.queryplan.set.TupleSetType;

/**
 * A tuple set especially for the case when the entire query can be delegated.
 * 
 * This differs from {@link DelegateQuerySet} in that DQS also has mappings which tell how the result from the delegate
 * evaluation is mapped (i.e. how to transform the individual tuple values and where to place them). We avoid this noise
 * in the most simple use-case of delegating the entire query by creating a special instruction that implies that.
 */
public interface DelegateQueryAsIs extends SmartTupleSet {

	EntityType<DelegateQueryAsIs> T = EntityTypes.T(DelegateQueryAsIs.class);

	IncrementalAccess getDelegateAccess();
	void setDelegateAccess(IncrementalAccess delegateAccess);

	SelectQuery getDelegateQuery();
	void setDelegateQuery(SelectQuery delegateQuery);

	@Override
	default TupleSetType tupleSetType() {
		return TupleSetType.extension;
	}

	@Override
	default SmartTupleSetType smartType() {
		return SmartTupleSetType.delegateQueryAsIs;
	}

}
