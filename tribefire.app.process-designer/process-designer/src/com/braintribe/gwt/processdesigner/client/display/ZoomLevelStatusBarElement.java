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
package com.braintribe.gwt.processdesigner.client.display;

import com.braintribe.gwt.gmview.client.GmSelectionSupport;
import com.braintribe.gwt.processdesigner.client.ProcessDesignerConfiguration;
import com.braintribe.gwt.processdesigner.client.resources.LocalizedText;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.LocalEntityProperty;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class ZoomLevelStatusBarElement extends ProcessDesignerStatusBarElement{
	
	private PersistenceGmSession session;
	private ProcessDesignerConfiguration configuration;
	
	public ZoomLevelStatusBarElement() {
		setName(LocalizedText.INSTANCE.zoom());
	}
	
	public void setSession(PersistenceGmSession session) {
		this.session = session;
	}
	
	public void setConfiguration(ProcessDesignerConfiguration configuration) {
		this.configuration = configuration;
	}
	
	@Override
	public void noticeManipulation(Manipulation manipulation) {
		if(manipulation instanceof ChangeValueManipulation){
			ChangeValueManipulation propertyManipulation = (ChangeValueManipulation)manipulation;
			LocalEntityProperty owner = (LocalEntityProperty)propertyManipulation.getOwner();
			GenericEntity entity = owner.getEntity();
			String propertyName = owner.getPropertyName();
			if(entity == configuration && propertyName.equals("scaleLevel")){
				getValue().getElement().setInnerText((configuration.getScaleLevel() * 100) + "%");
			}
		}
	}
	 
	@Override
	public void onSelectionChanged(GmSelectionSupport gmSelectionSupport) {
		//NOP
	}
	
	@Override
	public void handleDipose() {
		session.listeners().entity(configuration).remove(this);
	}
	
	@Override
	public void configure() {
		getValue().getElement().setInnerText((configuration.getScaleLevel() * 100) + "%");
		session.listeners().entity(configuration).add(this);
	}

}
