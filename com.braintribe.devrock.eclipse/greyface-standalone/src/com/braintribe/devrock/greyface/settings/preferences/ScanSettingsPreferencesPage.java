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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.braintribe.codec.CodecException;
import com.braintribe.devrock.greyface.GreyfacePlugin;
import com.braintribe.devrock.greyface.settings.codecs.GreyfacePreferencesCodec;
import com.braintribe.logging.Logger;
import com.braintribe.model.malaclypse.cfg.preferences.gf.GreyFacePreferences;
import com.braintribe.plugin.commons.preferences.BooleanEditor;

public class ScanSettingsPreferencesPage extends PreferencePage implements IWorkbenchPreferencePage {
	private static Logger log = Logger.getLogger(ScanSettingsPreferencesPage.class);
	private GreyfacePlugin plugin = GreyfacePlugin.getInstance();
	private GreyFacePreferences gfPreferences = plugin.getGreyfacePreferences(false);
	private Font bigFont;
	
	private BooleanEditor skipOptionalsEditor;
	private BooleanEditor skipTestEditor;
	private BooleanEditor skipExistingEditor;
	private BooleanEditor overwriteExistingEditor;
	private BooleanEditor asyncScanModeEditor;
	private BooleanEditor applyCompileScopeEditor;
	
	private BooleanEditor validatePomsDuringScanEditor;
	

	@Override
	public void init(IWorkbench arg0) {
		setDescription("Devrock Greyface Scan Preferences");	
	}

	
	@Override
	public void dispose() {
		if (bigFont != null)
			bigFont.dispose();
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
		
		Label scanSettingsLabel = new Label( composite, SWT.NONE);
		scanSettingsLabel.setText( "Scan settings");
		scanSettingsLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false));
		scanSettingsLabel.setFont(bigFont);
		
		skipOptionalsEditor = new BooleanEditor();
    	skipOptionalsEditor.setSelection( gfPreferences.getExcludeOptionals());
    	Composite uiComposite = skipOptionalsEditor.createControl(composite,  GreyfacePreferenceConstants.SKIP_ARTIFACTS_MARKED_AS_OPTIONAL);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	
    	skipTestEditor = new BooleanEditor();
    	skipTestEditor.setSelection( gfPreferences.getExcludeOptionals());
    	uiComposite = skipTestEditor.createControl(composite, GreyfacePreferenceConstants.SKIP_ARTIFACTS_WITH_SCOPE_TEST);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	skipExistingEditor = new BooleanEditor();
    	skipExistingEditor.setSelection( gfPreferences.getExcludeExisting());
    	uiComposite = skipExistingEditor.createControl(composite, GreyfacePreferenceConstants.DO_NOT_SCAN_ARTIFACTS_THAT_EXIST_IN_TARGET_REPOSITORY);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	overwriteExistingEditor = new BooleanEditor();
    	overwriteExistingEditor.setSelection( gfPreferences.getOverwrite());
    	uiComposite = overwriteExistingEditor.createControl(composite, GreyfacePreferenceConstants.OVERWRITE_EXISTING_ARTIFACT_IN_TARGET_REPOSITORY);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	applyCompileScopeEditor = new BooleanEditor();
    	applyCompileScopeEditor.setSelection( gfPreferences.getApplyCompileScope());
    	uiComposite = applyCompileScopeEditor.createControl(composite, GreyfacePreferenceConstants.ACCEPT_COMPILE_SCOPE);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));    	    
    	
    	    	
    	validatePomsDuringScanEditor = new BooleanEditor();
    	validatePomsDuringScanEditor.setSelection( gfPreferences.getValidatePoms());
    	uiComposite = validatePomsDuringScanEditor.createControl(composite, GreyfacePreferenceConstants.VALIDATE_POMS_DURING_SCAN);
    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	
    	
    	if (plugin.isDebugActive()) {
    		Label debugSettingsLabel = new Label( composite, SWT.NONE);
	    	debugSettingsLabel.setText( "Debug settings");
	    	debugSettingsLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false));
	    	debugSettingsLabel.setFont(bigFont);
	    	
    		
	    	asyncScanModeEditor = new BooleanEditor();
	    	asyncScanModeEditor.setSelection( gfPreferences.getAsyncScanMode());
	    	uiComposite = asyncScanModeEditor.createControl( composite, GreyfacePreferenceConstants.SCAN_MODE);
	    	uiComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
    	}
    	
		return composite;
	}


	@Override
	protected void performApply() {
		gfPreferences.setExcludeOptionals( skipOptionalsEditor.getSelection());
		gfPreferences.setExcludeExisting( skipExistingEditor.getSelection());
		gfPreferences.setExcludeTest( skipTestEditor.getSelection());
		gfPreferences.setOverwrite( overwriteExistingEditor.getSelection());
		gfPreferences.setApplyCompileScope( applyCompileScopeEditor.getSelection());
		gfPreferences.setValidatePoms( validatePomsDuringScanEditor.getSelection());
		
		if (asyncScanModeEditor != null) {
			gfPreferences.setAsyncScanMode( asyncScanModeEditor.getSelection());
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
	
	

}
