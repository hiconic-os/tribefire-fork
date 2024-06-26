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
package com.braintribe.gwt.processdesigner.client.event;

import com.braintribe.gwt.processdesigner.client.ProcessDesignerRenderer;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.processing.session.api.notifying.NotifyingGmSession;

public class ProcessElementMonitoring {
	
	GenericEntity parentEntity;
	GenericEntity entity;
	ManipulationListener manipulationListener;
	ProcessDesignerRenderer renderer;
	NotifyingGmSession session;
	
	public ProcessElementMonitoring(GenericEntity entity, GenericEntity parentEntity) {
		this.entity = entity;
		this.parentEntity = parentEntity;
	}
	
	public void setSession(NotifyingGmSession session) {
		this.session = session;
	}
	
	public void setRenderer(ProcessDesignerRenderer renderer) {
		this.renderer = renderer;
	}
	
	public GenericEntity getParentEntity() {
		return parentEntity;
	}
	
	public void init(){
		manipulationListener = new ManipulationListener() {
			@Override
			public void noticeManipulation(Manipulation manipulation) {
				renderer.noticeManipulation(manipulation, entity);	
			}
		};
		if(entity != null)
			session.listeners().entity(entity).add(manipulationListener);
	}
	
	public void dispose(){
		if(entity != null)
			session.listeners().entity(entity).remove(manipulationListener);
	}

}
