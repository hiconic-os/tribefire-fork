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
package com.braintribe.gwt.templateeditor.client;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.LocalEntityProperty;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.value.Variable;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChangeValueManipulationRepresentation extends ManipulationRepresentation<ChangeValueManipulation> {
	
	private FlowPanel valueElement;
	private Variable variable;

	@Override
	public Future<Widget> renderManipulation(final ChangeValueManipulation manipulation) {
		final Future<Widget> future = new Future<>();
		if(manipulation.getNewValue() instanceof Variable){
			variable = (Variable) manipulation.getNewValue();
			session.listeners().entityProperty(variable, "name").add(this);
		}
		
		prepareOwnerElement(manipulation).andThen(result -> {
			FlowPanel manipulationEntry = result;
			
			FlowPanel delimiterElement = new FlowPanel();
			delimiterElement.setStyleName(DELIMITER_ELEMENT);
			delimiterElement.getElement().setInnerText(ASSIGN_DELIMITER);
			
			manipulationEntry.add(delimiterElement);
			
			valueElement = new FlowPanel();
			boolean isVariable = manipulation.getNewValue() instanceof Variable;
			valueElement.setStyleName(isVariable ? VARIABLE_ELMENT_CLASS : VALUE_ELMENT_CLASS);
			if(isVariable){
				valueElement.addDomHandler(event -> {
					setCurrentSelectedEntity(variable);
					event.preventDefault();
					event.stopPropagation();
				}, MouseDownEvent.getType());
			}
			valueElement.addDomHandler(event -> valueElement.addStyleName("selected"), MouseOverEvent.getType());
			valueElement.addDomHandler(event -> {
				if(!manipulationRepresentationListener.isElementSelected(variable))
					valueElement.removeStyleName("selected");
			},MouseOutEvent.getType());
			String valueText = manipulation.getNewValue() != null ? 
					(isVariable ? ((Variable)manipulation.getNewValue()).getName() : manipulation.getNewValue().toString()) : "null";
			valueElement.getElement().setInnerText(valueText);
			
			if(variable != null)
				manipulationRepresentationListener.putEntityElement(variable, ownerElement);
			
			manipulationEntry.add(valueElement);
			future.onSuccess(manipulationEntry);
		}).onError(future::onFailure);
		
		return future;
	}

	@Override
	public void noticeManipulation(Manipulation manipulation) {
		ChangeValueManipulation changeValueManipulation = (ChangeValueManipulation) manipulation;
		LocalEntityProperty localEntityProperty = (LocalEntityProperty) changeValueManipulation.getOwner();
		
		GenericEntity candidate = localEntityProperty.getEntity();
		if(candidate == entity){
			EntityType<GenericEntity> entityType = entity.entityType();
			String ownerName = entity.toSelectiveInformation();
			Object id = entity.getId();
			if(ownerName == null || ownerName.equals("")) ownerName = entityType.getShortName() + " (" + id.toString() + ")";
			ownerElement.getElement().setInnerText(ownerName);
		}else if(candidate == variable){
			Variable variable = (Variable) candidate;
			valueElement.getElement().setInnerText(variable.getName());
		}		
	}
	
	@Override
	public void changeValue(Object oldValue, Object newValue) {
		boolean isVariable = newValue instanceof Variable;
		valueElement.setStyleName(isVariable ? VARIABLE_ELMENT_CLASS : VALUE_ELMENT_CLASS);
		String valueText = newValue != null ? (isVariable ? ((Variable)newValue).getName() : newValue.toString()) : "null";
		if(isVariable){
			variable = (Variable) newValue;
			session.listeners().entityProperty(variable, "name").add(this);
		}
		valueElement.getElement().setInnerText(valueText);
	}
	
	@Override
	public String getManipulationType() {
		return "Change Value";
	}


}
