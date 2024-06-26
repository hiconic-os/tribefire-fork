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
package com.braintribe.gwt.thumbnailpanel.client;

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmMouseInteractionEvent;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.sencha.gxt.widget.core.client.event.BeforeExpandItemEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;

public class ThumbnailPanelMouseInteractionEvent implements GmMouseInteractionEvent {
	
	private GwtEvent<?> listEvent;
	private ThumbnailPanel thumbnailPanel;
	
	public ThumbnailPanelMouseInteractionEvent(GwtEvent<?> event, ThumbnailPanel thumbnailPanel) {
		this.listEvent = event;
		this.thumbnailPanel = thumbnailPanel;
	}

	@Override
	public GmContentView getSource() {
		return this.thumbnailPanel;
	}

	@Override
	public <T> T getElement() {
		ImageResourceModelData model = null;
		if (listEvent instanceof CellClickEvent)
			model = (ImageResourceModelData) ((CellClickEvent) listEvent).getSource().getStore().get(((CellClickEvent) listEvent).getRowIndex());
		else if (listEvent instanceof CellDoubleClickEvent)
			model = (ImageResourceModelData) ((CellDoubleClickEvent) listEvent).getSource().getStore().get(((CellDoubleClickEvent) listEvent).getRowIndex());
		else if (listEvent instanceof BeforeExpandItemEvent)
			model = (ImageResourceModelData) ((BeforeExpandItemEvent<?>) listEvent).getItem();
		
		if (model != null)
			return (T)thumbnailPanel.getModelPath(model);
		return null;
	}

	@Override
	public int getNativeButton() {
		if (listEvent instanceof KeyEvent)
			((KeyEvent<?>) listEvent).getNativeEvent().getCharCode();
		return -1;
	}

	@Override
	public boolean isAltKeyDown() {
		if (listEvent instanceof KeyEvent)
			return ((KeyEvent<?>) listEvent).isAltKeyDown();
		return false;
	}

	@Override
	public boolean isCtrlKeyDown() {
		if (listEvent instanceof KeyEvent)
			return ((KeyEvent<?>) listEvent).isControlKeyDown();
		return false;
	}

	@Override
	public boolean isShiftKeyDown() {
		if (listEvent instanceof KeyEvent)
			return ((KeyEvent<?>) listEvent).isShiftKeyDown();
		return false;
	}

	@Override
	public boolean isMetaKeyDown() {
		if (listEvent instanceof KeyEvent)
			return ((KeyEvent<?>) listEvent).isMetaKeyDown();
		return false;
	}

}
