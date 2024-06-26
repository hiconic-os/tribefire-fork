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

import java.util.List;

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmMouseInteractionEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

public class ThumbnailPanelSelectionEvent implements GmMouseInteractionEvent {
	
	private SelectionChangedEvent<ImageResourceModelData> event;
	private ThumbnailPanel thumbnailPanel;
	
	public ThumbnailPanelSelectionEvent(SelectionChangedEvent<ImageResourceModelData> event, ThumbnailPanel thumbnailPanel) {
		this.event = event;
		this.thumbnailPanel = thumbnailPanel;
	}

	@Override
	public GmContentView getSource() {
		return this.thumbnailPanel;
	}

	@Override
	public <T> T getElement() {
		ImageResourceModelData model = null;
		List<ImageResourceModelData> models = event.getSelection();
		if (models != null && !models.isEmpty())
			model = models.get(0);
		
		return (T) thumbnailPanel.getModelPath(model);
	}

	@Override
	public int getNativeButton() {
		return 0;
	}

	@Override
	public boolean isAltKeyDown() {
		return false;
	}

	@Override
	public boolean isCtrlKeyDown() {
		return false;
	}

	@Override
	public boolean isShiftKeyDown() {
		return false;
	}

	@Override
	public boolean isMetaKeyDown() {
		return false;
	}

}
