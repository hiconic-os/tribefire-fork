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

import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

/**
 * convenient helper to create tables in SWT/JFace
 * @author Pit
 *
 */
public class CommonTableViewer extends TableViewer{
	private Composite tableComposite;
	private CommonTableColumnData [] columnDati;
	private TableViewerColumn [] viewerColumns;

	public CommonTableViewer(Composite parent, int style) {
		super(parent, style);
		tableComposite = parent;
	}

	public CommonTableViewer(Composite parent, int style, CommonTableColumnData [] data) {
		super(parent, style);
		this.columnDati = data;
		tableComposite = parent;
	}
	
	public void setup(CommonTableColumnData [] data) {
		this.columnDati = data;
		setup();
	}
	
	public void setup() {
		viewerColumns = new TableViewerColumn [columnDati.length];
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		tableComposite.setLayout(tableColumnLayout);
		
		for (int i = 0; i < columnDati.length; i++) {
			CommonTableColumnData data = columnDati[i];
			TableViewerColumn viewerColumn = new TableViewerColumn(this, data.getColumnStyle());
					
			TableColumn column = viewerColumn.getColumn();
			column.setText( data.getColumnName());
			column.setResizable( data.isResizable());
			column.setMoveable( data.isMoveable());

			viewerColumn.setLabelProvider(data.getLabelProvider());
			viewerColumn.setEditingSupport( data.getEditingSupport());
			column.setToolTipText( data.getToolTip());
			
			tableColumnLayout.setColumnData( column, new ColumnWeightData( data.getColumnMinimalSize(), data.getColumnWeight(), data.isResizable()));
			
			viewerColumns[i] = viewerColumn;
		}
		ColumnViewerToolTipSupport.enableFor(this, ToolTip.NO_RECREATE);
	}
	
}
