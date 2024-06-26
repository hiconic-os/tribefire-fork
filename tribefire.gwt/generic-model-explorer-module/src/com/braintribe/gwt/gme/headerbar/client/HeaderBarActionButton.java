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
package com.braintribe.gwt.gme.headerbar.client;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.ActionOrGroup;
import com.braintribe.gwt.action.client.ActionOrGroup.PropertyListener;
import com.braintribe.gwt.action.client.KnownProperties;
import com.braintribe.gwt.gmview.action.client.ActionMenuChangeListener;
import com.braintribe.gwt.gmview.action.client.ActionWithMenu;

public class HeaderBarActionButton extends HeaderBarButton{
	private Action action;
	private List<String> disabledPropertyList = new ArrayList<>();

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
		preparepropertyListener();
	}
	
	//RVE - allow to disasble auto refresh button depending on Actions property change
	//eg we have Icon defined via Workbench Folder, so we do not want to get it from Action
	public void addDisabledActionProperty(String propertyName) {
		disabledPropertyList.add(propertyName);	
	}

	public void removeDisabledActionProperty(String propertyName) {
		if (disabledPropertyList.contains(propertyName))
			disabledPropertyList.remove(propertyName);	
	}
	
	public void linkActionWithButton() {		
		if (action == null) {
			update();
			return;
		}
		
		if (!disabledPropertyList.contains(KnownProperties.PROPERTY_NAME))
			setText(action.getName());
		if (!disabledPropertyList.contains(KnownProperties.PROPERTY_TOOLTIP))
			setTooltip(action.getTooltip());
		//if (!disabledPropertyList.contains(KnownProperties.PROPERTY_HOVERICON))
		//	if (action.getHoverIcon() != null)
		//		setImageUrl(action.getHoverIcon().getSafeUri().asString());
		if (!disabledPropertyList.contains(KnownProperties.PROPERTY_ICON))
			if (action.getIcon() != null)
				setImageUrl(action.getIcon().getSafeUri().asString());
		if (!disabledPropertyList.contains(KnownProperties.PROPERTY_ENABLED))
			setEnabled(action.getEnabled());
		if (!disabledPropertyList.contains(KnownProperties.PROPERTY_HIDDEN))
			setVisible(!action.getHidden());	
	}
	
	private void preparepropertyListener() {
		if (action == null)
			return;
		
		action.addPropertyListener(new PropertyListener() {			
			@Override
			public void propertyChanged(ActionOrGroup source, String property) {
				if (KnownProperties.PROPERTY_ENABLED.equals(property))  {
					if (!disabledPropertyList.contains(KnownProperties.PROPERTY_ENABLED))
						setEnabled(action.getEnabled());				
				} else if (KnownProperties.PROPERTY_HIDDEN.equals(property)) {
					if (!disabledPropertyList.contains(KnownProperties.PROPERTY_HIDDEN)) 
						setVisible(!action.getHidden());					
				} else if (KnownProperties.PROPERTY_NAME.equals(property)) { 
					if (!disabledPropertyList.contains(KnownProperties.PROPERTY_NAME))
						setText(action.getName());
				//} else if (KnownProperties.PROPERTY_HOVERICON.equals(property)) { 
				//	if (!disabledPropertyList.contains(KnownProperties.PROPERTY_HOVERICON))
				//		if (action.getHoverIcon() != null)
				//			setImageUrl(action.getHoverIcon().getSafeUri().asString());
				} else if (KnownProperties.PROPERTY_ICON.equals(property)) { 
					if (!disabledPropertyList.contains(KnownProperties.PROPERTY_ICON))
						if (action.getIcon() != null)
							setImageUrl(action.getIcon().getSafeUri().asString());
				} else if (KnownProperties.PROPERTY_TOOLTIP.equals(property)) {
					if (!disabledPropertyList.contains(KnownProperties.PROPERTY_TOOLTIP))
						setTooltip(action.getTooltip());					
				}
			}
		});
		
		if (action instanceof ActionWithMenu) {
			((ActionWithMenu) action).addMenuChangeListener(new ActionMenuChangeListener() {				
				@Override
				public void onMenuChange(Action action) {
					setShowMenuOnClick(((ActionWithMenu) action).showMenuOnClick());
					update();
				}
			});
		}
	}

}
