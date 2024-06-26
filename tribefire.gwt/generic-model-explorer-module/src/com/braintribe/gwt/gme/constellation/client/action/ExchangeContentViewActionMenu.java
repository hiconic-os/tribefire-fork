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
package com.braintribe.gwt.gme.constellation.client.action;

import java.util.Arrays;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.ActionMenu;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;

public class ExchangeContentViewActionMenu extends ActionMenu{
	
	private int currentActionIndex = 0;
	
	public ExchangeContentViewActionMenu() {
		setHidden(false);
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar, ModelActionPosition.ContextMenu));
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		super.perform(triggerInfo);
		Action action = getActions().get(currentActionIndex);
		if (action != null)
			action.perform(triggerInfo);
	}
	
	public void raiseCurrentActionIndex(){
		currentActionIndex = ((currentActionIndex + 1) < getActions().size() ? currentActionIndex + 1 : 0);		
	}
	
	public void setCurrentAction(Action action) {
		if (action != null) {
			setIcon(action.getIcon());
			setHoverIcon(action.getHoverIcon());
			setName(action.getName());
		}
	}

}
