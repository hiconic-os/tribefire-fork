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
package com.braintribe.commons.preferences;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.braintribe.build.artifact.virtualenvironment.VirtualPropertyResolver;
import com.braintribe.cfg.Configurable;
import com.braintribe.devrock.virtualenvironment.listener.VirtualEnvironmentNotificationListener;

public class FileEditor extends AbstractEditor implements ModifyListener, VirtualEnvironmentNotificationListener {

	private Shell shell;
	private String start;
	private Text text;
	Button scanButton;
	private boolean startEnabled = true;
	private VirtualPropertyResolver resolver;
	private ModifyListener listener;

	@Configurable
	public void setSelection( String selection) {
		start = selection;
		if (text != null) {
			text.setText( start);
		}
	}
	
	public FileEditor( Shell shell) {
		this.shell = shell;			
	}
	
	@Configurable
	public void setListener(ModifyListener listener) {
		this.listener = listener;
		if (text != null) {
			text.addModifyListener(listener);
		}
	}
	
	@Configurable 
	public void setResolver(VirtualPropertyResolver resolver) {
		this.resolver = resolver;
	}

	public String scanForFile(Shell shell) {
		FileDialog dialog = new FileDialog(shell);
		String file = resolver != null ? resolver.resolve(text.getText()) : text.getText();	
		if (file != null) {
			dialog.setFileName( file);
			dialog.setFilterPath( new File(file).getAbsoluteFile().getParent());
		}
		String name = dialog.open();
		if (name == null)
			return null;
		return name;
	}
	
	public Composite createControl( Composite parent, String tag) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);
		
		Label label = new Label( composite, SWT.NONE);
		label.setText( tag);
		label.setLayoutData( new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		
		text = new Text( composite, SWT.NONE);
		text.setLayoutData( new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		if (start != null) {
			text.setText( start);
		}
		text.addModifyListener(this);
		if (listener != null) {
			text.addModifyListener(listener);
		}
		text.setToolTipText( resolver != null ? resolver.resolve(start) : start);	
		scanButton = new Button(composite, SWT.NONE);
		scanButton.setText("..");
		scanButton.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false, 1,1));
		scanButton.addSelectionListener( new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String value = scanForFile( shell);
				if (value != null) {					
					setSelection( value);
					text.setToolTipText( resolver != null ? resolver.resolve( value) : value);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {				
			}
		});		
		text.setEnabled(startEnabled);
		scanButton.setEnabled(startEnabled);
		return composite;
	}
	
	
	public String getSelection() {
		if (text != null)
			return text.getText();
		else
			return start;
	}
	
	public void setEnabled( boolean enable) {
		startEnabled = enable;
		if (text != null) {
			text.setEnabled(enable);
		}
		if (scanButton != null) {
			scanButton.setEnabled(enable);
		}
	}

	@Override
	public void modifyText(ModifyEvent arg0) {	
		text.setToolTipText( resolver != null ? resolver.resolve( text.getText()) : text.getText());
		broadcast(text.getText());
	}

	@Override
	public void acknowledgeOverrideChange() {
		modifyText(null);		
	}

	
	
}
