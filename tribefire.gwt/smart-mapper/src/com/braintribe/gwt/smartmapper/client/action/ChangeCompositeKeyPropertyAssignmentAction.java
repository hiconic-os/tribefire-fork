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

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.smartmapper.client.PropertyAssignmentContext;
import com.braintribe.model.accessdeployment.smart.meta.CompositeInverseKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.CompositeKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.InverseKeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.KeyPropertyAssignment;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.transaction.NestedTransaction;

public class ChangeCompositeKeyPropertyAssignmentAction extends SmartMapperAction{
	
	protected boolean inverse = false;
	protected GenericEntity parent;
	private boolean remove = true;
	
	public ChangeCompositeKeyPropertyAssignmentAction(boolean remove, boolean inverse, GenericEntity parent){
		this.parent = parent;
		this.inverse = inverse;
		this.remove = remove;
	}
	
	@Override
	public boolean isVisible(PropertyAssignmentContext pac) {
		return true;
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		PersistenceGmSession session = propertyAssignmentContext.session;
		NestedTransaction nt = session.getTransaction().beginNestedTransaction();
		if(remove){
			if(inverse){
				CompositeInverseKeyPropertyAssignment cikpa = (CompositeInverseKeyPropertyAssignment) parent;
				cikpa.getInverseKeyPropertyAssignments().remove(propertyAssignmentContext.parentEntity);
			}else{
				CompositeKeyPropertyAssignment ckpa = (CompositeKeyPropertyAssignment) parent;
				ckpa.getKeyPropertyAssignments().remove(propertyAssignmentContext.parentEntity);
			}			
		}else{
			if(inverse){
				CompositeInverseKeyPropertyAssignment cikpa = (CompositeInverseKeyPropertyAssignment) parent;
				InverseKeyPropertyAssignment kpa = session.create(InverseKeyPropertyAssignment.T);
				cikpa.getInverseKeyPropertyAssignments().add(kpa);
			}else{
				CompositeKeyPropertyAssignment ckpa = (CompositeKeyPropertyAssignment) parent;
				KeyPropertyAssignment kpa = session.create(KeyPropertyAssignment.T);
				ckpa.getKeyPropertyAssignments().add(kpa);
			}
		}
		nt.commit();
	}

}
