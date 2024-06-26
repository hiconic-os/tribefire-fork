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
package com.braintribe.devrock.tbrunner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import com.braintribe.devrock.api.ui.editors.StringEditor;
import com.braintribe.devrock.eclipse.model.identification.EnhancedCompiledArtifactIdentification;
import com.braintribe.devrock.importer.scanner.ui.QuickImportAction;
import com.braintribe.devrock.importer.scanner.ui.QuickImportDialog;
import com.braintribe.devrock.plugin.DevrockPlugin;


public class TbWizardArtifactTab extends SelectionAdapter implements ArtifactSelectionBroadcaster, ArtifactSelectionListener {
	
	private static final String paddingString = "                       ";
	private static final int padding = 12;

	private EnhancedCompiledArtifactIdentification selectedTargetArtifact;
	private Button targetBrowseButton;
	private StringEditor targetGroupIdEditor;
	private StringEditor targetArtifactIdEditor;
	private StringEditor targetVersionEditor;
	private List<ArtifactSelectionListener> listeners = new ArrayList<ArtifactSelectionListener>();
	
	private Shell parentShell;
	
	public TbWizardArtifactTab(Shell parentShell) {
		this.parentShell = parentShell;
	}
	
	public TbWizardArtifactTab(Shell parentShell, EnhancedCompiledArtifactIdentification artifact) {
		this.parentShell = parentShell;
		selectedTargetArtifact = artifact;
	}
		
	

	@Override
	public void addArtifactSelectionListener(ArtifactSelectionListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeArtifactSelectionListener(ArtifactSelectionListener listener) {
		listeners.remove( listener);
		
	}

	public Composite createControl( Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		
		int nColumns= 4;
        GridLayout layout= new GridLayout();
        layout.numColumns = nColumns;
        composite.setLayout( layout);
        composite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true));
        
        
    	targetGroupIdEditor = new StringEditor();
    	Composite targetGroupIdComposite = targetGroupIdEditor.createControl( composite, pad( "Group Id:"));
		targetGroupIdComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 3, 1));			
		if (selectedTargetArtifact != null) {
			targetGroupIdEditor.setSelection( selectedTargetArtifact.getGroupId());
		}

		targetBrowseButton = new Button( composite, SWT.NONE);
		targetBrowseButton.setText( "..");
		targetBrowseButton.setLayoutData( new GridData( SWT.RIGHT, SWT.FILL, false, false, 1, 1));
		targetBrowseButton.addSelectionListener( this);
    	
		targetArtifactIdEditor = new StringEditor();
    	Composite targetArtifactIdComposite = targetArtifactIdEditor.createControl( composite, pad( "Artifact Id:"));
		targetArtifactIdComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
		if (selectedTargetArtifact != null) {
			targetArtifactIdEditor.setSelection( selectedTargetArtifact.getArtifactId());
		}
		    	
		targetVersionEditor = new StringEditor();
    	Composite targetVersionComposite = targetVersionEditor.createControl( composite, pad( "Version:"));
		targetVersionComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, true, false, 4, 1));
		if (selectedTargetArtifact != null) {		
			targetVersionEditor.setSelection( selectedTargetArtifact.getVersion().asString());		
		}
                
        return composite;
	}
	
	private String pad( String tag) {
		int l = tag.length();
		if (l < padding) {
			return tag + paddingString.substring(0, padding - l);
		}
		return tag;
	}
	
	private EnhancedCompiledArtifactIdentification getBrowseResult() {
		QuickImportDialog quickImportDialog = new QuickImportDialog( parentShell);
		quickImportDialog.setImportAction( QuickImportAction.selectOnly);
		if (selectedTargetArtifact != null) {
			quickImportDialog.setSelection(selectedTargetArtifact);
		}		
		quickImportDialog.open();
		return quickImportDialog.getSelection();
	}
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.widget == targetBrowseButton) {
			EnhancedCompiledArtifactIdentification selected = getBrowseResult();
			if (selected != null) {
				targetGroupIdEditor.setSelection( selected.getGroupId());
				targetArtifactIdEditor.setSelection( selected.getArtifactId());
				targetVersionEditor.setSelection(selected.getVersion().asString());
				selectedTargetArtifact = selected;
				acknowledgeArtifactSelection( this, selected.asString());
			}
			else {
				acknowledgeArtifactSelection( this, "no target");
			}
		}
	}

	public EnhancedCompiledArtifactIdentification getSelectedArtifact() {		
		String groupId = targetGroupIdEditor.getSelection();
		String artifactId = targetArtifactIdEditor.getSelection();
		String version = targetVersionEditor.getSelection();
		if (
				groupId == null || groupId.length() == 0 ||
				artifactId == null || artifactId.length() == 0 ||
				version == null || version.length() == 0
		   )
			return null;
		List<EnhancedCompiledArtifactIdentification> query = DevrockPlugin.instance().quickImportController().runProjectToSourceQuery( groupId + ":" + artifactId + "#" + version);
		
		if (query != null && query.size() == 1) {						
			//EnhancedCompiledArtifactIdentification artifact = EnhancedCompiledArtifactIdentification.create(groupId, artifactId, Version.parse(version), null);
			EnhancedCompiledArtifactIdentification artifact = query.get(0);
			return artifact;
		}
		else  {
			return null;
		}
	}

	@Override
	public void acknowledgeArtifactSelection(TbWizardArtifactTab tab, String name) {
		for (ArtifactSelectionListener listener : listeners) {
			listener.acknowledgeArtifactSelection(tab, name);
		}		
	}

	
	
}
