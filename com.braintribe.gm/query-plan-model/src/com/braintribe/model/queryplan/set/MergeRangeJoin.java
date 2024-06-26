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
package com.braintribe.model.queryplan.set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.queryplan.index.GeneratedMetricIndex;
import com.braintribe.model.queryplan.value.range.Range;

/**
 * 
 * Represent a full Cartesian product of the passed tuple sets.
 * 
 * <h4>Example:</h4>
 * 
 * <tt>select * from Person p, Job j where p.skillLevel >= j.requiredLevel</tt>
 * 
 * <code>
 * MergeLookupJoin {
 * 		operand: SourceSet ;
 * 		range: {
 * 			upperBound: ValueProperty {
 * 				value: TupleComponent {
 * 					tupleComponentPosition: SourceSet ;
 * 				}
 * 				propertyPath: "skillLevel"
 * 			}
 * 			upperInclusive: true
 * 		}
 * 		index: GeneratedMetricIndex {
 * 			operand: SourceSet ;
 * 			indexKey: ValueProperty {
 * 				value: TupleComponent {
 * 					tupleComponentPosition: SourceSet ;
 * 				}
 * 				propertyPath: "requiredLevel"
 * 			}
 * 		}
 * }
 * </code>
 * 
 */

public interface MergeRangeJoin extends MergeJoin {

	EntityType<MergeRangeJoin> T = EntityTypes.T(MergeRangeJoin.class);

	GeneratedMetricIndex getIndex();
	void setIndex(GeneratedMetricIndex index);

	Range getRange();
	void setRange(Range range);

	@Override
	default TupleSetType tupleSetType() {
		return TupleSetType.mergeRangeJoin;
	}

}
