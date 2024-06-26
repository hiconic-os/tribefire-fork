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

import com.braintribe.gwt.gmview.client.GmeEntityDragAndDropSource;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.TemplateBasedAction;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Format;
import com.sencha.gxt.dnd.core.client.DndDragStartEvent;
import com.sencha.gxt.dnd.core.client.ListViewDragSource;

/**
 * {@link ThumbnailPanel}'s implementation for DnD.
 * @author michel.docouto
 *
 */
public class ThumbnailPanelDragSource extends ListViewDragSource<ImageResourceModelData> implements GmeEntityDragAndDropSource {
	
	private ThumbnailPanel thumbnailPanel;
	//private List<TemplateBasedAction> actions;

	public ThumbnailPanelDragSource(ThumbnailPanel thumbnailPanel) {
		super(thumbnailPanel.getImagesListView());
		this.thumbnailPanel = thumbnailPanel;
	}
	
	@Override
	protected void onDragStart(DndDragStartEvent event) {
		super.onDragStart(event);
		
		List<ImageResourceModelData> draggedModels = thumbnailPanel.getImagesListView().getSelectionModel().getSelectedItems();
		
		//actions = thumbnailPanel.gmeDragAndDropSupport.getTemplateActionsSupplier().get();
		//if (actions != null && !actions.isEmpty()) {
		if (draggedModels != null) {
			event.setData(draggedModels);
			event.setCancelled(false);
			
			if (getStatusText() == null)
				event.getStatusProxy().update(SafeHtmlUtils.fromString(LocalizedText.INSTANCE.itemSelected(draggedModels.size())));
			else
				event.getStatusProxy().update(SafeHtmlUtils.fromString(Format.substitute(getStatusText(), draggedModels.size())));
		}
	}
	
	@Override
	public List<TemplateBasedAction> getTemplateActions() {
		//return actions;
		//RVE - as refresh actions take same time, we need take actions not on the beggining but when drop (in this case Actions are already refreshed)
		return thumbnailPanel.gmeDragAndDropSupport.getTemplateActionsSupplier().get();
	}
	
	@Override
	public Widget getView() {
		return thumbnailPanel;
	}
	
	@Override
	public WorkbenchActionContext<TemplateBasedAction> getDragAndDropWorkbenchActionContext() {
		return thumbnailPanel.getDragAndDropWorkbenchActionContext();
	}

}
