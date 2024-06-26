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
package com.braintribe.model.processing.modellergraph;

import com.braintribe.model.modellergraph.condensed.CondensedModel;
import com.braintribe.model.modellergraph.condensed.CondensedRelationship;
import com.braintribe.model.modellergraph.condensed.CondensedType;
import com.braintribe.model.processing.modellergraph.filter.CondensedRelationshipContext;


public class BasicCondensedRelationshipFilterContext implements CondensedRelationshipContext {
	private int order;
	private CondensedRelationship relationship;
	private CondensedRelationship originalRelationship;
	
	public BasicCondensedRelationshipFilterContext(int order,
			CondensedRelationship originalRelationship, CondensedRelationship relationship) {
		super();
		this.order = order;
		this.relationship = relationship;
		this.originalRelationship = originalRelationship;
	}

	@Override
	public CondensedModel getModel() {
		return relationship.getToType().getModel();
	}
	
	@Override
	public int getOrder() {
		return order;
	}
	
	@Override
	public CondensedRelationship getRelationship() {
		return relationship;
	}
	
	@Override
	public CondensedType getToType() {
		return relationship.getToType();
	}
	
	@Override
	public CondensedRelationship getOriginalRelationship() {
		return originalRelationship;
	}
}
