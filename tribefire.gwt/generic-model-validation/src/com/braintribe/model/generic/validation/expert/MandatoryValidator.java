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

import java.util.Collection;
import java.util.Map;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.validation.ValidatorResult;
import com.braintribe.model.generic.validation.util.ValidatorUtil;
import com.braintribe.model.meta.data.constraint.Mandatory;

public class MandatoryValidator extends Validator<Mandatory> {
	
	private ValidatorResult validatorResult;
	
	@Override
	public Future<ValidatorResult> validate(ValidationContext validationContext, GenericEntity entity, Mandatory propertyMetaData,
			final EntitySignatureAndPropertyName entitySignatureAndPropertyName) {
		String propertyName = prepareValidation(validationContext, entity, entitySignatureAndPropertyName);
		
		if (!propertyMetaData.isTrue()) {
			validatorResult.setResult(true);
			return new Future<>(validatorResult);
		}
		
		EntityType<GenericEntity> type = entity.entityType();
		Property property = type.getProperty(propertyName);
		if (!ValidatorUtil.checkPropertyAbsense(entity, propertyName)) {
			Object value = property.get(entity);
			checkValue(property, value);
			return new Future<>(validatorResult);
		}
		
		Future<ValidatorResult> future = new Future<>();
		ValidatorUtil.fetchAbsentValue(validationContext, entity, propertyName).andThen(result -> {
			checkValue(property, result);
			future.onSuccess(validatorResult);
		}).onError(future::onFailure);
		
		return future;
	}

	private String prepareValidation(ValidationContext validationContext, GenericEntity entity,
			EntitySignatureAndPropertyName entitySignatureAndPropertyName) {
		String propertyName = entitySignatureAndPropertyName.getPropertyName();
		validatorResult = ValidatorUtil.prepareValidatorResult(propertyName);
		
		String propertyDisplayName = ValidatorUtil.getPropertyDisplayName(getGmSession(), entity, propertyName);
		
		String message;
		if (validationContext.isShortMessageStyle())
			message = LocalizedText.INSTANCE.stringDenyEmpty();			
		else	
			message = LocalizedText.INSTANCE.mandatoryMessage(propertyDisplayName);
		validatorResult.setMessageType(ValidatorMessageType.mandatory);
		validatorResult.setResultMessage(message);
		
		return propertyName;
	}

	@Override
	public Future<ValidatorResult> validateValue(ValidationContext validationContext, GenericEntity entity,
			Mandatory propertyMetaData, EntitySignatureAndPropertyName entitySignatureAndPropertyName, Object value) {
		String propertyName = prepareValidation(validationContext, entity, entitySignatureAndPropertyName);
		
		if (!propertyMetaData.isTrue()) {
			validatorResult.setResult(true);
			return new Future<>(validatorResult);
		}
		
		EntityType<GenericEntity> type = entity.entityType();
		Property property = type.getProperty(propertyName);

		checkValue(property, value);
		return new Future<>(validatorResult);
	}
	
	private void checkValue(Property property, Object value) {
		if (!property.getType().isCollection())
			validatorResult.setResult(value != null);
		else {
			int collectionSize = 0;
			if (value != null) {
				if (value instanceof Collection)
					collectionSize = ((Collection<?>) value).size();
				else
					collectionSize = ((Map<?,?>) value).size();
			}
			
			validatorResult.setResult(collectionSize > 0);
		}
	}

}
