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
package com.braintribe.devrock.api.ui.selection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.devrock.api.storagelocker.StorageLockerSlots;
import com.braintribe.devrock.api.ui.commons.UiSupport;
import com.braintribe.devrock.api.ui.editors.BooleanEditor;
import com.braintribe.devrock.api.ui.editors.FileEditor;
import com.braintribe.devrock.api.ui.listeners.ModificationNotificationListener;
import com.braintribe.devrock.plugin.DevrockPlugin;

public class CustomRepositoryConfigurationSelector {
	private BooleanEditor useStandardConfiguration;
	private FileEditor customConfiguration;	
	private Shell shell;
	private UiSupport uisSupport;
	private Font bigFont;
	
	private String currentlySelectedCustomConfiguration;
	private boolean currentlySelectedUsageOfStandadConfiguration;
	
	@Configurable @Required
	public void setBigFont(Font bigFont) {
		this.bigFont = bigFont;
	}

	@Configurable @Required
	public void setShell(Shell shell) {
		this.shell = shell;
	}
	
	public void setUisSupport(UiSupport uisSupport) {
		this.uisSupport = uisSupport;
	}
			
	public Composite createControl( Composite parent, String tag) {
		final Composite composite = new Composite(parent, SWT.NONE);
		
		int nColumns= 4;
        GridLayout layout= new GridLayout();
        layout.numColumns = nColumns;
        composite.setLayout( layout);
        
        layout.verticalSpacing=2;        
        
        Label respositorySelectionLabel = new Label( composite, SWT.NONE);
        respositorySelectionLabel.setText("repository configuration");
        respositorySelectionLabel.setFont(bigFont);
        
        respositorySelectionLabel.setLayoutData( new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
        
        useStandardConfiguration = new BooleanEditor();
        useStandardConfiguration.setSelectionListener( new SelectionAdapter() {
        	
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if (useStandardConfiguration.getSelection()) {
        			customConfiguration.setEnabled(false);
        			currentlySelectedUsageOfStandadConfiguration = true;
        		}
        		else {
        			customConfiguration.setEnabled(true);
        			currentlySelectedUsageOfStandadConfiguration = false;
        		}
        		super.widgetSelected(e);
        	}        	
        });

        useStandardConfiguration.setLabelToolTip("Allows the choice of using the standard or custom repository configuration for the analysis");
        useStandardConfiguration.setCheckToolTip( "If checked, the currently active configuration is used, else the specified custom configuration is used");
		Composite control = useStandardConfiguration.createControl(composite, "use standard configuration");		
		control.setLayoutData(new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
		
		boolean useStandardConfigurationValue = DevrockPlugin.envBridge().storageLocker().getValue( StorageLockerSlots.SLOT_AC_USE_STANDARD_CONFIGURATION, true);		
		useStandardConfiguration.setSelection( useStandardConfigurationValue);
		
		
		customConfiguration = new FileEditor( shell);
		customConfiguration.setExtensions( new String[] {"*.yaml"});
		customConfiguration.setLabelToolTip("The custom repository configuration to be used");
		customConfiguration.setCheckToolTip( "Selects a peristed repository configuration (yaml)");		
		control = customConfiguration.createControl(composite, "use alternative configuration");		
		control.setLayoutData(new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
		String customConfigurationValue = DevrockPlugin.envBridge().storageLocker().getValue( StorageLockerSlots.SLOT_AC_CUSTOM_CONFIGURATION, null);
		customConfiguration.setSelection( customConfigurationValue);
		
		customConfiguration.addListener( new ModificationNotificationListener() {
			
			@Override
			public void acknowledgeChange(Object sender, String value) {		
				if (sender == customConfiguration) {
					currentlySelectedCustomConfiguration = value;
				}
			}
		});
		
		if (useStandardConfigurationValue) {
			customConfiguration.setEnabled(false);
		}
       
        return composite;
	}

	
	public String getCurrentlySelectedCustomConfiguration() {
		return currentlySelectedCustomConfiguration;
	}

	public boolean getCurrentlySelectedUsageOfStandadConfiguration() {
		return currentlySelectedUsageOfStandadConfiguration;
	}


	public void dispose() {
	}
	
	
	
}
