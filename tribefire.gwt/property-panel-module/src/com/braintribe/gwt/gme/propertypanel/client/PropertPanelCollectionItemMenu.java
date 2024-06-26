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

import java.util.Collections;
import java.util.List;

import com.braintribe.gwt.gmview.action.client.RemoveFromCollectionAction;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class PropertPanelCollectionItemMenu extends Menu {
	
	private final PropertyPanel propertyPanel;
	private MenuItem removeItem;
	private RemoveFromCollectionAction removeAction;
	protected PropertyModel menuPropertyModel;
	
	public PropertPanelCollectionItemMenu(final PropertyPanel propertyPanel) {
		this.propertyPanel = propertyPanel;
		this.setMinWidth(180);
		SelectionHandler<Item> selectionHandler = event -> {
			Item item = event.getSelectedItem();
			if (item == null)
				return;
			
			int index = PropertPanelCollectionItemMenu.this.getData("index");
			propertyPanel.localManipulation = true;
			
			if (item == removeItem) {
				ModelPath modelPath = PropertyPanel.getModelPath(menuPropertyModel);
				ModelPathElement collectionElement = propertyPanel.getCollectionItemPathElement(index, menuPropertyModel, true);	
				modelPath.add(collectionElement);
				
				List<List<ModelPath>> modelPaths = propertyPanel.transformSelection(Collections.singletonList(modelPath));
				removeAction.updateState(modelPaths);
				removeAction.perform(null);
			} 
		};
				
		if (!propertyPanel.readOnly) {
			removeAction = new RemoveFromCollectionAction();
			removeAction.configureGmContentView(propertyPanel);
			removeItem = new MenuItem(removeAction.getName(), removeAction.getIcon());
			removeItem.addSelectionHandler(selectionHandler);
			this.add(removeItem);
		}
	}
	
	protected void updateMenu() {
		if (menuPropertyModel == null)
			return;
		
		GenericModelType modelValueElementType = menuPropertyModel.getValueElementType();
		if (!propertyPanel.readOnly && removeItem != null)
			removeItem.setVisible(menuPropertyModel.isEditable() && !propertyPanel.readOnly && modelValueElementType.isCollection());
						
	}
			
	private void updateHelperMenuPropertyModel(Object newValue) {
		menuPropertyModel.getParentEntityType().getProperty(menuPropertyModel.getPropertyName()).set(menuPropertyModel.getParentEntity(), newValue);
	}
	
	private native boolean isComponentHidden(Component component) /*-{
		return component.@com.sencha.gxt.widget.core.client.Component::hidden;
	}-*/;

}
