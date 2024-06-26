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
package com.braintribe.gwt.aceeditor.client;

import java.text.ParseException;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.async.client.AsyncCallbacks;
import com.braintribe.gwt.genericmodelgxtsupport.client.field.ClickableInsideTriggerField;
import com.braintribe.gwt.genericmodelgxtsupport.client.field.TriggerFieldAction;
import com.braintribe.gwt.gmview.util.client.TextResourceManager;
import com.braintribe.gwt.gxt.gxtresources.css.GxtResources;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.StringSource;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.form.PropertyEditor;
import com.sencha.gxt.widget.core.client.form.TriggerField;
import com.sencha.gxt.widget.core.client.grid.Grid.GridCell;
import com.sencha.gxt.widget.core.client.grid.editing.AbstractGridEditing;

import tribefire.extension.scripting.model.deployment.Script;

public class GmScriptEditor extends TriggerField<Script> implements ClickableInsideTriggerField, TriggerFieldAction {
	
	private Action triggerAction;
	private Script script;
	private GmScriptEditorDialog dialog;
	private HandlerRegistration hideHandlerRegistration;
	private HideHandler hideHandler;
	private AbstractGridEditing<?> gridEditing;
	private GridCell gridCell;
	
	public GmScriptEditor() {
		super(new PropertyEditor<Script>() {
			@Override
			public Script parse(CharSequence text) throws ParseException {
				return null;
			}
			
			@Override
			public String render(Script object) {
				return null;
			}
		});
		
		addTriggerClickHandler(event -> handleTriggerClick());
	}
	
	public void setScriptEditorDialog(GmScriptEditorDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void setValue(Script value) {
		this.script = value;
		super.setValue(value);
	}
	
	public void handleTriggerClick() {
		new Timer() {
			@Override
			public void run() {		
				gridEditing.cancelEditing();		
				new Timer() {
					@Override
					public void run() {
						GmScriptEditorDialog scriptDialog = getDialog();
						scriptDialog.setEntityValue(script);
						scriptDialog.setReadOnly(isReadOnly());
						scriptDialog.show();
						if (isRendered())
							getInputEl().focus();
					}					
				}.schedule(100);
			}
		}.schedule(100);						
	}
	
	private GmScriptEditorDialog getDialog() {
		if (dialog != null) {
			if (hideHandlerRegistration == null)
				hideHandlerRegistration = dialog.addHideHandler(getHideHandler());
			
			return dialog;
		}
		
		return null;
	}
	
	private HideHandler getHideHandler() {
		if (hideHandler != null)
			return hideHandler;
		
		hideHandler = (HideHandler) event -> {
			boolean hasChanges = dialog.hasChanges();
			new Timer() {
				@Override
				public void run() {
					if (hasChanges) {
						if (script.getSource() == null) {
							Resource resource = Resource.T.create();
							resource.setResourceSource(StringSource.T.create());
							script.setSource(resource);
						}
						
						Property prop = script.entityType().getProperty("source");
						TextResourceManager.saveResourceContent(dialog.getScriptText(), script.getSource(), prop, script).get(AsyncCallbacks.of(v -> {
							GmScriptEditor.super.setValue(script);
						}, e -> {
							ErrorDialog.show(GmScriptEditorLocalizedText.INSTANCE.saveError(), e);
						}));
					}
					if (!hasChanges)
						blur();
					
					hideHandlerRegistration.removeHandler();
					hideHandlerRegistration = null;
				}
						
			}.schedule(150);
		};
		
		return hideHandler;
	}

	@Override
	public TriggerField<?> getTriggerField() {
		return this;
	}

	@Override
	public void fireTriggerClick(NativeEvent event) {
		handleTriggerClick();
	}

	@Override
	public Action getTriggerFieldAction() {
		if (triggerAction != null)
			return triggerAction;
		
		triggerAction = new Action() {
			@Override
			public void perform(TriggerInfo triggerInfo) {
				gridEditing.startEditing(gridCell);
				Scheduler.get().scheduleDeferred(() -> new Timer() {
					@Override
					public void run() {
						handleTriggerClick();
						if (isReadOnly())
							gridEditing.cancelEditing();
					}
				}.schedule(100));
			}
		};
		
		triggerAction.setIcon(GxtResources.INSTANCE.multiLine());
		triggerAction.setName(LocalizedText.INSTANCE.multiLineEditor());
		triggerAction.setTooltip(LocalizedText.INSTANCE.multiLineEditor());
		triggerAction.setHidden(true);
		
		return triggerAction;
	}

	@Override
	public void setGridInfo(AbstractGridEditing<?> gridEditing, GridCell gridCell) {
		this.gridEditing = gridEditing;
		this.gridCell = gridCell;
		
		if (dialog != null)
			dialog.configureGridEditing(gridEditing);			
	}

	@Override
	public boolean canFireTrigger() {
		if (dialog == null)
			return false;
		
		return dialog.canFireDialog();
	}	
}
