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

import com.braintribe.gwt.gmview.action.client.resources.GmViewActionResources;
import com.braintribe.gwt.gxt.gxtresources.orangeflattab.client.OrangeFlatTabPanelAppearance;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.AccessStack;
import com.sencha.gxt.widget.core.client.PlainTabPanel;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class TabbedGmEntityViewTabPanel extends PlainTabPanel{
	
	private ModelAction action;
	private boolean actionConfigured;

	private TabItemConfig tabIconConfig;

	private SimplePanel simplePanel;
	
	private Timer updateTimer;	
	
	private TabbedGmEntityView parent;
	
	public TabbedGmEntityViewTabPanel(TabbedGmEntityView parent) {		
		super(GWT.<OrangeFlatTabPanelAppearance>create(OrangeFlatTabPanelAppearance.class));
		this.parent = parent;
		setBodyBorder(false);
		setBorders(false);
		addStyleName("tabbedEntityView");
		
		addAttachHandler(event -> {
			if (actionConfigured || !event.isAttached() || action == null)
				return;
			
			actionConfigured = true;
			action.configureGmContentView(parent);
			
			QuickTip quickTip = new QuickTip(this);
			ToolTipConfig config = new ToolTipConfig();
			config.setDismissDelay(0);
			quickTip.update(config);
			
			StringBuilder builder = new StringBuilder();
			builder.append("<div class='").append(GmViewActionResources.INSTANCE.css().actionPosition()).append("'>");
			if (!action.getHidden()) {
				builder.append("<img class='").append(GmViewActionResources.INSTANCE.css().actionIcon());
				builder.append("' qtip='").append(action.getName()).append("' src='").append(action.getIcon().getSafeUri().asString()).append("'/>");
			}
			builder.append("</div>");
			
			tabIconConfig = new TabItemConfig();
			tabIconConfig.setHTML(SafeHtmlUtils.fromTrustedString(builder.toString()));
			//tabIconConfig.setEnabled(false);
			simplePanel = new SimplePanel();
			add(simplePanel, tabIconConfig);
			
			action.addPropertyListener((source, property) -> getUpdateTimer().schedule(100));
		});
	}
	
	public void setAction(ModelAction action) {
		this.action = action;
	}
	
	@Override
	protected void onClick(Event event) {
		if (action == null) {
			super.onClick(event);
			return;
		}
		
		XElement target = event.getEventTarget().cast();
		Element item = findItem(target);
		if (item != null) {
			Widget w = getWidget(itemIndex(item));
			if (w == simplePanel) {
				action.perform(null);
				return;
			}
		}
		
		super.onClick(event);
	}
	
	@Override
	public void onBrowserEvent(Event event) {
		XElement target = event.getEventTarget().cast();
		Element item = null;
		if (target != null) {
			item = findItem(target);
		}
		Widget widget = null;
		int index = -1;
		if (item != null) {
			index = itemIndex(item);
			try{
				widget = getWidget(index);
			}catch(Exception ex){
				widget = null;
			}
		}
		
		if (action != null) {
			tabIconConfig.setEnabled(true);
			if (widget != null && simplePanel == widget) {
				if (event.getTypeInt() == Event.ONMOUSEOVER)
					tabIconConfig.setEnabled(false);
			}
		}
		
		if (index > -1 && index < getWidgetCount())
			super.onBrowserEvent(event);
	}
	
	@Override
	public void setActiveWidget(Widget item, boolean fireEvents) {
		if(item != null) {
			if (action == null || item != simplePanel) {
				super.setActiveWidget(item, fireEvents);
				return;
			}
			
			if (item.getParent() != getContainer() || item == getActiveWidget())
				return;
	
			if (fireEvents) {
				BeforeSelectionEvent<Widget> event = BeforeSelectionEvent.fire(this, item);
				if (event != null && event.isCanceled())
					return;
			}
	
			getContainer().setActiveWidget(item);
	
			AccessStack<Widget> stack = getStack();
			if (stack == null) {
				stack = new AccessStack<Widget>();
				setStack(stack);
			}
			stack.add(item);
	
			if (fireEvents)
				SelectionEvent.fire(this, item);
		}
		delegateUpdates();
	}
	
	public void clearWidgets(){
//		for(Widget widget : parent.tabItemsPerEntityContext.values()){
//			widget.removeFromParent();
//			close(widget);
//		}
//		for(Widget widget : parent.tabItemsPerWidgetContext.values()){
//			widget.removeFromParent();
//			close(widget);
//		}
//		if(simplePanel != null)
//			remove(simplePanel);
		
		forceLayout();
	}
	
	public void closeSimplePanel() {
		if (simplePanel != null)
			close(simplePanel);
	}
	
	public void tryAddSimplePanel() {
		if (actionConfigured)
			add(simplePanel, tabIconConfig);
	}
	
	private Timer getUpdateTimer() {
		if (updateTimer != null)
			return updateTimer;
		
		updateTimer = new Timer() {
			@Override
			public void run() {
				StringBuilder builder = new StringBuilder();
				builder.append("<div class='").append(GmViewActionResources.INSTANCE.css().actionPosition()).append("'>");
				if (!action.getHidden()) {
					builder.append("<img class='").append(GmViewActionResources.INSTANCE.css().actionIcon());
					builder.append("' qtip='").append(action.getName()).append("' src='").append(action.getIcon().getSafeUri().asString()).append("'/>");
				}
				builder.append("</div>");
				tabIconConfig.setHTML(SafeHtmlUtils.fromTrustedString(builder.toString()));
				
				update(simplePanel, tabIconConfig);
			}
		};
		return updateTimer;
	}
	
	private native AccessStack<Widget> getStack() /*-{
		return this.@com.sencha.gxt.widget.core.client.TabPanel::stack;
	}-*/;

	private native void setStack(AccessStack<Widget> newStack) /*-{
		return this.@com.sencha.gxt.widget.core.client.TabPanel::stack = newStack;
	}-*/;
	
	private native void delegateUpdates() /*-{
		this.@com.sencha.gxt.widget.core.client.TabPanel::delegateUpdates()();
	}-*/;	

}
