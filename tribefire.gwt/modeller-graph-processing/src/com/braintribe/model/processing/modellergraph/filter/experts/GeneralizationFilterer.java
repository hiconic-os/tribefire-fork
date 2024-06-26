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

import com.braintribe.model.modellerfilter.ExplicitTypeFilter;
import com.braintribe.model.modellerfilter.GeneralizationFilter;
import com.braintribe.model.processing.modellergraph.filter.CondensedRelationshipContext;
import com.braintribe.model.processing.modellergraph.filter.RelationshipFilterer;
import com.braintribe.model.processing.modellergraph.filter.relationship.Relationship;
import com.braintribe.model.processing.modellergraph.filter.relationship.RelationshipKind;

public class GeneralizationFilterer implements RelationshipFilterer<GeneralizationFilter>{
	
	private RelationshipFilterer<ExplicitTypeFilter> explicitTypeFilterer;
	private boolean explicit = false;
	
	public void setExplicit(boolean explicit) {
		this.explicit = explicit;
	}
	
	public void setExplicitTypeFilterer(RelationshipFilterer<ExplicitTypeFilter> explicitTypeFilterer) {
		this.explicitTypeFilterer = explicitTypeFilterer;
	}
	
	@Override
	public boolean matches(CondensedRelationshipContext relationshipContext,
			RelationshipFiltererContext filtererContext,
			GeneralizationFilter relationshipFilter) {
		if(explicit)
			return explicitTypeFilterer.matches(relationshipContext, filtererContext, null);
		else
			return relationshipContext.getRelationship().getGeneralization();
	}
	
	@Override
	public boolean matches(Relationship relationship, RelationshipFiltererContext filtererContext, GeneralizationFilter relationshipFilter) {
		if(explicit)
			return explicitTypeFilterer.matches(relationship, filtererContext, null);
		else
			return relationship.getRelationshipKind() == RelationshipKind.generalization;
	}
}
