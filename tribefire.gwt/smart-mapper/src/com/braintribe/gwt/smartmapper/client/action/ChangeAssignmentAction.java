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
package com.braintribe.gwt.smartmapper.client.action;

import java.util.function.Predicate;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.smartmapper.client.PropertyAssignmentChangeContext;
import com.braintribe.gwt.smartmapper.client.util.TypeAndPropertyInfo;
import com.braintribe.model.accessdeployment.smart.meta.PropertyAssignment;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.transaction.NestedTransaction;
import com.google.gwt.core.shared.GWT;

public abstract class ChangeAssignmentAction extends SmartMapperAction{
	
	protected EntityType<? extends PropertyAssignment> assignmentType;
		
	public void setAssignmentType(EntityType<? extends PropertyAssignment> assignmentType) {
		this.assignmentType = assignmentType;
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		changeAssignment();
	}
	
	public void changeAssignment(){
			
		GWT.debugger();
		PersistenceGmSession session = propertyAssignmentContext.session;
		NestedTransaction nt = session.getTransaction().beginNestedTransaction();
		
		modelMetaDataEditor.onEntityType(TypeAndPropertyInfo.getTypeSignature(propertyAssignmentContext.entityType)).removePropertyMetaData(
				propertyAssignmentContext.propertyName, (Predicate<MetaData>) metaData -> metaData == propertyAssignmentContext.parentEntity);
//		property.getMetaData().remove(propertyAssignmentContext.entity);	
		
		PropertyAssignment pa = session.create(assignmentType);
		
//		UseCaseSelector selector = session.create(UseCaseSelector.T);
//		selector.setUseCase(myAccess.getExternalId());
//		pa.setSelector(selector);
		
		postProcessNewAssignment(prepareChangeContext(pa));
		
		modelMetaDataEditor.onEntityType(TypeAndPropertyInfo.getTypeSignature(propertyAssignmentContext.entityType))
				.addPropertyMetaData(propertyAssignmentContext.propertyName, pa);
//		property.getMetaData().add(pa);	
		
		nt.commit();		
	}
	
	public abstract void postProcessNewAssignment(PropertyAssignmentChangeContext pacc);
	
	public PropertyAssignmentChangeContext prepareChangeContext(PropertyAssignment pa){
		PropertyAssignmentChangeContext changeContext = new PropertyAssignmentChangeContext(propertyAssignmentContext);
		changeContext.newPropertyAssignment = pa;
		return changeContext;
	}

}
