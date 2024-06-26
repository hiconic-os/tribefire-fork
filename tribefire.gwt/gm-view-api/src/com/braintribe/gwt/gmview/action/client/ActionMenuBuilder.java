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
package com.braintribe.gwt.gmview.action.client;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmSelectionSupport;
import com.braintribe.gwt.gmview.client.GmSessionHandler;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;

public interface ActionMenuBuilder extends GmSessionHandler, Function<Folder, Future<Void>> {
	
	Menu getContextMenu(GmContentView view, List<Pair<String, ? extends Widget>> externalComponents, ActionGroup actionGroup, boolean filterExternal);
	ActionGroup prepareActionGroup(List<Pair<ActionTypeAndName, ModelAction>> knownActions, GmContentView gmContentView);
	void onSelectionChanged(ActionGroup actionGroup, GmSelectionSupport gmSelectionSupport);
	Set<ActionTypeAndName> updateActionGroup(ActionGroup actionGroup, List<Pair<ActionTypeAndName, ModelAction>> externalActions);
	List<Item> addExternalActionsToMenu(GmContentView view, Widget actionMenuWidget, List<ModelAction> externalActions);
	List<Item> updateMenu(GmContentView view, Widget actionMenuWidget, List<Pair<ActionTypeAndName, ModelAction>> externalActions, ActionGroup actionGroup);
	void setWorkbenchSession(PersistenceGmSession workbenchSession);
	void notifyDisposedView(Widget viewMenu);
	
}
