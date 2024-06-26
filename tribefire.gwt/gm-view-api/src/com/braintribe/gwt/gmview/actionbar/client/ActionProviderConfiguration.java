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
package com.braintribe.gwt.gmview.actionbar.client;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.gmview.action.client.ActionGroup;
import com.braintribe.gwt.gmview.action.client.ActionTypeAndName;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

@JsType (namespace = InteropConstants.VIEW_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class ActionProviderConfiguration {
	
	private ActionGroup actionGroup;
	private List<Pair<ActionTypeAndName, ModelAction>> externalActions = new ArrayList<>();
	private List<Pair<String, ? extends Widget>> externalButtons;
	private GmContentView gmContentView;
	private List<ActionProviderConfigurationListener> actionProviderConfigurationListeners;
	
	public ActionGroup getActionGroup() {
		return actionGroup;
	}
	
	public void setActionGroup(ActionGroup actionGroup) {
		this.actionGroup = actionGroup;
	}
	
	public void addExternalActions(List<Pair<ActionTypeAndName, ModelAction>> externalActions) {
		if (externalActions != null && !externalActions.isEmpty()) {
			boolean fireEvent = !this.externalActions.isEmpty();
			this.externalActions.addAll(externalActions);
			if (fireEvent)
				fireExternalActionsAdded(externalActions);
		}
	}
	
	public List<Pair<ActionTypeAndName, ModelAction>> getExternalActions() {
		return externalActions;
	}
	
	@JsIgnore
	public List<Pair<String, ? extends Widget>> getExternalButtons() {
		return externalButtons;
	}
	
	@JsIgnore
	public void setExternalButtons(List<Pair<String, ? extends Widget>> externalButtons) {
		this.externalButtons = externalButtons;
	}
	
	public GmContentView getGmContentView() {
		return gmContentView;
	}
	
	public void setGmContentView(GmContentView gmContentView) {
		this.gmContentView = gmContentView;
	}
	
	public void addActionProviderConfigurationListener(ActionProviderConfigurationListener actionProviderConfigurationListener) {
		if (actionProviderConfigurationListeners == null)
			actionProviderConfigurationListeners = new ArrayList<ActionProviderConfigurationListener>();
		
		actionProviderConfigurationListeners.add(actionProviderConfigurationListener);
	}
	
	public void removeActionProviderConfigurationListener(ActionProviderConfigurationListener actionProviderConfigurationListener) {
		if (actionProviderConfigurationListeners != null) {
			actionProviderConfigurationListeners.remove(actionProviderConfigurationListener);
			if (actionProviderConfigurationListeners.isEmpty())
				actionProviderConfigurationListeners = null;
		}
	}
	
	private void fireExternalActionsAdded(List<Pair<ActionTypeAndName, ModelAction>> newExternalActions) {
		if (actionProviderConfigurationListeners != null) {
			for (ActionProviderConfigurationListener listener : actionProviderConfigurationListeners)
				listener.onFurtherExternalActionsAdded(newExternalActions);
		}
	}
	
	@JsType (namespace = InteropConstants.VIEW_NAMESPACE)
	public interface ActionProviderConfigurationListener {
		void onFurtherExternalActionsAdded(List<Pair<ActionTypeAndName, ModelAction>> newExternalActions);
	}

}
