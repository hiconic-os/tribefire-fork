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
package com.braintribe.model.smartqueryplan;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import com.braintribe.model.generic.GenericEntity;

import com.braintribe.model.queryplan.value.Value;
import com.braintribe.model.smartqueryplan.set.DelegateQuerySet;

/**
 * Maps given value to a position (index) in a tuple. This is used in {@link DelegateQuerySet} to map the query result
 * from a delegate to the position in a smart-level tuple. In most cases, the <tt>sourceValue</tt> is simply a position
 * in the result tuple, but it may also be some function (e.g. for the purpose of value conversion).
 * 
 */
// Rename to something else? The class itself does not say anything about "scalars"
public interface ScalarMapping extends GenericEntity {

	EntityType<ScalarMapping> T = EntityTypes.T(ScalarMapping.class);

	int getTupleComponentIndex();
	void setTupleComponentIndex(int tupleComponentIndex);

	Value getSourceValue();
	void setSourceValue(Value sourceValue);

}
