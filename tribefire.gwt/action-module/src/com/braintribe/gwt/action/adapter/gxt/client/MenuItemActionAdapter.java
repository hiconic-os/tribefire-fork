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
package com.braintribe.gwt.action.adapter.gxt.client;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.ActionOrGroup;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.widget.core.client.menu.CheckMenuItem;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

/**
 * This class is an adapter from Action to GXT MenuItem.
 * 
 * @author michel.docouto
 *
 */
public class MenuItemActionAdapter {
	private Action action;
	private MenuItem item;
	private ImageResource lastIcon;
	private boolean ignoreVisibility = false;
	private boolean ignoreIcon = false;

	private class PropertyListenerImpl implements Action.PropertyListener {
		@Override
		public void propertyChanged(ActionOrGroup source, String property) {
			updateItem();
		}
	}

	private class SelectionHandlerImpl implements SelectionHandler<Item> {
		@Override
		public void onSelection(SelectionEvent<Item> event) {
			TriggerInfo triggerInfo = new TriggerInfo();
			triggerInfo.put(TriggerInfo.PROPERTY_COMPONENTEVENT, event);
			triggerInfo.setWidget(item);
			action.perform(triggerInfo);
		}
	}

	/**
	 * Use {@link MenuItemActionAdapter#linkActionToMenuItem(Action, MenuItem)} instead.
	 */
	private MenuItemActionAdapter(Action action, MenuItem item) {
		this(action, item, false);
	}

	protected SelectionHandler<Item> getSelectionHandler() {
		return new SelectionHandlerImpl();
	}

	/**
	 * Use {@link MenuItemActionAdapter#linkActionToMenuItem(Action, MenuItem, boolean)} instead.
	 */
	private MenuItemActionAdapter(Action action, MenuItem item, boolean ignoreVisibility) {
		this.item = item;
		this.action = action;
		this.ignoreVisibility = ignoreVisibility;

		action.addPropertyListener(new PropertyListenerImpl());
		item.addSelectionHandler(getSelectionHandler());
		updateItem();
	}

	protected MenuItemActionAdapter(boolean ignoreIcon, Action action, MenuItem item) {
		this.item = item;
		this.action = action;
		this.ignoreIcon = ignoreIcon;

		action.addPropertyListener(new PropertyListenerImpl());
		item.addSelectionHandler(getSelectionHandler());
		updateItem();
	}

	public void updateItem() {
		String id = action.getId();
		if (id != null && !id.isEmpty() && !id.equals(item.getId())) {
			item.setId(id);
		}
		String name = action.getName();
		if (name == null)
			name = action.getTooltip();
		if (name == null)
			name = "";
		if (!name.equals(item.getText())) {
			item.setText(name);
		}

		item.setEnabled(action.getEnabled());

		if (!ignoreIcon) {
			ImageResource icon = action.getIcon();
			if (icon != null && icon != lastIcon) {
				item.setIcon(icon);
				lastIcon = icon;
			}
		} else {
			ImageResource icon = action.getHoverIcon();
			if (icon != null && icon != lastIcon) {
				item.setIcon(icon);
				lastIcon = icon;
			}
		}

		if (action.getTooltip() != null) {
			item.setToolTip(action.getTooltip());
		}

		String shortcut = action.get("keyConfiguration");
		if (shortcut != null && !shortcut.isEmpty()) {
			String html = item.getHTML();
			StringBuilder builder = new StringBuilder();
			builder.append(html);
			builder.append("<span class='gmeMenuItemShortcut'>").append(shortcut).append("</span>");
			SafeHtml safeHhtml = SafeHtmlUtils.fromTrustedString(builder.toString());
			item.setHTML(safeHhtml);
		}

		if (item instanceof CheckMenuItem) {
			CheckMenuItem checkItem = (CheckMenuItem) item;
			checkItem.setChecked(action.getToggled());
		}

		if (!ignoreVisibility)
			item.setVisible(!action.getHidden());

		item.setData("action", action);
	}

	public static MenuItemActionAdapter linkActionToMenuItem(Action action, MenuItem item) {
		return new MenuItemActionAdapter(action, item);
	}

	/**
	 * ignoreVisibility - true for ignoring visibility changes.
	 */
	public static MenuItemActionAdapter linkActionToMenuItem(Action action, MenuItem item, boolean ignoreVisibility) {
		return new MenuItemActionAdapter(action, item, ignoreVisibility);
	}

	/**
	 * ignoreIcon - true for ignoring icon changes.
	 */
	public static MenuItemActionAdapter linkActionToMenuItem(boolean ignoreIcon, Action action, MenuItem item) {
		return new MenuItemActionAdapter(ignoreIcon, action, item);
	}
}
