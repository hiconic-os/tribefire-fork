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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmeDragAndDropView;
import com.braintribe.gwt.gmview.client.GmeEntityDragAndDropSource;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.Variable;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.TemplateBasedAction;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.sencha.gxt.dnd.core.client.DND.Feedback;
import com.sencha.gxt.dnd.core.client.DND.Operation;
import com.sencha.gxt.dnd.core.client.DndDragCancelEvent;
import com.sencha.gxt.dnd.core.client.DndDragMoveEvent;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.ListViewDropTarget;

/**
 * {@link ThumbnailPanel}'s DnD implementation.
 * @author michel.docouto
 *
 */
public class ThumbnailPanelDropTarget extends ListViewDropTarget<ImageResourceModelData> {
	
	private final ThumbnailPanel thumbnailPanel;
	private Element lastElement;
	private List<TemplateBasedAction> actions;
	private GenericEntity previousDefaultValue;
	private WorkbenchActionContext<TemplateBasedAction> workbenchActionContext;

	public ThumbnailPanelDropTarget(ThumbnailPanel thumbnailPanel) {
		super(thumbnailPanel.getImagesListView());
		this.thumbnailPanel = thumbnailPanel;
		
		setAllowSelfAsSource(true);
		setFeedback(Feedback.INSERT);
		setOperation(Operation.COPY);
	}
	
	@Override
	protected void showFeedback(DndDragMoveEvent event) {
		workbenchActionContext = null;
		if (!(event.getDragSource() instanceof GmeEntityDragAndDropSource)) {
			handleInvalid(event);
			return;
		}
		
		GmeEntityDragAndDropSource dragSource = (GmeEntityDragAndDropSource) event.getDragSource();
		
		super.showFeedback(event);
		
		if (!event.getStatusProxy().getStatus()) {
			cancelTarget();
			return;
		}
		
		cancelTarget();
		
		lastElement = thumbnailPanel.getImagesListView().findElement(event.getDragMoveEvent().getNativeEvent().getEventTarget().<Element>cast());
		if (lastElement == null) {
			handleInvalid(event);
			return;
		}
		lastElement.addClassName(GmeDragAndDropView.GME_ELEMENT_DROP_TARGET);

		actions = dragSource.getTemplateActions();
		if (GmeEntityDragAndDropSource.isAnyActionValid(actions, activeItem.getEntity(), dragSource)) {
			workbenchActionContext = dragSource.getDragAndDropWorkbenchActionContext();
			event.getStatusProxy().setStatus(true);
		} else
			handleInvalid(event);
	}
	
	@Override
	protected void onDragDrop(DndDropEvent event) {
		ThumbnailListViewSelectionModel<ImageResourceModelData> selectionModel = (ThumbnailListViewSelectionModel<ImageResourceModelData>) thumbnailPanel
				.getImagesListView().getSelectionModel();
		selectionModel.setDisableClickSelection(true);
		
		cancelTarget();
		GenericEntity dropEntity = activeItem.getEntity();
		Map<TemplateBasedAction, Variable> availableActionsMap = GmeEntityDragAndDropSource.handleDrop(dropEntity, actions, workbenchActionContext);
		
		Future<TemplateBasedAction> chosenActionFuture;
		if (availableActionsMap.size() <= 1 || thumbnailPanel.workbenchActionSelectionHandler == null)
			chosenActionFuture = new Future<>(availableActionsMap.keySet().stream().findFirst().get());
		else {
			chosenActionFuture = new Future<>();
			thumbnailPanel.workbenchActionSelectionHandler.handleActionSelection(new ArrayList<>(availableActionsMap.keySet()), chosenActionFuture);
			Scheduler.get().scheduleDeferred(() -> selectionModel.setDisableClickSelection(false));
		}
		
		chosenActionFuture.andThen(action -> {
			selectionModel.setDisableClickSelection(false);
			if (action == null) {
				insertIndex = -1;
				activeItem = null;
				return;
			}
			
			Variable actionVariable = availableActionsMap.get(action);
			workbenchActionContext.setWorkbenchAction(action);
			
			prepareDefaultValue(actionVariable, dropEntity);
			
			ModelAction modelAction = thumbnailPanel.workbenchActionHandlerRegistry.apply(workbenchActionContext);
			if (modelAction != null) {
				GmContentView view = workbenchActionContext.getPanel() instanceof GmContentView ? ((GmContentView) workbenchActionContext.getPanel())
						: null;
				modelAction.configureGmContentView(view);
				modelAction.perform(null);
				
				Object source = event.getSource();
				if (source instanceof GmeEntityDragAndDropSource && thumbnailPanel != ((GmeEntityDragAndDropSource) source).getView())
					((GmeEntityDragAndDropSource) source).markParentAsReloadPending(thumbnailPanel);
			}
			
			actionVariable.setDefaultValue(previousDefaultValue);
			
			insertIndex = -1;
			activeItem = null;
		}).onError(e -> selectionModel.setDisableClickSelection(false));
	}
	
	@Override
	protected void onDragCancelled(DndDragCancelEvent event) {
		super.onDragCancelled(event);
		cancelTarget();
	}
	
	private void handleInvalid(DndDragMoveEvent event) {
		event.getStatusProxy().setStatus(false);
		cancelTarget();
	}
	
	private void cancelTarget() {
		if (lastElement != null) {
			lastElement.removeClassName(GmeDragAndDropView.GME_ELEMENT_DROP_TARGET);
			lastElement = null;
		}
	}
	
	private void prepareDefaultValue(Variable variable, GenericEntity entity) {
		previousDefaultValue = (GenericEntity) variable.getDefaultValue();
		variable.detach();
		variable.setDefaultValue(entity);
	}

}
