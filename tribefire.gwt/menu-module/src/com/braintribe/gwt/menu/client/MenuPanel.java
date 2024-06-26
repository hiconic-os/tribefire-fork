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

import java.util.function.Supplier;

import com.braintribe.gwt.action.adapter.common.client.LabelActionAdapter;
import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.menu.client.resources.MenuClientBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;

public class MenuPanel extends HorizontalLayoutContainer {
		
	static {
		MenuClientBundle.INSTANCE.css().ensureInjected();
	}
	
	private int itemWidth = 99;
	private int itemHeight = 19;
	private String additionalPanelStyle;
	private String additionalLabelStyle;
	
	public MenuPanel() {
		super();
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
	
	public void setAdditionalLabelStyle(String additionalLabelStyle) {
		this.additionalLabelStyle = additionalLabelStyle;
	}
	
	public void setAdditionalPanelStyle(String additionalPanelStyle) {
		this.additionalPanelStyle = additionalPanelStyle;
	}
	
	private void changeStyle(Label label, String style) {
		label.setStyleName(style);
	}
	
	/**
	 * Adds a new action as a Label.
	 */
	public void addAction(final Action action) {
		final ContentPanel panel = new ContentPanel();
		panel.setHeaderVisible(false);
		panel.setBorders(false);
		panel.setBodyBorder(false);
		panel.setWidth(itemWidth);
		panel.setHeight(itemHeight);
		
		String tooltip;
		if (action instanceof HasTooltipProvider && ((HasTooltipProvider) action).getTooltipProvider() != null) {
			try {
				tooltip = ((HasTooltipProvider) action).getTooltipProvider().get();
				if (tooltip != null && !tooltip.isEmpty())
					setToolTip(tooltip);
			} catch(Exception ex) {
				tooltip = action.getTooltip();
				if (tooltip != null && !tooltip.isEmpty())
					setToolTip(tooltip);
			}
		} else {
			tooltip = action.getTooltip();
			if (tooltip != null && !tooltip.isEmpty())
				setToolTip(tooltip);
		}
		
		if (!(action instanceof HasAsyncIconProvider)) {
			preparePanel(panel, action);
			return;
		}
		
		Supplier<Future<Image>> iconProvider = ((HasAsyncIconProvider) action).getIconProvider();
		if (iconProvider == null) {
			preparePanel(panel, action);
			return;
		}
		
		iconProvider.get().andThen(image -> {
			if (image != null)
				preparePanel(panel, action, image);
			else
				preparePanel(panel, action);
		}).onError(e -> {
			e.printStackTrace();
			preparePanel(panel, action);
		});
	}
	
	private void preparePanel(ContentPanel panel, Action action){
		final ImageResource icon = action.getIcon();
		final Image image;
		final AbstractImagePrototype imagePrototype;
		if (icon != null) {
			imagePrototype = AbstractImagePrototype.create(icon);
			image = new Image();
			imagePrototype.applyTo(image);
		} else {
			image = new Image();
			image.setUrl(GWT.getModuleBaseURL() + "clear.cache.gif");
			image.setWidth("16");
			image.setHeight("16");
			imagePrototype = null;
		}
		preparePanel(panel, action, image);
	}
	
	private void preparePanel(final ContentPanel panel, final Action action,final Image image){
		final HorizontalLayoutContainer hPanel = new HorizontalLayoutContainer();
		//hPanel.setHorizontalAlign(null);	
		
		image.addStyleName(MenuClientBundle.INSTANCE.css().menuImage());
		
		final Label label = new Label();
		int labelWidth = Math.max(0,itemWidth - image.getWidth());
		label.setWidth(Integer.toString(labelWidth));
		LabelActionAdapter.linkActionToLabel(action, label);
		label.setStyleName(MenuClientBundle.INSTANCE.css().menuBtn());
		if(additionalLabelStyle != null)
			label.addStyleName(additionalLabelStyle);
		
		MouseOverHandler mouseOverHandler = event -> {
			image.removeStyleName(MenuClientBundle.INSTANCE.css().menuImage());
			image.addStyleName(MenuClientBundle.INSTANCE.css().menuImageHover());
			changeStyle(label, MenuClientBundle.INSTANCE.css().menuBtnHover());
		};
		
		MouseOutHandler mouseOutHandler = event -> {
			image.removeStyleName(MenuClientBundle.INSTANCE.css().menuImageHover());
			image.addStyleName(MenuClientBundle.INSTANCE.css().menuImage());
			changeStyle(label, MenuClientBundle.INSTANCE.css().menuBtn());
		};
		
		image.addMouseOverHandler(mouseOverHandler);
		image.addMouseOutHandler(mouseOutHandler);
		
		image.addClickHandler(event -> {
			TriggerInfo triggerInfo = new TriggerInfo();
			triggerInfo.setWidget((Widget)event.getSource());
			action.perform(triggerInfo);
		});
		
		label.addMouseOverHandler(mouseOverHandler);
		label.addMouseOutHandler(mouseOutHandler);
		
		//hPanel.setSpacing(2);
		if (action.getName() != null)
			hPanel.setStyleName(MenuClientBundle.INSTANCE.css().menuBtnCursor());
		if (additionalPanelStyle != null)
			hPanel.addStyleName(additionalPanelStyle);
		HorizontalLayoutData margin = new HorizontalLayoutData();
		hPanel.add(image, margin);
		hPanel.add(label, margin);
		panel.add(hPanel);
		HorizontalLayoutData width = new HorizontalLayoutData();
		width.setWidth(50);
		super.add(panel, width);
		
		action.addPropertyListener((source, property) -> panel.setVisible(!source.getHidden()));
		forceLayout();
	}
	
}
