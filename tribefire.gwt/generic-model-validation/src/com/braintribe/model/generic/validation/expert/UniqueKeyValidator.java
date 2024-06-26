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

import com.braintribe.gwt.async.client.Future;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.generic.validation.ValidatorResult;
import com.braintribe.model.generic.validation.util.ValidatorUtil;
import com.braintribe.model.meta.data.constraint.Unique;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.query.EntityQueryResult;
import com.braintribe.model.query.OrderingDirection;

public class UniqueKeyValidator extends AbstractValidator<Unique> {
	
	@Override
	public Future<ValidatorResult> validateValue(ValidationContext validationContext, GenericEntity entity, Unique metadata,
			EntitySignatureAndPropertyName entitySignatureAndPropertyName, Object value) {
		String propertyName = entitySignatureAndPropertyName.getPropertyName();
		String displayInfo = ValidatorUtil.getPropertyDisplayName(getGmSession(), entity, propertyName);
		
		ValidatorResult validatorResult = ValidatorUtil.prepareValidatorResult(propertyName);
		if (!metadata.isTrue() || value == null) {
			validatorResult.setResult(true);
			return new Future<>(validatorResult);
		}
		
		EntityQueryBuilder entityQueryBuilder = EntityQueryBuilder//
				.from(entitySignatureAndPropertyName.getEntityTypeSignature())//
				.orderBy(propertyName, OrderingDirection.ascending);
		Object entityId = entity.getId();
		if (entityId == null) {
			entityQueryBuilder = entityQueryBuilder.where()//
					.property(propertyName).eq(value.toString());
		} else {
			entityQueryBuilder = entityQueryBuilder.where()
					.conjunction()
						.property(propertyName).eq(value.toString())
						.property(GenericEntity.id).ne(entityId)
					.close();
		}
		EntityQuery entityQuery = entityQueryBuilder.done();
		
		Future<ValidatorResult> future = new Future<>();
		validationContext.getGmSession().query().entities(entityQuery).result(com.braintribe.processing.async.api.AsyncCallback.of(conv -> {
			try {
				EntityQueryResult result = conv.result();
				boolean valueFound = false;
				Object entityValue = entity.entityType().getProperty(propertyName).get(entity);
				Object entityIdValue = entity.getId();
				EntityType<GenericEntity> metaDataEntityType = GMF.getTypeReflection()
						.getType(entitySignatureAndPropertyName.getEntityTypeSignature());
				for (GenericEntity localChangedEntity : validationContext.getChangedEntitiesByEntityType(metaDataEntityType)) {
					Object localChangedEnityValue = metaDataEntityType.getProperty(propertyName).get(localChangedEntity);
					Object localChangedEntityIdValue = localChangedEntity.getId();
					if (entityIdValue != null && localChangedEntityIdValue != null && !localChangedEntityIdValue.equals(entityIdValue)) {
						valueFound = entityValue.equals(localChangedEnityValue);
						break;
					} else if (entity != localChangedEntity) {
						valueFound = entityValue.equals(localChangedEnityValue);
						break;
					}
				}
				
				String message = displayInfo + ": '" + value + "' " + LocalizedText.INSTANCE.uniqueMessage();
				validatorResult.setResultMessage(message);
				validatorResult.setResult(result.getEntities().isEmpty() && !valueFound);
				future.onSuccess(validatorResult);
			} catch (GmSessionException e) {
				future.onFailure(e);
			}
		}, future::onFailure));
		
		return future;
	}

}
