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
package com.braintribe.gwt.gme.constellation.client;

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gme.verticaltabpanel.client.VerticalTabElement;
import com.braintribe.gwt.gmview.action.client.KnownGlobalAction;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.gwt.utils.client.RootKeyNavExpert.RootKeyNavListener;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.validation.Validation;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.transaction.TransactionFrame;
import com.braintribe.model.processing.session.api.transaction.TransactionFrameListener;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.sencha.gxt.core.client.GXT;

public class SaveAction extends Action implements TransactionFrameListener, RootKeyNavListener, KnownGlobalAction {
	
	private static final String KNOWN_NAME = "commit";
	private PersistenceGmSession gmSession;
	private ExplorerConstellation explorerConstellation;
	private VerticalTabElement verticalTabElementToRemove;
	private Supplier<Validation> validationSupplier;
	private boolean enablementBeforePerform;
	
	@Required
	public void configureGwtPersistenceSession(PersistenceGmSession gmSession) {
		if (this.gmSession != null)
			this.gmSession.getTransaction().removeTransactionFrameListener(this);
		
		this.gmSession = gmSession;
		gmSession.getTransaction().addTransactionFrameListener(this);
	}
	
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}
	
	@Required
	public void setValidationSupplier(Supplier<Validation> validationSupplier) {
		this.validationSupplier = validationSupplier;
	}
	
	public void applyDefaultSaveButtonStyle() {
		setEnabled(false);
		setName(LocalizedText.INSTANCE.save());
		setTooltip(LocalizedText.INSTANCE.save());
		setIcon(ConstellationResources.INSTANCE.save());
		setHoverIcon(ConstellationResources.INSTANCE.saveSmall());
	}
	
	public SaveAction() {
		applyDefaultSaveButtonStyle();
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		enablementBeforePerform = getEnabled();
		setEnabled(false); //Disabling it while performing, so it won't be triggered more than once.
		processSaving(null, triggerInfo);
	}
	
	@Override
	public String getKnownName() {
		return KNOWN_NAME;
	}
	
	@Override
	public void onRootKeyPress(NativeEvent evt) {
		if ((evt.getCtrlKey() || (GXT.isMac() && evt.getMetaKey())) && evt.getKeyCode() == KeyCodes.KEY_S) {
			evt.stopPropagation();
			evt.preventDefault();
			if (getEnabled())
				processSaving(null);
		}
	}
	
	private Future<Boolean> processSaving(String comment, TriggerInfo triggerInfo) {
		GlobalState.showSuccess(LocalizedText.INSTANCE.validating());
		
		boolean autoCommit;
		if (triggerInfo == null)
			autoCommit = false;
		else {
			Boolean auto = triggerInfo.get("AutoCommit");
			if (auto != null)
				autoCommit = auto;
			else
				autoCommit = false;
		}
		
		if (explorerConstellation == null)
			return applyManipulations(comment, autoCommit);
		
		Validation validation = validationSupplier.get();
		Future<Boolean> future = new Future<>();
		validation.validateManipulations(gmSession.getTransaction().getManipulationsDone()) //
				.andThen(result -> {
					validation.setAlreadyTriggered(true);
					GlobalState.clearState();
					if (result.isEmpty()) {
						applyManipulations(comment, autoCommit).andThen(future::onSuccess).onError(future::onFailure);
						validation.setAlreadyTriggered(false);
						
						Action clearwValidationLogAction = new Action() {
							@Override
							public void perform(TriggerInfo triggerInfo) {
								explorerConstellation.clearValidationLog();
							}
						};
						clearwValidationLogAction.perform(triggerInfo);												
						return;
					}

					setEnabled(enablementBeforePerform);
					future.onSuccess(false);

					Action showValidationLogAction = new Action() {
						@Override
						public void perform(TriggerInfo triggerInfo) {
							explorerConstellation.showValidations();
							//explorerConstellation.prepareValidationLog(result, triggerInfo).onError(Throwable::printStackTrace)
							//		.andThen(result -> verticalTabElementToRemove = result);
						}
					};
					
					explorerConstellation.prepareValidationLog(result, triggerInfo).onError(Throwable::printStackTrace)
					.andThen(resultVerticalTabElement -> verticalTabElementToRemove = resultVerticalTabElement);

					showValidationLogAction.setName(LocalizedText.INSTANCE.details());
					GlobalState.showWarning(LocalizedText.INSTANCE.validationError(), showValidationLogAction);
					//showValidationLogAction.perform(triggerInfo);
				}).onError(e -> {
					GlobalState.showError("Error while validating manipulations.", e);
					e.printStackTrace();
					future.onFailure(e);
				});
		
		return future;		
	}
	
	public Future<Boolean> processSaving(String comment) {
		return processSaving(comment, null);
	}
	
	@Override
	public void onDoUndoStateChanged(TransactionFrame transactionFrame) {
		List<Manipulation> manipulationsDone = transactionFrame.getManipulationsDone();
		setEnabled(manipulationsDone != null && !manipulationsDone.isEmpty(), true);
	}
	
	private Future<Boolean> applyManipulations(String comment, boolean isAutoCommit) {
		Future<Boolean> future = new Future<>();
		GlobalState.mask(LocalizedText.INSTANCE.saving());
		gmSession.prepareCommit().comment(comment).commit(com.braintribe.processing.async.api.AsyncCallback.of( //
				response -> {
					GlobalState.unmask();
					if (explorerConstellation != null)
						explorerConstellation.removeVerticalTabElement(verticalTabElementToRemove);
					GlobalState.showInfo(LocalizedText.INSTANCE.saveSuccess());
					future.onSuccess(true);
				}, e -> {
					setEnabled(enablementBeforePerform);
					GlobalState.unmask();
					if (isAutoCommit)
						handleAutoCommitError(e);
					else
						ErrorDialog.show(LocalizedText.INSTANCE.errorApplyingManipulations(), e);
					e.printStackTrace();
					future.onFailure(e);
				}));
		
		return future;
	}
	
	private void handleAutoCommitError(Throwable e) {
		UndoAction.undoAllManipulations(gmSession);
		ErrorDialog.show(LocalizedText.INSTANCE.errorDuringAutoCommit(), e);
	}

}
