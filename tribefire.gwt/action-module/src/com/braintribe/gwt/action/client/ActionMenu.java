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
package com.braintribe.gwt.action.client;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.ioc.client.Configurable;

/**
 * The ActionMenu is a action that has inner actions.
 * Normally, this actions normally don't perform anything, and are used as Button with a menu,
 * and this menu has the inner actions.
 * @author dirk.scheffler
 *
 */
public class ActionMenu extends Action {

	private boolean syncEnabledAndHiddenWithActions = true;
	private List<Action> actions = new ArrayList<Action>();

	@Configurable
	public void addAction(Action action) {
		actions.add(action);
		if(syncEnabledAndHiddenWithActions)
		{
			action.addPropertyListener(new PropertyListener() {

				@Override
				public void propertyChanged(ActionOrGroup source, String property) {
					if(Action.PROPERTY_ENABLED.equals(property))
							syncEnablement();		
					if(Action.PROPERTY_HIDDEN.equals(property))
							syncHiddenStatus();
				}
			});
			
			syncEnablement();
			syncHiddenStatus();
		}
	}
	
	/**
	 * Iterates through all actions and disables the ActionMenu when no action is enabled
	 */
	protected void syncEnablement()
	{
		boolean groupEnabled = false;

		for(Action action : getActions())
		{
			if(action.getEnabled())
				groupEnabled=true;

			if(groupEnabled)
				break;
		}
		setEnabled(groupEnabled);	
	}

	/**
	 * Iterates through all actions and hides the ActionMenu when all actions are hidden
	 */
	protected void syncHiddenStatus()
	{
		boolean groupHidden = true;
		for(Action action : getActions())
		{
			if(!action.getHidden())
				groupHidden=false;

			if(!groupHidden)
				break;
		}
		setHidden(groupHidden);
	}

	public List<Action> getActions() {
		return actions;
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		//NOP
	}
	
	/**
	 * When this is true, the ActionMenu monitors enabled- and hidden-property-changes of its actions and sets its own status accordingly
	 * Defaults to true
	 */
	public void setSyncEnabledAndHiddenWithActions(boolean syncEnabledAndHiddenWithActions) {
		this.syncEnabledAndHiddenWithActions = syncEnabledAndHiddenWithActions;
	}
}
