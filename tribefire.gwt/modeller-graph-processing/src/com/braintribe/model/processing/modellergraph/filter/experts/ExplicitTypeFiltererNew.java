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

import java.util.Set;

import com.braintribe.model.modellerfilter.ExplicitTypeFilter;
import com.braintribe.model.processing.modellergraph.ModelGraphConfigurationsNew;
import com.braintribe.model.processing.modellergraph.filter.CondensedRelationshipContext;
import com.braintribe.model.processing.modellergraph.filter.RelationshipFilterer;
import com.braintribe.model.processing.modellergraph.filter.relationship.Relationship;

public class ExplicitTypeFiltererNew implements RelationshipFilterer<ExplicitTypeFilter>{
	
	private ModelGraphConfigurationsNew modelGraphConfigurations;	
	
	public void setModelGraphConfigurations(ModelGraphConfigurationsNew modelGraphConfigurations) {
		this.modelGraphConfigurations = modelGraphConfigurations;
	}
	
	@Override
	public boolean matches(CondensedRelationshipContext relationshipContext,
			RelationshipFiltererContext filtererContext,
			ExplicitTypeFilter relationshipFilter) {
		try{
			Set<String> addedTypes = modelGraphConfigurations.modellerView.getIncludesFilterContext().getAddedTypes();
			String fromTypeSig = relationshipContext.getOriginalRelationship().getFromType().getGmType().getTypeSignature();
			String toTypeSig = relationshipContext.getOriginalRelationship().getToType().getGmType().getTypeSignature();
			return (addedTypes.contains(fromTypeSig) || modelGraphConfigurations.currentFocusedType.equals(fromTypeSig)) 
					&&	(addedTypes.contains(toTypeSig) || modelGraphConfigurations.currentFocusedType.equals(toTypeSig));
		}catch(Exception ex){
			return false;
		}
	}
	
	@Override
	public boolean matches(Relationship relationship,
			RelationshipFiltererContext filtererContext,
			ExplicitTypeFilter relationshipFilter) {
		try{
			Set<String> addedTypes = modelGraphConfigurations.modellerView.getIncludesFilterContext().getAddedTypes();
			String fromTypeSig = relationship.getFromType().getTypeSignature();
			String toTypeSig = relationship.getToType().getTypeSignature();
			return (addedTypes.contains(fromTypeSig) || modelGraphConfigurations.currentFocusedType.equals(fromTypeSig)) 
					&&	(addedTypes.contains(toTypeSig) || modelGraphConfigurations.currentFocusedType.equals(toTypeSig));
		}catch(Exception ex){
			return false;
		}
	}

}
