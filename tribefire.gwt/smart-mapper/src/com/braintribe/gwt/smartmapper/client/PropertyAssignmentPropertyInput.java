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
package com.braintribe.gwt.smartmapper.client;

import java.util.List;
import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.action.client.EntitiesProviderResult;
import com.braintribe.gwt.gmview.action.client.ParserResult;
import com.braintribe.gwt.gmview.client.parse.ParserArgument;
import com.braintribe.gwt.smartmapper.client.experts.AbstractMappingElementsProvider;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.data.QualifiedProperty;
import com.google.gwt.dom.client.Style.TextAlign;

public class PropertyAssignmentPropertyInput extends PropertyAssignmentInput{

	protected AbstractMappingElementsProvider propertiesProvider;
	
	public PropertyAssignmentPropertyInput() {
		super();
		getElement().setAttribute("placeholder", "propertyName");
		getElement().getStyle().setTextAlign(TextAlign.LEFT);
		internalPropertyName = "property";
	}
	
	public void setPropertiesProvider(AbstractMappingElementsProvider propertiesProvider) {
		this.propertiesProvider = propertiesProvider;
	}
	
	@Override
	public void render(){
		String propertyName = getPropertyName();
		if(propertyName != null)
			setText(propertyName);
		else
			setText("");
	}
		
	private String getPropertyName(){
		if(pac.parentEntity != null){
			try{
				Object propertyCandidate = pac.parentEntity.entityType().getProperty(propertyNameOfAssignment).get(pac.parentEntity);
				if(propertyCandidate instanceof GmProperty)
					return ((GmProperty) propertyCandidate).getName();
				else if(propertyCandidate instanceof QualifiedProperty){
					QualifiedProperty qp = (QualifiedProperty)propertyCandidate;
					if(qp.getProperty() != null){
						return qp.getProperty().getName();
					}else
						return null;
				}
			}catch(Exception ex){
				return null;
			}
		}
		return null;
	}
	
	@Override
	public EntityType<? extends GenericEntity> getType() {
		return gmPropertyType;
	}

	@Override
	public boolean loadExisitingValues() {
		return false;
	}
	
	@Override
	public boolean loadTypes() {
		return false;
	}

	@Override
	public Function<ParserArgument, List<ParserResult>> simpleTypesValuesProvider() {
		return null;
	}

	@Override
	public Function<ParserArgument, Future<EntitiesProviderResult>> entitiesFutureProvider() {
		return propertiesProvider;
	}

}
