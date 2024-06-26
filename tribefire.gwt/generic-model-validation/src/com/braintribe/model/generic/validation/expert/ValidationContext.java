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
package com.braintribe.model.generic.validation.expert;

import java.util.HashSet;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class ValidationContext {
	
	private PersistenceGmSession gmSession;
	private boolean completeConfiguredValidation = false;
	private boolean groupValidations = false;
	private boolean shortMessageStyle = false;
	
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}
	
	public ManagedGmSession getGmSession() {
		return gmSession;
	}

	public Set<GenericEntity> getChangedEntitiesByEntityType(EntityType<GenericEntity> entityType) {
		Set<GenericEntity> changedEntities = new HashSet<>();
		gmSession.getTransaction().getManipulatedProperties().forEach(entityProperty -> {
			GenericEntity candidate = entityProperty.getEntity();
			EntityType<GenericEntity> candidateType = candidate.entityType();
			if (candidateType.isAssignableFrom(entityType))
				changedEntities.add(candidate);
		});
		
		return changedEntities;//gmSession.getTransaction().getManipulatedProperties();
	}

	public boolean isCompleteConfiguredValidation() {
		return completeConfiguredValidation;
	}

	public void setCompleteConfiguredValidation(boolean completeConfiguredValidation) {
		this.completeConfiguredValidation = completeConfiguredValidation;
	}

	public boolean isGroupValidations() {
		return groupValidations;
	}

	public void setGroupValidations(boolean groupValidations) {
		this.groupValidations = groupValidations;
	}

	public boolean isShortMessageStyle() {
		return shortMessageStyle;
	}

	public void setShortMessageStyle(boolean shortMessageStyle) {
		this.shortMessageStyle = shortMessageStyle;
	}
}
