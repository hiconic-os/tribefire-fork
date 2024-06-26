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
package com.braintribe.devrock.mungojerry.dialog.tab;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeItem;

import com.braintribe.build.gwt.ModuleCheckProtocol;
import com.braintribe.build.gwt.ModuleClassDependency;
import com.braintribe.devrock.mungojerry.dialog.MungojerryDialog;

public class GwtModuleScannerErrorTab extends GenericGwtViewerTab {
	private static final String MARKER_DEFERRED = "loading...";
	private static final String MARKER_PROTOCOL = "protocol";
	private static final String MARKER_DEPENDENCY = "dependency";
	private static final String TOKEN_NO_SOURCE_FOUND_FOR_CLASS = "no source found for class";
	private static final String TOKEN_FOUND_SOURCE_FOR_CLASS_BUT_NOT_WITHIN_ANY_INHERITED_MODULE = "found source for class but not within any inherited module";
	//
	
	private Image classImage = null;
	private Image errorImage = null;
	private Image infoImage = null;

	
	public GwtModuleScannerErrorTab(Display display) {		
		super(display);
		setColumnNames(new String [] {"Problems", });
		setColumnWeights( new int [] {300,});		
		
		ImageDescriptor imageDescriptor = ImageDescriptor.createFromFile( MungojerryDialog.class, "class.gif");
		classImage = imageDescriptor.createImage();
		
		imageDescriptor = ImageDescriptor.createFromFile( MungojerryDialog.class, "error.gif");
		errorImage = imageDescriptor.createImage();
		
		imageDescriptor = ImageDescriptor.createFromFile( MungojerryDialog.class, "information.png");
		infoImage = imageDescriptor.createImage();
	}
	
	
	
	@Override
	public void dispose() {
		classImage.dispose();
		errorImage.dispose();
		infoImage.dispose();
		super.dispose();
	}


	@Override
	public Composite createControl(Composite parent) {
		return super.createControl(parent);
	}
	
	private void buildItem( Object parent, ModuleCheckProtocol protocol) {
		
		for (ModuleClassDependency dependency: protocol.getUnsatisfiedDependencies()) {			
			TreeItem item = new TreeItem( tree, SWT.NONE);
			item.setText( new String[] {dependency.getClassName()});
			item.setImage( errorImage);
			item.setData( MARKER_DEPENDENCY, dependency);
			item.setData( MARKER_PROTOCOL, protocol);
			TreeItem subItem = new TreeItem( item, SWT.NONE);
			subItem.setText( MARKER_DEFERRED);					
		}				
	}
	
	public void setup( final ModuleCheckProtocol protocol) {		
		display.asyncExec( new Runnable() {
			
			@Override
			public void run() {
				tree.removeAll();				
				buildItem( tree, protocol);				
			}
		});							
	}
	
	@Override
	public void handleEvent(Event event) {		
		final TreeItem selectedItem = (TreeItem) event.item;
		TreeItem [] children = selectedItem.getItems();
		if (
			(children != null) &&
			(children.length == 1) 
		   ) {
			final TreeItem suspect = children[0];
			String name = suspect.getText(0);
			if (name.equalsIgnoreCase( MARKER_DEFERRED) == false)
				return;
			//
			//
			display.asyncExec( new Runnable() {
				
				@Override
				public void run() {
					suspect.dispose();					
					ModuleClassDependency dependency = (ModuleClassDependency) selectedItem.getData( MARKER_DEPENDENCY);
					if (dependency != null) {
						String text = dependency.getPathToSource() != null ? TOKEN_FOUND_SOURCE_FOR_CLASS_BUT_NOT_WITHIN_ANY_INHERITED_MODULE : TOKEN_NO_SOURCE_FOUND_FOR_CLASS;
						TreeItem reasonItem = new TreeItem( selectedItem, SWT.NONE);
						reasonItem.setText( text);
						reasonItem.setImage( infoImage);
						ModuleCheckProtocol protocol = (ModuleCheckProtocol) selectedItem.getData( MARKER_PROTOCOL);
						for (String dependingClass: protocol.getClassesDependingClass(dependency.getClassName())) {
							TreeItem subItem = new TreeItem( selectedItem, SWT.NONE);
							subItem.setText( dependingClass);
							subItem.setImage( classImage);
						}
					}				
				}
			});			
		}
		
	}	
}
