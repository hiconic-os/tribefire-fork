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
import com.braintribe.gwt.gxt.gxtresources.extendedtrigger.client.ExtendedStringField;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.AbstractGridEditing;

/**
 * Extension prepared for GM's {@link TriggerFieldAction}.
 * @author michel.docouto
 *
 */
public class GmExtendedStringField extends ExtendedStringField implements TriggerFieldAction {
	
	private Action triggerAction;
	private AbstractGridEditing<?> gridEditing;
	private GridCell gridCell;
	private Supplier<? extends ExtendedStringDialog> extendedStringDialog;

	public GmExtendedStringField(Supplier<? extends ExtendedStringDialog> dialogProvider) {		
		super();
		this.extendedStringDialog = dialogProvider;
	}			
		
	@Override
	protected void handleTriggerClick() {
		ExtendedStringDialog dialog = getDialog();
		dialog.configureGridEditing(gridEditing);
		String dialogName = this.getData("dialogName");
		if (dialogName != null)
			dialog.setCaption(dialogName);
		String codeFormat = this.getData("codeFormatting");
		if (codeFormat != null)
			dialog.setCodeFormat(codeFormat);
		dialog.setString(getValue());
		dialog.setReadOnly(isReadOnly());
		dialog.show();		
		
		if (!isReadOnly())
			getInputEl().focus();
	}	
	
	@Override
	public void focus() {
		if (!isReadOnly())
			super.focus();
	 }	
	
	private ExtendedStringDialog getDialog() {
		if (dialog != null)
			return (ExtendedStringDialog) dialog;
		
		dialog = extendedStringDialog.get();
		if (dialog == null)
			return null;
		
		dialog.addHideHandler(event -> Scheduler.get().scheduleDeferred(() -> {
			if (isReadOnly())
				return;
			
			if (((ExtendedStringDialog) dialog).isApplyChanges())
				setValue(((ExtendedStringDialog) dialog).getString(), true);
			else
				gridEditing.cancelEditing();
			//editingField = false;
		}));
		
		return (ExtendedStringDialog) dialog;
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
				Scheduler.get().scheduleDeferred(() -> new Timer() {
					@Override
					public void run() {
						handleTriggerClick();
						if (isReadOnly()) {
							gridEditing.cancelEditing();
							editingField = false;
						}
					}
				}.schedule(100));
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
		if (this.hasFocus)
		    return super.getText();    //need in case of real time validation
		else	
			return getTextWithEncodedLineBreaks(getValue());
	}
	
	@Override
	public void setValue(String value) {
		super.setValue(getTextWithCodedLineBreaks(value));
	}	

	@Override
	public String getValue() {
		return getTextWithEncodedLineBreaks(super.getValue());
	}

	public void setDialog(Supplier<? extends ExtendedStringDialog> extendedStringDialog) {
		this.extendedStringDialog = extendedStringDialog;
	}	
}
