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

import java.util.function.Supplier;

import com.google.gwt.user.client.ui.Widget;

public class TabbedWidgetContext implements ViewContext {

	private String name;
	private String description;
	private Supplier<? extends Widget> widgetSupplier;
	protected Widget widget;
	private int index;
	private boolean hideDefaultView;
	
	public TabbedWidgetContext(String name, String description, Supplier<? extends Widget> widgetSupplier) {
		this(name, description, widgetSupplier, -1);
	}

	/**
	 * @param index - The index where the item should be placed in the tabPanel. If -1 is set, then the element is put as in the last position.
	 */
	public TabbedWidgetContext(String name, String description, Supplier<? extends Widget> widgetSupplier, int index) {
		this(name, description, widgetSupplier, index, false);
	}
	
	/**
	 * @param index - The index where the item should be placed in the tabPanel. If -1 is set, then the element is put as in the last position.
	 * @param hideDefaultView - true for hiding the default PP.
	 */
	public TabbedWidgetContext(String name, String description, Supplier<? extends Widget> widgetSupplier, int index, boolean hideDefaultView) {
		super();
		this.name = name;
		this.description = description;
		this.widgetSupplier = widgetSupplier;
		this.index = index;
		this.hideDefaultView = hideDefaultView;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Widget getWidget() {
		if (widget == null)
			widget = widgetSupplier.get();
		
		return widget;
	}

	public Widget getWidgetIfProvided() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public boolean isHideDefaultView() {
		return hideDefaultView;
	}
}
