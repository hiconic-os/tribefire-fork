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
package com.braintribe.gwt.menu.client;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.InitializableBean;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

/**
 * Component responsible for creating a menu panel.
 */
public class CustomMenu extends VerticalLayoutContainer implements InitializableBean {
	private MenuPanel topPanel;
	private MenuPanel bottomPanel;
	private int itemWidth = 99;
	private int itemHeight = 19;
	private Iterable<? extends Action> topActions;
	private Iterable<? extends Action> bottomActions;
	
	public CustomMenu() {
		topPanel = new MenuPanel();
		bottomPanel = new MenuPanel();
	}
	
	@Override
	public void intializeBean() throws Exception {
		setBorders(false);
		topPanel.setItemHeight(itemHeight);
		topPanel.setItemWidth(itemWidth);
		topPanel.setBorders(false);
		
		bottomPanel.setItemHeight(itemHeight);
		bottomPanel.setItemWidth(itemWidth);
		bottomPanel.setBorders(false);
		
		if (topActions != null)
			topActions.forEach(action -> topPanel.addAction(action));
		
		if (bottomActions != null)
			bottomActions.forEach(action -> bottomPanel.addAction(action));
	}

	public MenuPanel getTopPanel() {
		return topPanel;
	}

	public MenuPanel getBottomPanel() {
		return bottomPanel;
	}
	
	@Configurable
	public void setTopActions(Iterable<? extends Action> actions) {
		this.topActions = actions;
		add(topPanel);
	}
	
	@Configurable
	public void setBottomActions(Iterable<? extends Action> actions) {
		this.bottomActions = actions;
		
		add(bottomPanel);
	}
	
	/**
	 * Configures the width for the created items.
	 * Defaults to 99.
	 */
	@Configurable
	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}
	
	/**
	 * Configures the height for the created items.
	 * Defaults to 19.
	 */
	@Configurable
	public void setItemHeight(int itemHeight) {
		this.itemHeight = itemHeight;
	}
}
