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
package com.braintribe.gwt.gmview.client.js.interop;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.gmview.action.client.ActionGroup;
import com.braintribe.gwt.gmview.action.client.ActionTypeAndName;
import com.braintribe.gwt.gmview.actionbar.client.ActionProviderConfiguration;
import com.braintribe.gwt.gmview.actionbar.client.GmViewActionProvider;
import com.braintribe.gwt.gmview.client.GmActionSupport;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmContentViewActionManager;
import com.braintribe.gwt.gmview.client.ModelAction;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Class needed for being able to export the following classes via JsInterop: {@link GmContentView},
 * {@link GmActionSupport} and {@link GmViewActionProvider}.
 */
@JsType(name = "GmActionContentView", namespace = InteropConstants.VIEW_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class GmActionContentViewInterop extends GmContentViewInterop implements GmActionSupportInterfaceInterop, GmViewActionProviderInterfaceInterop {
	private GmContentViewActionManager actionManager;
	private ActionProviderConfiguration actionProviderConfiguration;
	private List<Pair<ActionTypeAndName, ModelAction>> externalActions;
	
	@JsConstructor
	public GmActionContentViewInterop() {
		super();
	}

	@Override
	@JsMethod
	public void setActionManager(GmContentViewActionManager actionManager) {
		this.actionManager = actionManager;
	}

	@Override
	@JsMethod
	public void configureActionGroup(ActionGroup actionGroup) {
		//NOP
	}

	@Override
	@JsMethod
	public void configureExternalActions(List<Pair<ActionTypeAndName, ModelAction>> externalActions) {
		this.externalActions = externalActions;
		
		if (externalActions != null) {
			if (actionProviderConfiguration != null)
				actionProviderConfiguration.addExternalActions(externalActions);
			if (actionManager != null) //Already initialized
				actionManager.addExternalActions(getViewWidget(), externalActions);
		}
	}

	@Override
	@JsMethod
	public List<Pair<ActionTypeAndName, ModelAction>> getExternalActions() {
		return externalActions;
	}
	
	@Override
	@JsMethod
	public ActionProviderConfiguration getActions() {
		if (actionProviderConfiguration != null)
			return actionProviderConfiguration;
		
		actionProviderConfiguration = new ActionProviderConfiguration();
		actionProviderConfiguration.setGmContentView(getViewWidget());
		
		List<Pair<ActionTypeAndName, ModelAction>> knownActions = null;
		if (actionManager != null)
			knownActions = actionManager.getKnownActionsList(getViewWidget());
		
		if (knownActions != null || externalActions != null) {
			List<Pair<ActionTypeAndName, ModelAction>> allActions = new ArrayList<>();
			if (knownActions != null)
				allActions.addAll(knownActions);
			if (externalActions != null)
				allActions.addAll(externalActions);

			actionProviderConfiguration.addExternalActions(allActions);
		}			
		
		return actionProviderConfiguration;
	}
	
	private GmContentView getViewWidget() {
		return viewWidget != null ? viewWidget : this;
	}

	@Override
	@JsMethod
	public boolean isFilterExternalActions() {
		return false;
	}

}
