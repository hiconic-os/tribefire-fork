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
package com.braintribe.devrock.greyface.settings.preferences;

import java.io.File;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.braintribe.build.artifact.virtualenvironment.VirtualPropertyResolver;
import com.braintribe.codec.CodecException;
import com.braintribe.devrock.greyface.GreyfacePlugin;
import com.braintribe.devrock.greyface.settings.codecs.GreyfacePreferencesCodec;
import com.braintribe.logging.Logger;
import com.braintribe.model.malaclypse.cfg.preferences.gf.GreyFacePreferences;
import com.braintribe.plugin.commons.preferences.BooleanEditor;
import com.braintribe.plugin.commons.preferences.DirectoryEditor;



public class UploadSettingsPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {
	private static Logger log = Logger.getLogger(UploadSettingsPreferencesPage.class);
	private GreyfacePlugin plugin = GreyfacePlugin.getInstance();
	private GreyFacePreferences gfPreferences = plugin.getGreyfacePreferences(false);
	private Font bigFont;
	
	private BooleanEditor repairExistingEditor;
	private BooleanEditor purgePomEditor;
	private BooleanEditor enforceLicenseEditor;
	
	private BooleanEditor fakeUploadEditor;
	private DirectoryEditor fakeTargetDirectoryEditor;
	private BooleanEditor simulateErrorsEditor;
	private Image clearImage = ImageDescriptor.createFromFile( UploadSettingsPreferencesPage.class, "clear.gif").createImage();
	
	@Override
	public void init(IWorkbench arg0) {
		setDescription("Devrock Greyface Upload Preferences");	
	}
	
	@Override
	public void dispose() {
		bigFont.dispose();
		clearImage.dispose();
		/*
		VirtualEnvironmentPlugin virtualEnvironmentPlugin = VirtualEnvironmentPlugin.getInstance();		
		if (fakeTargetDirectoryEditor != null) {
			virtualEnvironmentPlugin.removeListener( fakeTargetDirectoryEditor);
		}
		*/
		super.dispose();
	}

	@Override
	protected Control createContents(Composite parent) {
		Font initialFont = parent.getFont();
		FontData [] fontDataBig = initialFont.getFontData();
		for (FontData data : fontDataBig) {
			data.setHeight( data.getHeight() + (data.getHeight() / 5));				
		}
		bigFont = new Font( getShell().getDisplay(), fontDataBig);
		
		Composite composite = new Composite(parent, SWT.NONE);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout( layout);
		
    	Label uploadSettingsLabel = new Label( composite, SWT.NONE);
		uploadSettingsLabel.setText( "Upload settings");
		uploadSettingsLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false));
		uploadSettingsLabel.setFont(bigFont);
		
		enforceLicenseEditor = new BooleanEditor();
    	enforceLicenseEditor.setSelection( gfPreferences.getEnforceLicenses());
    	Composite uiComposite = enforceLicenseEditor.createControl(composite, GreyfacePreferenceConstants.ENFORCE_LICENSES_PRIOR_TO_UPLOAD);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	
    	repairExistingEditor = new BooleanEditor();
    	repairExistingEditor.setSelection( gfPreferences.getRepair());
    	uiComposite = repairExistingEditor.createControl(composite, GreyfacePreferenceConstants.REPAIR_PARTS_OF_EXISTING_ARTIFACT_IN_TARGET_REPOSITORY);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	purgePomEditor = new BooleanEditor();
    	purgePomEditor.setSelection( gfPreferences.getPurgePoms());
    	uiComposite = purgePomEditor.createControl(composite, GreyfacePreferenceConstants.PURGE_POMS_OF_REPOSITORIES);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	/* 
    	 * 
    	 */
    	if (plugin.isDebugActive()) { 
    	
	    	Label debugSettingsLabel = new Label( composite, SWT.NONE);
	    	debugSettingsLabel.setText( "Debug settings");
	    	debugSettingsLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false));
	    	debugSettingsLabel.setFont(bigFont);
	    	
	    	fakeUploadEditor = new BooleanEditor();
	    	fakeUploadEditor.setSelection( gfPreferences.getFakeUpload());
	    	uiComposite = fakeUploadEditor.createControl(composite, GreyfacePreferenceConstants.FAKE_UPLOAD);
	    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
	    	
	    	VirtualPropertyResolver virtualPropertyResolver = plugin.getVirtualPropertyResolver();
	    	fakeTargetDirectoryEditor = new DirectoryEditor( getShell());
	    	fakeTargetDirectoryEditor.setSelection( gfPreferences.getFakeUploadTarget());
	    	fakeTargetDirectoryEditor.setPropertyResolver(virtualPropertyResolver);
			uiComposite = fakeTargetDirectoryEditor.createControl( composite, GreyfacePreferenceConstants.FAKE_UPLOAD_TARGET);
			//VirtualEnvironmentPlugin.getInstance().addListener( fakeTargetDirectoryEditor);
			uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false, 3, 1));
			
			Button clearUploadTargetButton = new Button( composite, SWT.NONE);
			clearUploadTargetButton.setImage(clearImage);
			clearUploadTargetButton.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false, 1, 1));
			clearUploadTargetButton.setToolTipText( "clear upload target directory");
			clearUploadTargetButton.addSelectionListener( new SelectionAdapter() {			

				@Override
				public void widgetSelected(SelectionEvent e) {
					VirtualPropertyResolver virtualPropertyResolver = plugin.getVirtualPropertyResolver();
					String directory = virtualPropertyResolver.resolve( fakeTargetDirectoryEditor.getSelection());
					File file = new File( directory);
					if (file.exists() && file.isDirectory()) {
						// 
						delete( file);						
					}
					
				}
				
			});
			
	    	simulateErrorsEditor = new BooleanEditor();
	    	simulateErrorsEditor.setSelection( gfPreferences.getSimulateErrors());
	    	uiComposite = simulateErrorsEditor.createControl(composite, GreyfacePreferenceConstants.SIMULATE_ERRORS);
	    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	}
		return composite;
	}

	@Override
	protected void performApply() {
		gfPreferences.setRepair( repairExistingEditor.getSelection());
		gfPreferences.setPurgePoms( purgePomEditor.getSelection());
		gfPreferences.setEnforceLicenses( enforceLicenseEditor.getSelection());
		
		if (fakeUploadEditor != null) {
			gfPreferences.setFakeUpload( fakeUploadEditor.getSelection());			
		}
		if (fakeTargetDirectoryEditor != null) {
			gfPreferences.setFakeUploadTarget( fakeTargetDirectoryEditor.getSelection());
		}
		if (simulateErrorsEditor != null) {
			gfPreferences.setSimulateErrors( simulateErrorsEditor.getSelection());
		}
		try {
			new GreyfacePreferencesCodec(plugin.getPreferenceStore()).decode(gfPreferences);
		} catch (CodecException e) {
			log.error("cannot store preferences", e);
		}		
	}

	@Override
	public boolean performOk() {
		performApply();
		return super.performOk();
	}

	private void delete( File file) {
		if (file == null || file.exists() == false)
			return;
		for (File child : file.listFiles()) {
			if (child.isDirectory()) {
				delete( child);
			} 
			child.delete();			
		}
	}	
}
