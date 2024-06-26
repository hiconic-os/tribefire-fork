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
package com.braintribe.gwt.gme.assemblypanel.client;

import com.braintribe.gwt.gme.assemblypanel.client.model.AbstractGenericTreeModel;
import com.braintribe.gwt.gme.assemblypanel.client.resources.AssemblyPanelResources;
import com.braintribe.gwt.gmview.util.client.GMEUtil;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.sencha.gxt.widget.core.client.grid.GridView;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeNode;

public class AssemblyPanelTreeGridMouseOverHandler implements MouseOverHandler {
	
	private AssemblyPanel assemblyPanel;
	private AssemblyPanelTreeGrid assemblyPanelTreeGrid;
	private Element previousJointEl;
	
	public AssemblyPanelTreeGridMouseOverHandler(AssemblyPanel assemblyPanel, AssemblyPanelTreeGrid assemblyPanelTreeGrid) {
		this.assemblyPanel = assemblyPanel;
		this.assemblyPanelTreeGrid = assemblyPanelTreeGrid;
	}

	@Override
	public void onMouseOver(MouseOverEvent event) {
		NativeEvent nativeEvent = event.getNativeEvent();
		if (!Element.is(nativeEvent.getEventTarget()))
			return;
		
		GridView<?> view = assemblyPanelTreeGrid.getView();
		
		int rowIndex = view.findRowIndex(Element.as(nativeEvent.getEventTarget()));
		int colIndex = view.findCellIndex(Element.as(nativeEvent.getEventTarget()), null);
		if (rowIndex == - 1 || colIndex == -1)
			return;

		Element element = view.getCell(rowIndex, colIndex);
		if (element == null)
			return;
		
		handleExpandIconVisibility(rowIndex);
		
		if (!assemblyPanel.showContextMenu)
			return;

		NodeList<Element> elements = element.getElementsByTagName("table");
		if (elements == null || elements.getLength() == 0) {
			assemblyPanelTreeGrid.menuImageElement.removeFromParent();
			return;
		}
		
		Element tableElement = elements.getItem(0);
		if (tableElement == null || assemblyPanelTreeGrid.menuImageElement == null) {
			assemblyPanelTreeGrid.menuImageElement.removeFromParent();
			return;
		}
		
		Element imageElement = assemblyPanelTreeGrid.menuImageElement.getElementsByTagName("img").getItem(0);
		assemblyPanelTreeGrid.menuImageElement.getStyle().setWidth(10, Unit.PX);
		if (GMEUtil.containsCssDefinition(tableElement.getClassName(), AssemblyPanelResources.INSTANCE.css().tableForTreeWithFixedLayout())) { //node column
			if (imageElement != null) {
				AbstractGenericTreeModel model = assemblyPanelTreeGrid.getStore().get(rowIndex);
				int level = assemblyPanelTreeGrid.getTreeStore().getDepth(model);
				
				double right = (18 * (level - 1));
				ImageResource icon = model.getIcon();
				right += 21 + (icon == null ? 0 : icon.getWidth() - 1);
				imageElement.getStyle().setRight(right, Unit.PX);
				imageElement.getStyle().setTop(-4, Unit.PX);
			}
		} else {
			if (HorizontalAlignmentConstant.endOf(Direction.LTR).equals(assemblyPanelTreeGrid.getColumnModel().
					getColumn(colIndex).getHorizontalAlignment())) {
				assemblyPanelTreeGrid.menuImageElement.getStyle().setWidth(0, Unit.PX);
				if (imageElement != null)
					imageElement.getStyle().setRight(8, Unit.PX);
			} else if (imageElement != null)
				imageElement.getStyle().setRight(0, Unit.PX);
			
			if (imageElement != null)
				imageElement.getStyle().setTop(0, Unit.PX);
		}
		Element trElement = tableElement.getElementsByTagName("tr").getItem(0);
		trElement.insertAfter(assemblyPanelTreeGrid.menuImageElement, trElement.getLastChild());
		assemblyPanelTreeGrid.menuImageElement.getStyle().clearDisplay();
	}
	
	private void handleExpandIconVisibility(int rowIndex) {
		AbstractGenericTreeModel model = assemblyPanelTreeGrid.getStore().get(rowIndex);
		AssemblyPanelTreeGridView assemblyPanelTreeGridView = (AssemblyPanelTreeGridView) assemblyPanelTreeGrid.getView();
		TreeNode<AbstractGenericTreeModel> node = assemblyPanelTreeGridView.findNode(model);
		Element jointElement = assemblyPanelTreeGridView.getJointElement(node);
		if (previousJointEl != null)
			previousJointEl.getStyle().setVisibility(Visibility.HIDDEN);
		jointElement.getStyle().clearVisibility();
		previousJointEl = jointElement;
	}

}
