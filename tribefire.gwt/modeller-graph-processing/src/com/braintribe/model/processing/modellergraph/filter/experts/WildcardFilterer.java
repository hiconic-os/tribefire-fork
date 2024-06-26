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
package com.braintribe.model.processing.modellergraph.filter.experts;

import com.braintribe.model.modellerfilter.WildcardRelationshipFilter;
import com.braintribe.model.processing.modellergraph.filter.CondensedRelationshipContext;
import com.braintribe.model.processing.modellergraph.filter.RelationshipFilterer;
import com.braintribe.model.processing.modellergraph.filter.relationship.Relationship;

public abstract class WildcardFilterer<T extends WildcardRelationshipFilter> implements RelationshipFilterer<T> {
	@Override
	public boolean matches(CondensedRelationshipContext relationshipContext, RelationshipFiltererContext filtererContext, T relationshipFilter) {
		String values[] = getValues(relationshipContext);
		String expression = relationshipFilter.getWildcardExpression();
		String regex = '^' + expression.replace("*", ".*") + '$';
		
		for (String value: values) {
			if (value.matches(regex)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean matches(Relationship relationship, RelationshipFiltererContext filtererContext, T relationshipFilter) {
		try {
			String value = getValue(relationship);
			
			if (value == null)
				return false;
			
			String expression = relationshipFilter.getWildcardExpression();
			String regex = '^' + expression.replace("*", ".*") + '$';
			
			return value.matches(regex);
		}catch(Exception ex) {
			return false;
		}
	}

	protected abstract String[] getValues(CondensedRelationshipContext relationshipContext);
	protected abstract String getValue(Relationship relationship);
}
