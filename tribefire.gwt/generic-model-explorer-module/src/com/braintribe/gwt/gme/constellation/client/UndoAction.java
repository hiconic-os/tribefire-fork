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

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gmview.action.client.KnownGlobalAction;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.processing.session.api.transaction.Transaction;
import com.braintribe.model.processing.session.api.transaction.TransactionException;
import com.braintribe.model.processing.session.api.transaction.TransactionFrame;
import com.braintribe.model.processing.session.api.transaction.TransactionFrameListener;
import com.google.gwt.user.client.Timer;

public class UndoAction extends Action implements TransactionFrameListener, KnownGlobalAction {
	
	private static final String KNOWN_NAME = "undo";
	private PersistenceGmSession gmSession;
	private List<UndoActionListener> undoActionListeners;
	private int currentUndoSize = 0;
	private Timer manipulationTimer;
	
	public void setGmSession(PersistenceGmSession gmSession) {
		if (this.gmSession != null)
			this.gmSession.getTransaction().removeTransactionFrameListener(this);
		
		this.gmSession = gmSession;
		
		gmSession.getTransaction().addTransactionFrameListener(this);
		gmSession.listeners().add((ManipulationListener) manipulation -> getManipulationTimer().schedule(200));
	}
	
	public UndoAction() {
		setName(LocalizedText.INSTANCE.undo());
		setTooltip(LocalizedText.INSTANCE.undo());
		setIcon(ConstellationResources.INSTANCE.undo());
		setHoverIcon(ConstellationResources.INSTANCE.undoSmall());
		setEnabled(false);
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		boolean undoAll = false;
		if (triggerInfo != null) {
			Boolean all = triggerInfo.get("UndoAll");
			undoAll = (all == null) ? false : all;			
		}
		
		if (undoAll) 
			undoAllManipulations();
		else	
			undoManipulation();
	}
	
	@Override
	public String getKnownName() {
		return KNOWN_NAME;
	}
	
	/**
	 * Undoes the current manipulation to undo on the given {@link PersistenceGmSession}.
	 */
	public static void undoManipulation(PersistenceGmSession gmSession) {
		undoManipulations(1, gmSession);
	}
	
	/**
	 * Undoes all manipulations on the given {@link PersistenceGmSession}.
	 */
	public static void undoAllManipulations(PersistenceGmSession gmSession) {
		undoManipulations(gmSession.getTransaction().getManipulationsDone().size(), gmSession);
	}
	
	public void undoAllManipulations() {
		undoManipulations(gmSession.getTransaction().getManipulationsDone().size(), gmSession);
	}
	
	@Override
	public void onDoUndoStateChanged(TransactionFrame transactionFrame) {
		boolean canUndo = transactionFrame.canUndo();
		setEnabled(canUndo);
		
		/*if (canUndo) {
			Manipulation manipulationToUndo = transactionFrame.getManipulationsDone().get(transactionFrame.getManipulationsDone().size() - 1);
			setTooltip(LocalizedText.INSTANCE.undo() + " - " + manipulationToUndo.getDescription());
		}*/
	}
	
	public void addUndoActionListener(UndoActionListener listener) {
		if (undoActionListeners == null)
			undoActionListeners = new ArrayList<>();
		
		undoActionListeners.add(listener);
	}
	
	public void removeUndoActionListener(UndoActionListener listener) {
		if (undoActionListeners != null) {
			undoActionListeners.remove(listener);
			if (undoActionListeners.isEmpty())
				undoActionListeners = null;
		}
	}
	
	private void undoManipulation() {
		undoManipulation(gmSession);
	}
	
	private static void undoManipulations(int steps, PersistenceGmSession gmSession) {
		Transaction transaction = gmSession.getTransaction();
		try {
			transaction.undo(steps);
		} catch (TransactionException e) {
			ErrorDialog.show(LocalizedText.INSTANCE.errorUndoing(), e);
			e.printStackTrace();
		}
	}
	
	private Timer getManipulationTimer() {
		if (manipulationTimer == null) {
			manipulationTimer = new Timer() {
				@Override
				public void run() {
					int undoSize = UndoAction.this.gmSession.getTransaction().getManipulationsDone().size();
					if (undoSize != currentUndoSize) {
						currentUndoSize = undoSize;
						fireUndoStateChanged(undoSize);
					}
				}
			};
		}
		
		return manipulationTimer;
	}
	
	private void fireUndoStateChanged(int manipulationsToUndo) {
		if (undoActionListeners != null) {
			for (UndoActionListener listener : undoActionListeners)
				listener.onUndoStateChanged(manipulationsToUndo);
		}
	}
	
	@FunctionalInterface
	public static interface UndoActionListener {
		void onUndoStateChanged(int manipulationsToUndo);
	}
	
}
