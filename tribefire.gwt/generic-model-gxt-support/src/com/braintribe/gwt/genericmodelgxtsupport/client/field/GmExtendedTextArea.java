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
package com.braintribe.gwt.genericmodelgxtsupport.client.field;

import java.util.function.Supplier;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.genericmodelgxtsupport.client.LocalizedText;
import com.braintribe.gwt.genericmodelgxtsupport.client.resources.GMGxtSupportResources;
import com.braintribe.gwt.gxt.gxtresources.multieditor.client.NoBlurWhileEditingField;
import com.google.gwt.core.client.Scheduler;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.AbstractGridEditing;

/**
 * {@link TextArea} Extension prepared for GM's {@link TriggerFieldAction}.
 *
 */
public class GmExtendedTextArea extends TextArea implements TriggerFieldAction, NoBlurWhileEditingField {

	private Action triggerAction;
	private ExtendedStringDialog extendedStringDialog;
	private boolean editingField = false;
	private AbstractGridEditing<?> gridEditing;
	private GridCell gridCell;
	private Supplier<? extends ExtendedStringDialog> extendedStringDialogProvider;

	public GmExtendedTextArea() {
		super(new ClickableTextAreaInputCell());
	}		

	public GmExtendedTextArea(Supplier<? extends ExtendedStringDialog> dialogProvider) {		
		super(new ClickableTextAreaInputCell());
		this.extendedStringDialogProvider = dialogProvider;
	}			
	
	protected void handleTriggerClick() {
		ExtendedStringDialog extendedStringFieldDialog = getExtendedStringDialog();
		extendedStringFieldDialog.configureGridEditing(gridEditing);
		String dialogName = this.getData("dialogName");
		if (dialogName != null)
			extendedStringFieldDialog.setCaption(dialogName);
		String codeFormat = this.getData("codeFormatting");
		if (codeFormat != null)
			extendedStringFieldDialog.setCodeFormat(codeFormat);
		extendedStringFieldDialog.setString(getValue());
		extendedStringFieldDialog.setReadOnly(isReadOnly());
		extendedStringFieldDialog.show();		
		
		if (!isReadOnly())
			Scheduler.get().scheduleDeferred(getInputEl()::focus);
	}
	
	@Override
	public void focus() {
		if (!isReadOnly())
			super.focus();
	 }	
	
	private ExtendedStringDialog getExtendedStringDialog() {
		if (extendedStringDialog != null)
			return extendedStringDialog;
		
		if (extendedStringDialogProvider != null)
			extendedStringDialog = extendedStringDialogProvider.get();
		else	
			extendedStringDialog = new ExtendedStringFieldAceEditorDialog();
		
		extendedStringDialog.addHideHandler((HideHandler) event -> {
			if (isReadOnly())
				return;
			
			String newValue = extendedStringDialog.getString();				
			Scheduler.get().scheduleDeferred(() -> {
				if (extendedStringDialog.isApplyChanges())  					
					GmExtendedTextArea.this.setValue(newValue, true);
			});
		});
		return extendedStringDialog;
	}

	@Override
	public Action getTriggerFieldAction() {
		if (triggerAction != null)
			return triggerAction;
		
		triggerAction = new Action() {
			@Override
			public void perform(TriggerInfo triggerInfo) {
				gridEditing.startEditing(gridCell);				
				editingField = true;
				Scheduler.get().scheduleDeferred(() -> {
					handleTriggerClick();
					if (isReadOnly()) {
						gridEditing.cancelEditing();
						editingField = true;
					}						
				});
			}
		};
		
		triggerAction.setIcon(GMGxtSupportResources.INSTANCE.multiLine());
		triggerAction.setName(LocalizedText.INSTANCE.multiline());
		triggerAction.setTooltip(LocalizedText.INSTANCE.multilineDescription());
		
		return triggerAction;
	}

	@Override
	public void setGridInfo(AbstractGridEditing<?> gridEditing, GridCell gridCell) {
		this.gridEditing = gridEditing;
		this.gridCell = gridCell;
	}	
	
	@Override
	public String getText() {
		return getValue();
	}

	@Override
	public String getValue() {
		return super.getValue();
	}	
	
	@Override
	public boolean isEditingField() {
		return editingField;				
	}

	public void setDialog(Supplier<ExtendedStringDialog> extendedStringDialogProvider) {
		this.extendedStringDialogProvider = extendedStringDialogProvider; 		
	}
}
