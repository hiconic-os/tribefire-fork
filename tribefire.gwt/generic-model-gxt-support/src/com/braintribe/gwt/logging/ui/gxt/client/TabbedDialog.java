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
package com.braintribe.gwt.logging.ui.gxt.client;

import com.braintribe.gwt.gxt.gxtresources.orangeflattab.client.OrangeFlatTabPanelAppearance;
import com.braintribe.gwt.logging.client.resources.LoggingResources;
import com.google.gwt.core.shared.GWT;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public abstract class TabbedDialog extends Window {

	private TextButton closeButton;
	private TextButton maximizeButton;
	private TextButton restoreButton;
	protected PlainTabPanel tabPanel;

	public TabbedDialog() {
		setModal(true);
		setMaximizable(true);
		setHeaderVisible(false);
		setBodyBorder(false);
		setBorders(false);
		addStyleName("tabbedDialog");
		
		BorderLayoutContainer container = new BorderLayoutContainer();
		container.setCenterWidget(prepareTabPanel(), new MarginData());
		container.setSouthWidget(prepareToolBar(), new BorderLayoutData(61));
		updateButtons();
		add(container);
	}

	protected PlainTabPanel prepareTabPanel() {
		tabPanel = new PlainTabPanel(GWT.<OrangeFlatTabPanelAppearance>create(OrangeFlatTabPanelAppearance.class));
		tabPanel.setTabScroll(true);
		tabPanel.setBorders(false);
		tabPanel.setBodyBorder(false);
		return tabPanel;
	}

	private void updateButtons() {
		maximizeButton.setVisible(isMaximized() == false);
		restoreButton.setVisible(isMaximized() == true);
	}

	protected ToolBar prepareToolBar() {
		ToolBar toolBar = new ToolBar();
		toolBar.add(new FillToolItem());
		toolBar.add(prepareMaximizeButton());
		toolBar.add(prepareRestoreButton());
		toolBar.add(prepareCloseButton());
		return toolBar;
	}

	private TextButton prepareCloseButton() {
		closeButton = new TextButton(LocalizedText.INSTANCE.close());
		closeButton.setIconAlign(IconAlign.TOP);
		closeButton.setScale(ButtonScale.LARGE);
		closeButton.setIcon(LoggingResources.INSTANCE.delete());
		closeButton.addSelectHandler(event -> hide());
		return closeButton;
	}

	private TextButton prepareMaximizeButton() {
		maximizeButton = new TextButton(LocalizedText.INSTANCE.maximize());
		maximizeButton.setIconAlign(IconAlign.TOP);
		maximizeButton.setScale(ButtonScale.LARGE);
		maximizeButton.setIcon(LoggingResources.INSTANCE.maximize());
		maximizeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				maximize();
				updateButtons();
			}
		});
		return maximizeButton;
	}

	private TextButton prepareRestoreButton() {
		restoreButton = new TextButton(LocalizedText.INSTANCE.restore());
		restoreButton.setIconAlign(IconAlign.TOP);
		restoreButton.setScale(ButtonScale.LARGE);
		restoreButton.setIcon(LoggingResources.INSTANCE.restore());
		restoreButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				restore();
				updateButtons();
			}
		});
		return restoreButton;
	}
}
