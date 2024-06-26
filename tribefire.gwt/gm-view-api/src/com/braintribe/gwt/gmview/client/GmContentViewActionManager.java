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
package com.braintribe.gwt.gmview.client;

import java.util.Collections;
import java.util.List;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.gmview.action.client.ActionGroup;
import com.braintribe.gwt.gmview.action.client.ActionMenuBuilder;
import com.braintribe.gwt.gmview.action.client.ActionPerformanceListener;
import com.braintribe.gwt.gmview.action.client.ActionTypeAndName;
import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;
import com.google.gwt.user.client.ui.Widget;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

@JsType (namespace = InteropConstants.VIEW_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public interface GmContentViewActionManager extends GmSelectionListener {
	
	void connect(GmContentView view);
	void addActionPeformanceListener(ActionPerformanceListener listener);
	void removeActionPeformanceListener(ActionPerformanceListener listener);
	
	@JsIgnore
	Widget getActionMenu(GmContentView view, List<Pair<String, ? extends Widget>> externalComponents, boolean filterExternal);
	
	void resetActions(GmContentView view);
	
	@JsIgnore
	List<? extends Widget> addExternalActions(GmContentView view, List<Pair<ActionTypeAndName, ModelAction>> externalActions);
	
	@JsIgnore
	Widget getActionMenuByGroup(GmContentView view, ActionGroup actionGroup);
	
	@JsIgnore
	void addExternalComponents(GmContentView view, List<Pair<String, ? extends Widget>> externalComponents);
	
	@JsIgnore
	List<Pair<String, ? extends Widget>> getExternalComponentsForView(GmContentView view);
	
	void notifyDisposedView(GmContentView view);
	List<Pair<ActionTypeAndName, ModelAction>> getKnownActionsList(GmContentView view);
	boolean isActionAvailable(ActionTypeAndName actionTypeAndName);
	ActionGroup getActionGroup(GmContentView view);
	
	@JsIgnore
	ActionMenuBuilder getActionMenuBuilder();
	
	/**
	 * @param view - the view for the action.
	 */
	default ModelAction getWorkWithEntityAction(GmContentView view) {
		return null;
	}
	
	/**
	 * @param view - the view for getting the actions.
	 */
	default List<Action> getActionsForView(GmContentView view) {
		return Collections.emptyList();
	}

}
