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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.modellerfilter.AllIncludedTypeFilter;
import com.braintribe.model.modellergraph.condensed.CondensedType;
import com.braintribe.model.processing.modellergraph.filter.CondensedRelationshipContext;
import com.braintribe.model.processing.modellergraph.filter.RelationshipFilterer;
import com.braintribe.model.processing.modellergraph.filter.relationship.Relationship;

public class AllInlcludedTypeFilterer implements RelationshipFilterer<AllIncludedTypeFilter> {
	
	private Map<String, Set<GmType>> depCache = new HashMap<>();
	
	@Override
	public boolean matches(CondensedRelationshipContext relationshipContext, RelationshipFiltererContext filtererContext, AllIncludedTypeFilter relationshipFilter) {
		CondensedType toType = relationshipContext.getRelationship().getToType();
		for(CondensedType condensedType : relationshipContext.getModel().getTypes()){
			if(condensedType.getGmType().getTypeSignature().equals(toType.getGmType().getTypeSignature()))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean matches(Relationship relationship, RelationshipFiltererContext filtererContext, AllIncludedTypeFilter relationshipFilter) {
		GmType toType = relationship.getToType();
		
		Set<GmType> types = new HashSet<GmType>();
		GmMetaModel model = relationship.getModel();
//		if(depCache.containsKey(model.getGlobalId())) {
//			types.addAll(depCache.get(model.getGlobalId()));
//		}else {
			Set<GmType> types2 = getDependentTypes(model);
			depCache.put(model.getGlobalId(), types2);
			types.addAll(types2);
//		}
		
		for(GmType condensedType : types){
			if(condensedType.getTypeSignature().equals(toType.getTypeSignature()))
				return true;
		}
		return false;
	}
	
	private Set<GmType> getDependentTypes(GmMetaModel model){
		Set<GmType> types = new HashSet<GmType>();
		if(model.getDependencies() != null){
			for(GmMetaModel dep : model.getDependencies()){
				
//				if(depCache.containsKey(model.getGlobalId())) {
//					types.addAll(depCache.get(model.getGlobalId()));
//				}else {
					Set<GmType> types2 = getDependentTypes(dep);
					depCache.put(dep.getGlobalId(), types2);
					types.addAll(types2);
//				}
								
			}			
		}
		types.addAll(model.getTypes());
		return types;
	}
}
