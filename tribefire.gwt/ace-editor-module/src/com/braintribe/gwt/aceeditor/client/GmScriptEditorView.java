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

import java.util.ArrayList;
import java.util.List;

import com.braintribe.common.lcd.Pair;
import com.braintribe.gwt.aceeditor.listeners.SelectionListeners;
import com.braintribe.gwt.gmview.action.client.ActionTypeAndName;
import com.braintribe.gwt.gmview.actionbar.client.ActionProviderConfiguration;
import com.braintribe.gwt.gmview.actionbar.client.GmViewActionProvider;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.GmContentViewActionManager;
import com.braintribe.gwt.gmview.client.GmResetableActionsContentView;
import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.util.client.TextResourceManager;
import com.braintribe.gwt.ioc.client.DisposableBean;
import com.braintribe.gwt.ioc.client.InitializableBean;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.LocalEntityProperty;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.manipulation.Owner;
import com.braintribe.model.generic.manipulation.PropertyManipulation;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.transaction.NestedTransaction;
import com.google.gwt.user.client.Timer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;

import edu.ycp.cs.dh.acegwt.client.ace.AceEditor;
import tribefire.extension.scripting.model.deployment.Script;

/**
 * GmContentView based Script Editor.
 * 
 */

public class GmScriptEditorView extends BorderLayoutContainer implements InitializableBean, DisposableBean, GmContentView, GmResetableActionsContentView, GmViewActionProvider, ManipulationListener{
	
	private PersistenceGmSession gmSession;
	private String useCase;
	private Script script;
	private AceEditor aceEditor;	
	private GmContentViewActionManager actionManager;
    private ModelPath lastModelPath;
	private ActionProviderConfiguration actionProviderConfiguration;
	private SelectionListeners gmSelectionListeners = null; 	
	private NestedTransaction editionNestedTransaction;
	private boolean preventManipulation = false;
	private boolean readOnly = false;
	private String initialScriptContent;
	
	public GmScriptEditorView() {
		gmSelectionListeners = new SelectionListeners(this);		
		setBorders(false);
		doLayout();
	}
		
	@Override
	public void intializeBean() throws Exception {
		aceEditor = GmScriptEditorUtil.prepareAceEditor();		
		aceEditor.setReadOnly(false);
		
		setCenterWidget(aceEditor);
						
		aceEditor.addOnChangeHandler(obj -> {
			//RVE = react only on keystroke by user (not by noticeManipulation)
			if (preventManipulation)
				return;
							
			//RVE - reset Timer
			updateTimer.cancel();				
			updateTimer.schedule(1000);
		});

		if (actionManager != null)
			actionManager.connect(this);
	}	
	
	@Override
	public void disposeBean() throws Exception {
		
		if (updateTimer.isRunning())
			updateValue();
		
		updateTimer.cancel();
		

		if (actionManager != null)
			actionManager.notifyDisposedView(this);
		
		removeEntityListener(script);
	}
	
	@Override
	public ModelPath getContentPath() {
		return lastModelPath;
	}

	@Override
	public void setActionManager(GmContentViewActionManager actionManager) {
		this.actionManager = actionManager;
	}

	@Override
	public GmContentViewActionManager getGmContentViewActionManager() {
		return actionManager;
	}	
	
	@Override
	public void setContent(ModelPath modelPath) {
		if (modelPath == null) 
			return;
		
		lastModelPath = modelPath;	
		ModelPathElement pathElement = modelPath.last();

		if (pathElement == null)
			return;
		
		Object object = pathElement.getValue();
		
		if (object instanceof Script) {
			addEntityListener((Script) object);
			setEntityValue((Script) object);
		}
		
		gmSelectionListeners.fireListeners();
	}

	@Override
	public void addSelectionListener(GmSelectionListener sl) {
		gmSelectionListeners.add(sl);
	}

	@Override
	public void removeSelectionListener(GmSelectionListener sl) {
		gmSelectionListeners.remove(sl);
	}

	@Override
	public ModelPath getFirstSelectedItem() {
		return lastModelPath;
	}

	@Override
	public List<ModelPath> getCurrentSelection() {
		if (lastModelPath == null) 
			return null;
		
		List<ModelPath> listModelpath = new ArrayList<>();		
		listModelpath.add(lastModelPath);		
		return listModelpath;
	}

	@Override
	public boolean isSelected(Object element) {
		return false;
	}

	@Override
	public void select(int index, boolean keepExisting) {
		// NOP
	}

	@Override
	public GmContentView getView() {
		return this;
	}

	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;	
	}

	@Override
	public PersistenceGmSession getGmSession() {
		return this.gmSession;	}

	@Override
	public void configureUseCase(String useCase) {
		this.useCase = useCase;
	}

	@Override
	public String getUseCase() {
		return useCase;
	}

	@Override
	public ActionProviderConfiguration getActions() {
		if (actionProviderConfiguration != null) 
			return actionProviderConfiguration;

		actionProviderConfiguration = new ActionProviderConfiguration();
		actionProviderConfiguration.setGmContentView(this);
		
		List<Pair<ActionTypeAndName, ModelAction>> knownActions = null;
		if (actionManager != null)
			knownActions = actionManager.getKnownActionsList(this);
		if (knownActions != null) {
			List<Pair<ActionTypeAndName, ModelAction>> allActions = new ArrayList<>();
			allActions.addAll(knownActions);
			
			actionProviderConfiguration.addExternalActions(allActions);
		}
		
		return actionProviderConfiguration;
	}

	@Override
	public boolean isFilterExternalActions() {
		return false;
	}

	@Override
	public void resetActions() {
		if (actionManager != null)
			actionManager.connect(this);
	}

	@Override
    public void setReadOnly(boolean readOnly) {
    	this.readOnly = readOnly;
    	this.aceEditor.setReadOnly(readOnly);
    }
	
	@Override
    public boolean isReadOnly() {
    	return readOnly;
    }    	
	
	public void setEntityValue(Script script) {
		this.script = script;
		GmScriptEditorUtil.setScriptValue(script, aceEditor) //
				.andThen(content -> initialScriptContent = content) //
				.onError(e -> {
					aceEditor.setReadOnly(true);
					ErrorDialog.show(GmScriptEditorLocalizedText.INSTANCE.retrieveError(), e);
				});
	}

	private void addEntityListener(GenericEntity entity) {
		if (entity == null) 
			return;

		removeEntityListener(entity);
    	gmSession.listeners().entity(entity).add(this);
	}
	
	private void removeEntityListener(GenericEntity entity) {
		if (entity == null) 
			return;
		
		gmSession.listeners().entity(entity).remove(this);
	}
	
	private final Timer updateTimer = new Timer() {
        @Override
        public void run() {
            updateValue();
        }

    };

    private void updateValue() {
    	if (script == null)
    		return;
    	
		Boolean hasChanges = false;
		
		if (script.getSource() == null || initialScriptContent == null)
			hasChanges = (aceEditor.getValue() != null);
		else
			hasChanges = !initialScriptContent.equals(aceEditor.getValue());
		
		if (!hasChanges)
			return;
		
		if (gmSession.getTransaction().canUndo() && editionNestedTransaction != null
				&& editionNestedTransaction.getParentFrame().equals(gmSession.getTransaction().getCurrentTransactionFrame())) {
			gmSession.getTransaction().undo(1);
		}
							
		editionNestedTransaction = gmSession.getTransaction().beginNestedTransaction();
		
		Property prop = script.entityType().getProperty("source");
		TextResourceManager.saveResourceContent(aceEditor.getValue(), script.getSource(), prop, script).andThen(v -> {
			initialScriptContent = aceEditor.getValue();
			editionNestedTransaction.commit();
		}).onError(e -> {
			ErrorDialog.show(GmScriptEditorLocalizedText.INSTANCE.saveError(), e);
			editionNestedTransaction.rollback();
		});
		
	}

	@Override
	public void noticeManipulation(Manipulation manipulation) {
		if (!(manipulation instanceof PropertyManipulation)) 
			return;
		
		new Timer() {
			@Override
			public void run() {
				GenericEntity entity = null;
				Owner manipulationOwner = ((PropertyManipulation) manipulation).getOwner();
				if (manipulationOwner instanceof LocalEntityProperty)
					entity = ((LocalEntityProperty) manipulationOwner).getEntity();
				
				if (script != null && script.equals(entity)) {
                    //is changed
					switch (manipulation.manipulationType()) {
					case CHANGE_VALUE:
					case ABSENTING:
					case ADD:
					case REMOVE:
					case CLEAR_COLLECTION:
							preventManipulation = true;
							setContent(lastModelPath);
							preventManipulation = false;							
						break;
					default:
						break;
					}
					return;
				} 				
			}
		}.schedule(10); //needed, so the value in the entity was the correct one		
	}

}


