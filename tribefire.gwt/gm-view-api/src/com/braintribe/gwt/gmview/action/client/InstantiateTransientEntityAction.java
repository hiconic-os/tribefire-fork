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

import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.gmview.client.InstantiatedEntityListener;
import com.braintribe.gwt.gmview.util.client.GMEMetadataUtil;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.constraint.Instantiable;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

/**
 * This action is responsible for instantiating a new transient entity.
 * @author michel.docouto
 *
 */
public class InstantiateTransientEntityAction extends AbstractInstantiateEntityAction {
	
	public InstantiateTransientEntityAction() {
		setName(LocalizedText.INSTANCE.newTransientEntity());
	}
	
	/**
	 * Configures the session used for creating new transient instances. This is required when the action is available.
	 */
	@Configurable
	public void setTransientSession(PersistenceGmSession transientSession) {
		this.gmSession = transientSession;
		transientSession.listeners().add(InstantiateTransientEntityAction.this);
	}
	
	/**
	 * Configures the useCase to be used within this action.
	 * Notice that this is used only if there is no {@link GmContentView} configured via {@link #configureGmContentView(com.braintribe.gwt.gmview.client.GmContentView)}.
	 */
	@Override
	public void configureUseCase(String useCase) {
		this.useCase = useCase;
	}		
	
	/**
	 * Configures an {@link InstantiatedEntityListener} to be called when instantiating.
	 * Notice that this is used only if there is no {@link GmContentView} configured via {@link #configureGmContentView(com.braintribe.gwt.gmview.client.GmContentView)}.
	 */
	@Override
	public void configureInstantiatedEntityListener(InstantiatedEntityListener instantiatedEntityListener) {
		this.instantiatedEntityListener = instantiatedEntityListener;
	}	
		
	/**
	 * Configures the {@link EntityType} that will be instantiated.
	 */
	@Override
	public void configureEntityType(EntityType<?> entityType) {
		this.entityType = entityType;
		setHidden(entityType == null || !isInstantiable(entityType));
		if (entityType != null) {
			String actionName = LocalizedText.INSTANCE.createEntity(GMEMetadataUtil.getEntityNameMDOrShortName(entityType,
					getGmSession().getModelAccessory().getMetaData(), gmContentView != null ? gmContentView.getUseCase() : useCase));
			if (displayEntityNameInAction)
				setName(actionName);
			setTooltip(actionName);
		}
	}
	
	@Override
	public PersistenceGmSession getGmSession() {
		return gmSession;
	}
	
	@Override
	protected void updateVisibility() {
		if (entityType == null)
			entityType = GenericEntity.T;
		if (gmSession.getModelAccessory() != null) {
			setHidden(!isInstantiable(entityType));
			return;
		}
		
		setHidden(true, true);
	}
	
	//@Override
	protected boolean isInstantiable(EntityType<?> entityType) {
		if (gmSession.getModelAccessory() != null) {
			EntityMdResolver entityMetaDataContextBuilder = gmSession.getModelAccessory().getMetaData().entityType(entityType)
					.useCase(gmContentView != null ? gmContentView.getUseCase() : useCase);
			if (!entityMetaDataContextBuilder.is(Instantiable.T))
				return false;
		}
		
		return true;
	}
	
}
