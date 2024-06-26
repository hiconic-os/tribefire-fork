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
import com.braintribe.model.accessdeployment.smart.meta.QualifiedPropertyAssignment;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.data.QualifiedProperty;
import com.google.gwt.dom.client.Style.TextAlign;

public class PropertyAssignmentTypeInput extends PropertyAssignmentInput{
	
	protected AbstractMappingElementsProvider typesProvider;
	
	public PropertyAssignmentTypeInput() {
		super();
		getElement().setAttribute("placeholder", "EntityTypeName");
		getElement().getStyle().setTextAlign(TextAlign.RIGHT);
		internalPropertyName = "entityType";
	}
	
	public void setTypesProvider(AbstractMappingElementsProvider typesProvider) {
		this.typesProvider = typesProvider;
	}
	
	@Override
	public void render(){
		if(pac.parentEntity != null){
			Property property = pac.parentEntity.entityType().getProperty(propertyNameOfAssignment);
			Object currentValue = property.get(pac.parentEntity);
			if(currentValue != null){
				if(currentValue instanceof GmEntityType){
					setText(getTypeName(((GmEntityType)currentValue).getTypeSignature()));
				}else if(currentValue instanceof QualifiedProperty){
					QualifiedProperty qpa = (QualifiedProperty) currentValue;
					if(qpa.getEntityType() != null){
						setText(getTypeName(qpa.getEntityType().getTypeSignature()));	
					}else{
						setText("");
					}				
				}
			}else{
				if(pac.parentEntity.entityType().isAssignableFrom(QualifiedPropertyAssignment.T)){
					QualifiedProperty qp = (QualifiedProperty)pac.parentEntity;
					if(qp.getEntityType() != null)
						setText(getTypeName(qp.getEntityType().getTypeSignature()));
					else if(qp.getProperty() != null && qp.getProperty().getDeclaringType() != null)
						setText(getTypeName(qp.getProperty().getDeclaringType().getTypeSignature()));
					else
						setText("");
				}else
					setText("");
			}
		}
		else
			setText("");
					
	}
	
	private String getTypeName(String typeSignature){
		return typeSignature.substring(typeSignature.lastIndexOf(".")+1, typeSignature.length());
	}
	
	@Override
	public EntityType<? extends GenericEntity> getType() {
		return gmEntityType;
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
		return typesProvider;
	}

}
