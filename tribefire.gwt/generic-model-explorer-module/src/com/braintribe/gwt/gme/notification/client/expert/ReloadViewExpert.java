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
package com.braintribe.gwt.gme.notification.client.expert;

import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.verticaltabpanel.client.VerticalTabElement;
import com.braintribe.gwt.gmview.client.ReloadableGmView;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.processing.notification.api.CommandExpert;
import com.braintribe.model.uicommand.ReloadView;
import com.google.gwt.user.client.ui.Widget;

/**
 * Expert responsible for the implementation of the {@link ReloadView} command.
 * @author michel.docouto
 *
 */
public class ReloadViewExpert implements CommandExpert<ReloadView> {
	
	private ExplorerConstellation explorerConstellation;
	
	/**
	 * Configures the required {@link ExplorerConstellation} used for getting what is the currentView.
	 */
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
		//test();
		//testReloadAll();
	}

	@Override
	public void handleCommand(ReloadView command) {
		if (command.getReloadAll())
			markViewsWithReloadPending();
		
		VerticalTabElement selectedElement = explorerConstellation.getVerticalTabPanel().getSelectedElement();
		if (selectedElement == null)
			return;
		
		Widget currentView = selectedElement.getWidget();
		if (currentView instanceof ReloadableGmView)
			((ReloadableGmView) currentView).reloadGmView();
	}
	
	private void markViewsWithReloadPending() {
		explorerConstellation.getVerticalTabPanel().getTabElements().stream().filter(el -> el.getWidgetIfSupplied() instanceof ReloadableGmView)
				.forEach(el -> ((ReloadableGmView) el.getWidgetIfSupplied()).setReloadPending(true));
	}
	
	/*private void test() {
		new Timer() {
			@Override
			public void run() {
				ReloadView rv = ReloadView.T.create();
				handleCommand(rv);
			}
		}.scheduleRepeating(10000);
	}*/
	
	/*private void testReloadAll() {
		new Timer() {
			@Override
			public void run() {
				ReloadView rv = ReloadView.T.create();
				rv.setReloadAll(true);
				handleCommand(rv);
			}
		}.schedule(100000);
	}*/

}
