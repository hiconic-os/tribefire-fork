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
package com.braintribe.gwt.gme.propertypanel.client;

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmMouseInteractionEvent;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;

public class PropertyPanelMouseInteractionEvent implements GmMouseInteractionEvent {
	
	private GwtEvent<?> gridEvent;
	private PropertyPanel propertyPanel;
	
	public PropertyPanelMouseInteractionEvent(GwtEvent<?> gridEvent, PropertyPanel propertyPanel) {
		this.gridEvent = gridEvent;
		this.propertyPanel = propertyPanel;
	}

	@Override
	public GmContentView getSource() {
		return propertyPanel;
	}

	@Override
	public <T> T getElement() {
		PropertyModel model = null;
		if (gridEvent instanceof CellClickEvent)
			model = (PropertyModel) ((CellClickEvent) gridEvent).getSource().getStore().get(((CellClickEvent) gridEvent).getRowIndex());
		else if (gridEvent instanceof CellDoubleClickEvent)
			model = (PropertyModel) ((CellDoubleClickEvent) gridEvent).getSource().getStore().get(((CellDoubleClickEvent) gridEvent).getRowIndex());
		
		if (model != null)
			return (T) PropertyPanel.getModelPath(model);
		return null;
	}

	@Override
	public int getNativeButton() {
		if (gridEvent instanceof KeyEvent)
			((KeyEvent<?>) gridEvent).getNativeEvent().getCharCode();
		return -1;
	}

	@Override
	public boolean isAltKeyDown() {
		if (gridEvent instanceof KeyEvent)
			return ((KeyEvent<?>) gridEvent).isAltKeyDown();
		return false;
	}

	@Override
	public boolean isCtrlKeyDown() {
		if (gridEvent instanceof KeyEvent)
			return ((KeyEvent<?>) gridEvent).isControlKeyDown();
		return false;
	}

	@Override
	public boolean isShiftKeyDown() {
		if (gridEvent instanceof KeyEvent)
			return ((KeyEvent<?>) gridEvent).isShiftKeyDown();
		return false;
	}

	@Override
	public boolean isMetaKeyDown() {
		if (gridEvent instanceof KeyEvent)
			return ((KeyEvent<?>) gridEvent).isMetaKeyDown();
		return false;
	}

}
