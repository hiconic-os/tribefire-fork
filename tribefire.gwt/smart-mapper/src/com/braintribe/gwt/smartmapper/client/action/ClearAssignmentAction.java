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
import com.braintribe.model.accessdeployment.smart.meta.KeyPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.LinkPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.OrderedLinkPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.QualifiedPropertyAssignment;

public class ClearAssignmentAction extends SmartMapperAction{
	
	public ClearAssignmentAction() {
		setName("Clear");
	}

	@Override
	public boolean isVisible(PropertyAssignmentContext pac) {
		return true;
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if(propertyAssignmentContext.parentEntity instanceof QualifiedPropertyAssignment){
			QualifiedPropertyAssignment qpa = (QualifiedPropertyAssignment) propertyAssignmentContext.parentEntity;
			qpa.setEntityType(null);
			qpa.setProperty(null);
		}else if(propertyAssignmentContext.parentEntity instanceof KeyPropertyAssignment){
			KeyPropertyAssignment kpa = (KeyPropertyAssignment) propertyAssignmentContext.parentEntity;
			kpa.setKeyProperty(null);
			kpa.setProperty(null);
		}else if(propertyAssignmentContext.parentEntity instanceof LinkPropertyAssignment){
			LinkPropertyAssignment lpa = (LinkPropertyAssignment) propertyAssignmentContext.parentEntity;
			
			lpa.setKey(null);
			lpa.setOtherKey(null);
			lpa.setLinkEntityType(null);
			lpa.setLinkKey(null);
			lpa.setLinkOtherKey(null);
			lpa.setLinkAccess(null);
			
			if(lpa instanceof OrderedLinkPropertyAssignment){
				OrderedLinkPropertyAssignment olpa = (OrderedLinkPropertyAssignment) lpa;
				olpa.setLinkIndex(null);
			}
		}
	}

}
