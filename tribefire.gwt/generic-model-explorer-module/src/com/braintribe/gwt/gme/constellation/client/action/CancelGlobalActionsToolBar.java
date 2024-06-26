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

import java.util.List;

import com.braintribe.gwt.action.adapter.gxt.client.ButtonActionAdapter;
import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.gme.constellation.client.GlobalActionsToolBar;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class CancelGlobalActionsToolBar extends GlobalActionsToolBar {
	
	private List<Action> externalActions;
	
	@Configurable
	public void setExternalActions(List<Action> externalActions) {
		this.externalActions = externalActions;
	}
	
	@Override
	public void intializeBean() throws Exception {
		add(prepareToolBar());
	}
	
	private ToolBar prepareToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.add(new FillToolItem());
		//toolBar.setAlignment(HorizontalAlignment.RIGHT);
		toolBar.getElement().getStyle().setBackgroundColor("white");
		toolBar.getElement().getStyle().setBackgroundImage("none");
		toolBar.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
		
		if (externalActions != null)
			externalActions.forEach(action -> toolBar.add(createButton(action)));
		
		toolBar.add(createCloseButton());
		
		toolBar.setBorders(false);
		return toolBar;
	}
	
	private TextButton createCloseButton() {
		TextButton button = new TextButton();
		button.setScale(ButtonScale.LARGE);
		button.setIconAlign(IconAlign.TOP);
		button.setText(LocalizedText.INSTANCE.close());
		button.setIcon(ConstellationResources.INSTANCE.cancel());
		button.addSelectHandler(event -> getWindow(CancelGlobalActionsToolBar.this).hide());
		
		return button;
	}
	
	private Window getWindow(Widget widget) {
		if (widget instanceof Window)
			return (Window) widget;
		else
			return getWindow(widget.getParent());
	}
	
	private TextButton createButton(Action action) {
		TextButton button = new TextButton();
		button.setScale(ButtonScale.LARGE);
		button.setIconAlign(IconAlign.TOP);
		ButtonActionAdapter.linkActionToButton(true, action, button);
		return button;
	}

}
