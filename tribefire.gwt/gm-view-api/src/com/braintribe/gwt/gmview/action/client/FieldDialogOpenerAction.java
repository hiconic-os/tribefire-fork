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
package com.braintribe.gwt.gmview.action.client;

import java.util.function.Supplier;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gmview.client.EntityFieldDialog;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class FieldDialogOpenerAction<E extends GenericEntity> extends Action {
	
	private Supplier<? extends EntityFieldDialog<E>> entityFieldDialogSupplier;
	private EntityFieldDialog<E> entityFieldDialog;
	private PersistenceGmSession gmSession;
	private E entityValue;
	private Boolean isFreeInstantiation;
	
	/**
	 * Configures the required supplier for the EntityFieldDialog, used for editing the GenericEntity configured via
	 * {@link #configureEntityValue(GenericEntity)}.
	 */
	@Required
	public void setEntityFieldDialogSupplier(Supplier<? extends EntityFieldDialog<E>> entityFieldDialogSupplier) {
		this.entityFieldDialogSupplier = entityFieldDialogSupplier;
		entityFieldDialog = getEntityFieldDialog();
		if (entityFieldDialog == null)
			return;
		
		entityFieldDialog.addHideHandler(event -> {
			boolean hasChanges = FieldDialogOpenerAction.this.entityFieldDialog.hasChanges();
			if (hasChanges) {
				FieldDialogOpenerAction.this.entityFieldDialog.performManipulations();
				/*try {
					parentPanel.getManipulationContext().doManipulation(manipulation);
				} catch (ManipulationException e) {
					ErrorDialog.show("Error while performing field manipulation.", e);
					e.printStackTrace();
				}*/
			}
		});
	}
	
	public void configureGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
		
		if (entityFieldDialog != null)
			entityFieldDialog.configureGmSession(gmSession);
	}
	
	/**
	 * Sets the entity to be edited within this action.
	 */
	public void configureEntityValue(E entityValue) {
		this.entityValue = entityValue;
		
		if (entityFieldDialog != null)
			entityFieldDialog.setEntityValue(entityValue);
	}
	
	public void setIsFreeInstantiation(Boolean isFreeInstantiation) {
		this.isFreeInstantiation = isFreeInstantiation;
		
		if (entityFieldDialog != null)
			entityFieldDialog.setIsFreeInstantiation(isFreeInstantiation);
	}
	
	@Override
	public void perform(TriggerInfo triggerInfo) {
		getEntityFieldDialog().show();
	}
	
	private EntityFieldDialog<E> getEntityFieldDialog() {
		if (entityFieldDialog != null)
			return entityFieldDialog;
		
		entityFieldDialog = entityFieldDialogSupplier.get();
		if (gmSession != null)
			entityFieldDialog.configureGmSession(gmSession);
		if (entityValue != null)
			entityFieldDialog.setEntityValue(entityValue);
		if (isFreeInstantiation != null)
			entityFieldDialog.setIsFreeInstantiation(isFreeInstantiation);
			
		return entityFieldDialog;
	}
	
}
