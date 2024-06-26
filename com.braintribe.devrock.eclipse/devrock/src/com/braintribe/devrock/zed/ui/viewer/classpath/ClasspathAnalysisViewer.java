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
package com.braintribe.devrock.zed.ui.viewer.classpath;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.services.IDisposable;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.api.ui.tree.TreeViewerColumnResizer;
import com.braintribe.devrock.zarathud.model.common.Node;
import com.braintribe.devrock.zed.ui.ZedViewingContext;

public class ClasspathAnalysisViewer implements IDisposable {
	private ContentProvider contentProvider;
	private TreeViewer treeViewer;
	private Tree tree;	
	
	private UiSupport uiSupport; 
	private List<Node> nodes;
	@SuppressWarnings("unused")
	private ZedViewingContext context;
	private ViewLabelProvider viewLabelProvider;
	
	public ClasspathAnalysisViewer( ZedViewingContext context) {
		this.context = context;		
	}
	
	@Configurable @Required
	public void setUiSupport(UiSupport uiSupport) {
		this.uiSupport = uiSupport;				
	}
	
	@Configurable @Required
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	public Composite createControl( Composite parent, String tag) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);
		
		
		Composite treeLabelComposite = new Composite( composite, SWT.NONE);
        treeLabelComposite.setLayout( layout);
        treeLabelComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4,1));
        
        Label treeLabel = new Label( treeLabelComposite, SWT.NONE);
        treeLabel.setText( tag);
        treeLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, true, false, 4, 1));
        
        // tree for display
        Composite treeComposite = new Composite( composite, SWT.BORDER);	
        treeComposite.setLayout(layout);
		treeComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true, 4, 2));
				
		contentProvider = new ContentProvider();
		contentProvider.setupFrom( nodes);
		
		treeViewer = new TreeViewer( treeComposite, SWT.H_SCROLL | SWT.V_SCROLL);
		treeViewer.setContentProvider( contentProvider);
    	treeViewer.getTree().setHeaderVisible(true);
		
		// columns 
    	List<TreeViewerColumn> columns = new ArrayList<>();        	
    	
    	TreeViewerColumn nameColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
        nameColumn.getColumn().setText("Findings");
        nameColumn.getColumn().setToolTipText( "Findings of the classpath analysis");
        nameColumn.getColumn().setWidth(1000);
        
        viewLabelProvider = new ViewLabelProvider();
        viewLabelProvider.setUiSupport(uiSupport);
        viewLabelProvider.setUiSupportStylersKey("zed-classpath-analysis-view");
                
		nameColumn.setLabelProvider(new DelegatingStyledCellLabelProvider( viewLabelProvider));
        nameColumn.getColumn().setResizable(true);
        columns.add(nameColumn);
               
		ColumnViewerToolTipSupport.enableFor(treeViewer);
		
		tree = treeViewer.getTree();
	    
		GridData layoutData = new GridData( SWT.FILL, SWT.FILL);			
		int ht = (tree.getItemHeight() * 3) + tree.getHeaderHeight();
		
    	Point computedSize = tree.computeSize(SWT.DEFAULT, ht);
		layoutData.heightHint = computedSize.y;    					
		layoutData.widthHint = computedSize.x;// * 2;
    	tree.setLayoutData(layoutData);
		
  
		TreeViewerColumnResizer columnResizer = new TreeViewerColumnResizer();
    	columnResizer.setColumns( columns);		
    	columnResizer.setParent( treeComposite);
    	columnResizer.setTree( tree);    	
    	tree.addControlListener(columnResizer);
	
    	treeViewer.setInput( nodes);
    	//treeViewer.expandAll();
	    
        composite.pack();	
	
		return composite;
	}

	@Override
	public void dispose() {	
		viewLabelProvider.dispose();
	}
	
	
}
