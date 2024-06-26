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

/**
 * Button responsible for performing redo manipulations.
 * @author michel.docouto
 *
 */
public class RedoAction extends Action implements TransactionFrameListener, KnownGlobalAction {
	
	private static final String KNOWN_NAME = "redo";
	private PersistenceGmSession gmSession;
	private List<RedoActionListener> redoActionListeners;
	private int currentRedoSize = 0;
	private Timer manipulationTimer;
	
	public void setGmSession(PersistenceGmSession gmSession) {
		if (this.gmSession != null)
			this.gmSession.getTransaction().removeTransactionFrameListener(this);
		
		this.gmSession = gmSession;
		
		gmSession.getTransaction().addTransactionFrameListener(this);
		gmSession.listeners().add((ManipulationListener) manipulation -> getManipulationTimer().schedule(200));
	}
	
	public RedoAction() {
		setName(LocalizedText.INSTANCE.redo());
		setTooltip(LocalizedText.INSTANCE.redo());
		setEnabled(false);
		setIcon(ConstellationResources.INSTANCE.redo());
		setHoverIcon(ConstellationResources.INSTANCE.redoSmall());
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		redoManipulation();
	}
	
	@Override
	public String getKnownName() {
		return KNOWN_NAME;
	}
	
	public void redoAllManipulations() {
		redoManipulations(gmSession.getTransaction().getManipulationsUndone().size());
	}
	
	@Override
	public void onDoUndoStateChanged(TransactionFrame transactionFrame) {
		boolean canRedo = transactionFrame.canRedo();
		setEnabled(canRedo);
		
		/*if (canRedo) {
			Manipulation manipulationToRedo = transactionFrame.getManipulationsUndone().get(0);
			setTooltip(LocalizedText.INSTANCE.redo() + " - " + manipulationToRedo.getDescription());
		}*/
	}
	
	public void addRedoActionListener(RedoActionListener listener) {
		if (redoActionListeners == null)
			redoActionListeners = new ArrayList<>();
		
		redoActionListeners.add(listener);
	}
	
	public void removeRedoActionListener(RedoActionListener listener) {
		if (redoActionListeners != null) {
			redoActionListeners.remove(listener);
			if (redoActionListeners.isEmpty())
				redoActionListeners = null;
		}
	}
	
	private void redoManipulation() {
		redoManipulations(1);
	}
	
	private void redoManipulations(int steps) {
		Transaction transaction = gmSession.getTransaction();
		try {
			transaction.redo(steps);
		} catch (TransactionException e) {
			ErrorDialog.show(LocalizedText.INSTANCE.errorRedoing(), e);
			e.printStackTrace();
		}
	}
	
	private Timer getManipulationTimer() {
		if (manipulationTimer != null)
			return manipulationTimer;
		
		manipulationTimer = new Timer() {
			@Override
			public void run() {
				int redoSize = RedoAction.this.gmSession.getTransaction().getManipulationsUndone().size();
				if (redoSize != currentRedoSize) {
					currentRedoSize = redoSize;
					fireRedoStateChanged(redoSize);
				}
			}
		};
		
		return manipulationTimer;
	}
	
	private void fireRedoStateChanged(int manipulationsToRedo) {
		if (redoActionListeners != null) {
			for (RedoActionListener listener : redoActionListeners)
				listener.onRedoStateChanged(manipulationsToRedo);
		}
	}

	@FunctionalInterface
	public static interface RedoActionListener {
		void onRedoStateChanged(int manipulationsToRedo);
	}
	
}
