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
package com.braintribe.devrock.mungojerry.preferences;

import org.eclipse.core.runtime.IStatus;
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
import com.braintribe.commons.preferences.StringEditor;
import com.braintribe.devrock.mungojerry.plugin.Mungojerry;
import com.braintribe.model.malaclypse.cfg.preferences.gwt.GwtPreferences;
import com.braintribe.model.malaclypse.cfg.preferences.mj.MungojerryPreferences;



public class MungojerryPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
protected Font bigFont;
	
		
	private StringEditor gwtFileEditor;
	
	@Override
	public void init(IWorkbench arg0) {
		setPreferenceStore(Mungojerry.getInstance().getPreferenceStore());
		setDescription("Mungojerry's preferences page");
		
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
		
				
		
				
		Composite gwtGroup = new Composite( composite, SWT.NONE);
		gwtGroup.setLayout( layout);
		gwtGroup.setLayoutData(new GridData( SWT.FILL, SWT.FILL, true, false, 4, 1));
		
		Label gwtLabel = new Label( gwtGroup, SWT.NONE);
		gwtLabel.setText( "Settings for GWT");
		gwtLabel.setLayoutData( new GridData( SWT.LEFT, SWT.CENTER, false, false));
		gwtLabel.setFont(bigFont);
		
		String gwtFile = Mungojerry.getInstance().getMungojerryPreferences(false).getGwtPreferences().getAutoInjectLibrary();
		gwtFileEditor = new StringEditor();
		gwtFileEditor.setSelection( gwtFile);
		Composite gwtComposite = gwtFileEditor.createControl(gwtGroup, "&GWT model auto inject gwt-user jar");
		gwtComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
				        	
    	composite.pack();
		return composite;
	}

	@Override
	protected void performApply() {
		MungojerryPreferences preferences = Mungojerry.getInstance().getMungojerryPreferences(false);
		GwtPreferences gwtPreferences = preferences.getGwtPreferences();
		gwtPreferences.setAutoInjectLibrary( gwtFileEditor.getSelection());
		try {
			new GwtPreferencesCodec( Mungojerry.getInstance().getPreferenceStore()).decode(gwtPreferences);
		} catch (CodecException e) {
			Mungojerry.log(IStatus.ERROR, "cannot write preferences to IPreferencesStore as " + e.getLocalizedMessage());
			e.printStackTrace();
		}		
	}

	@Override
	public boolean performOk() {
		performApply();
		return super.performOk();
	}

	@Override
	public boolean performCancel() {
		MungojerryPreferences preferences = Mungojerry.getInstance().getMungojerryPreferences(false);
		GwtPreferences gwtPreferences = preferences.getGwtPreferences();
		gwtFileEditor.setSelection(gwtPreferences.getAutoInjectLibrary());
		return super.performCancel();
	}

	

}
