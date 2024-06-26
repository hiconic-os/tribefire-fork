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
package com.braintribe.plugin.commons.tableviewer;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.swt.SWT;

/**
 * configuration data for the {@link CommonTableViewer}
 * 
 * @author Pit
 *
 */
public class CommonTableColumnData {

	private String columnName;
	private int columnWeight;
	private int columnMinimalSize;
	private ColumnLabelProvider labelProvider;
	private EditingSupport editingSupport;
	private int columnStyle;
	private boolean resizable = true;
	private boolean moveable = true;
	private String toolTip;
	
	public CommonTableColumnData(String name, int columnMinimalSize, int weight, String toolTip, ColumnLabelProvider labelProvider) {
		this.columnName = name;
		this.columnWeight = weight;
		this.toolTip = toolTip;
		this.labelProvider = labelProvider;		
		this.columnStyle = SWT.NONE;
		this.columnMinimalSize = columnMinimalSize;
	}
	
	public CommonTableColumnData(String name, int columnMinimalSize, int weight, String toolTip, ColumnLabelProvider labelProvider, EditingSupport editingSupport) {
		this.columnName = name;
		this.columnWeight = weight;
		this.columnMinimalSize = columnMinimalSize;
		this.toolTip = toolTip;
		this.labelProvider = labelProvider;
		this.editingSupport = editingSupport;
		this.columnStyle = SWT.NONE;
	}
	public CommonTableColumnData(String name, int columnMinimalSize, int weight, String toolTip, ColumnLabelProvider labelProvider, EditingSupport editingSupport, int columnStyle, boolean resizeable, boolean moveable) {
		this.columnName = name;
		this.columnWeight = weight;
		this.columnMinimalSize = columnMinimalSize;
		this.toolTip = toolTip;
		this.labelProvider = labelProvider;
		this.editingSupport = editingSupport;
		this.columnStyle = columnStyle;		
		this.resizable = resizeable;
		this.moveable = moveable;
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnWeight() {
		return columnWeight;
	}
	public void setColumnWeight(int columnWeight) {
		this.columnWeight = columnWeight;
	}
	public ColumnLabelProvider getLabelProvider() {
		return labelProvider;
	}
	public void setLabelProvider(ColumnLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}
	public EditingSupport getEditingSupport() {
		return editingSupport;
	}
	public void setEditingSupport(EditingSupport editingSupport) {
		this.editingSupport = editingSupport;
	}

	public int getColumnStyle() {
		return columnStyle;
	}

	public void setColumnStyle(int columnStyle) {
		this.columnStyle = columnStyle;
	}

	public boolean isResizable() {
		return resizable;
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}

	public int getColumnMinimalSize() {
		return columnMinimalSize;
	}

	public void setColumnMinimalSize(int columnMinimalSize) {
		this.columnMinimalSize = columnMinimalSize;
	}

	public String getToolTip() {
		return toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
	
	
	
}
