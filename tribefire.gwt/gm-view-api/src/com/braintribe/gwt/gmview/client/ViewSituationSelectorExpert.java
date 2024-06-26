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
package com.braintribe.gwt.gmview.client;

import java.util.Set;

import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.ModelPathElement;
import com.braintribe.model.generic.path.ModelPathElementInstanceKind;
import com.braintribe.model.generic.path.ModelPathElementType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.generic.value.PreliminaryEntityReference;
import com.braintribe.model.meta.data.EntityTypeMetaData;
import com.braintribe.model.processing.meta.cmd.builders.EntityMdResolver;
import com.braintribe.model.processing.session.api.common.GmSessions;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class ViewSituationSelectorExpert extends AbstractViewSelectorExpert<ViewSituationSelector> {
	
	private PersistenceGmSession gmSession;
	
	@Required
	public void setGmSession(PersistenceGmSession gmSession) {
		this.gmSession = gmSession;
	}

	@Override
	public boolean doesSelectorApply(ViewSituationSelector viewSituationSelector, ModelPathElement modelPathElement) {
		String metadataTypeSignature = viewSituationSelector.getMetadataTypeSignature();
		if (metadataTypeSignature != null) {
			GenericModelType type = modelPathElement.getType();
			if (!type.isEntity())
				return false;
			
			return getEntityTypeMetadata((GenericEntity) modelPathElement.getValue(), (EntityType<?>) type, metadataTypeSignature,
					viewSituationSelector.getUseCases()) != null;
		}
		
		boolean typeConditionMatched = viewSituationSelector.getValueType().matches(modelPathElement.<GenericModelType>getType());
		ModelPathElementType selectorPathElementType = viewSituationSelector.getPathElementType();
		if (!typeConditionMatched || (selectorPathElementType != null && !selectorPathElementType.equals(modelPathElement.getPathElementType())))
			return false;
		
		Object value = modelPathElement.getValue();
		if (!(value instanceof GenericEntity) || viewSituationSelector.getElementInstanceKind() == null)
			return true;
		
		GenericEntity entity = (GenericEntity) value;
		EntityReference ref = entity.reference();
		ModelPathElementInstanceKind ik = viewSituationSelector.getElementInstanceKind();
		if (ModelPathElementInstanceKind.any.equals(ik))
			return true;
		
		if (ModelPathElementInstanceKind.preliminary.equals(ik) && ref instanceof PreliminaryEntityReference)
			return true;
		
		if (ModelPathElementInstanceKind.persistent.equals(ik) && ref instanceof PersistentEntityReference)
			return true;
		
		return false;
	}

	private EntityTypeMetaData getEntityTypeMetadata(GenericEntity entity, EntityType<?> entityType, String metadataTypeSignature,
			Set<String> useCases) {
		EntityType<? extends EntityTypeMetaData> metadataEntityType = GMF.getTypeReflection().getEntityType(metadataTypeSignature);
		EntityMdResolver entityMdResolver;
		if (entity != null)
			entityMdResolver = GmSessions.getMetaData(entity).lenient(true).entity(entity);
		else
			entityMdResolver = gmSession.getModelAccessory().getMetaData().lenient(true).entityType(entityType);
		
		if (useCases != null && !useCases.isEmpty())
			entityMdResolver = entityMdResolver.useCases(useCases);
		
		return entityMdResolver.meta(metadataEntityType).exclusive();
	}

}
