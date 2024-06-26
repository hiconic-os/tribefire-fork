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
package com.braintribe.gwt.gme.selectresultpanel.client;

import com.braintribe.gwt.gme.selectresultpanel.client.SelectResultPanel.GridData;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmMouseInteractionEvent;
import com.google.gwt.event.dom.client.KeyEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.sencha.gxt.widget.core.client.event.CellClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;

public class SelectResultPanelMouseInteractionEvent implements GmMouseInteractionEvent {
	
	private GwtEvent<?> gridEvent;
	private SelectResultPanel selectResultPanel;
	
	public SelectResultPanelMouseInteractionEvent(GwtEvent<?> gridEvent, SelectResultPanel selectResultPanel) {
		this.gridEvent = gridEvent;
		this.selectResultPanel = selectResultPanel;
	}

	@Override
	public GmContentView getSource() {
		return selectResultPanel;
	}

	@Override
	public <T> T getElement() {
		GridData model = null;
		int cellIndex = 0;
		if (gridEvent instanceof CellClickEvent) {
			model = (GridData) ((CellClickEvent) gridEvent).getSource().getStore().get(((CellClickEvent) gridEvent).getRowIndex());
			cellIndex = ((CellClickEvent) gridEvent).getCellIndex();
		} else if (gridEvent instanceof CellDoubleClickEvent) {
			model = (GridData) ((CellDoubleClickEvent) gridEvent).getSource().getStore().get(((CellDoubleClickEvent) gridEvent).getRowIndex());
			cellIndex = ((CellDoubleClickEvent) gridEvent).getCellIndex();
		}
		
		if (model != null)
			return (T) selectResultPanel.getModelPath(model, cellIndex);
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
