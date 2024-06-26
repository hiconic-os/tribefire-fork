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
package com.braintribe.gwt.action.adapter.gxt.client;

import com.braintribe.gwt.action.client.Action;
import com.sencha.gxt.widget.core.client.button.CellButtonBase;

/**
 * This class is an adapter from Action to GXT Button.
 * This means that, once the Button is somehow changed, then the action will be adapted.
 * @author michel.docouto
 *
 */
public class ActionButtonAdapter {
	
	private Action action;
	
	/**
	 * Links an {@link Action} to a {@link CellButtonBase}.
	 */
	public static ActionButtonAdapter linkActionToButton(Action action, CellButtonBase<?> button) {
		return new ActionButtonAdapter(action, button);
	}
	
	protected ActionButtonAdapter(Action action, CellButtonBase<?> button) {
		this.action = action;
		
		button.addShowHandler(event -> ActionButtonAdapter.this.action.setHidden(false));
		
		button.addHideHandler(event -> ActionButtonAdapter.this.action.setHidden(true));
		
		button.addEnableHandler(event -> ActionButtonAdapter.this.action.setEnabled(true));
		
		button.addDisableHandler(event -> ActionButtonAdapter.this.action.setEnabled(false));
	}

}
