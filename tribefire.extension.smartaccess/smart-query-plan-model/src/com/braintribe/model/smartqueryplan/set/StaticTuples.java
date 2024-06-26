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

import java.util.List;


import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.queryplan.set.TupleSet;
import com.braintribe.model.queryplan.set.TupleSetType;

/**
 * Represents a {@link TupleSet} consisting of multiple tuples described by underlying {@link StaticTuple}s. The planner must guarantee that
 * none of these underlying tuples is empty.
 * 
 * @author peter.gazdik
 */
public interface StaticTuples extends SmartTupleSet {

	EntityType<StaticTuples> T = EntityTypes.T(StaticTuples.class);

	List<StaticTuple> getTuples();
	void setTuples(List<StaticTuple> value);

	@Override
	default TupleSetType tupleSetType() {
		return TupleSetType.extension;
	}

	@Override
	default SmartTupleSetType smartType() {
		return SmartTupleSetType.staticTuples;
	}

}
