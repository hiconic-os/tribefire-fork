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
package com.braintribe.gwt.modeller.client.filter;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SelectiveInformation("${description}")
public interface RelationshipFilterWeavingContext extends GenericEntity{

	final EntityType<RelationshipFilterWeavingContext> T = EntityTypes.T(RelationshipFilterWeavingContext.class);
	
	String getFilterType();
	void setFilterType(String filterType);
	
	Object getValue();
	void setValue(Object value);
	
	String getPropertyName();
	void setPropertyName(String propertyName);
	
	Boolean getUseNegation();
	void setUseNegation(Boolean useNegation);
	
	String getDescription();
	void setDescription(String description);
	
	/*
	public EntityType<? extends RelationshipFilter> filterType;
	public Object value;
	public String propertyName;
	public boolean useNegation;
	*/
}