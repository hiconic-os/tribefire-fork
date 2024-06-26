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

import java.util.Arrays;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.validation.ValidatorResult;
import com.braintribe.model.generic.validation.util.ValidatorUtil;
import com.braintribe.model.meta.data.constraint.Max;
import com.braintribe.model.processing.vde.evaluator.VDE;

public class MaxValidator extends AbstractValidator<Max> {

	@Override
	public Future<ValidatorResult> validateValue(ValidationContext validationContext, GenericEntity entity,
			Max metaData, EntitySignatureAndPropertyName entitySignatureAndPropertyName, Object value) {
		String propertyName = entitySignatureAndPropertyName.getPropertyName();
		String propertyDisplayName = ValidatorUtil.getPropertyDisplayName(getGmSession(), entity, propertyName);
		
		ValidatorResult validatorResult = ValidatorUtil.prepareValidatorResult(propertyName);
		
		Object limit = metaData.getLimit();
		if (limit == null) {
			validatorResult.setResult(true);
			return new Future<>(validatorResult);
		}
		
		String message;
		com.braintribe.model.bvd.math.Max maxVde = com.braintribe.model.bvd.math.Max.T.create();
		if (metaData.getExclusive()) {
			if (validationContext.isShortMessageStyle())
				message = LocalizedText.INSTANCE.stringLesserThan(limit.toString());
			else	
				message = LocalizedText.INSTANCE.less(propertyDisplayName, limit.toString());
			validatorResult.setMessageType(ValidatorMessageType.lessThan);
			maxVde.setOperands(Arrays.asList(value, limit)); //limit > value
		} else {
			if (validationContext.isShortMessageStyle())
				message = LocalizedText.INSTANCE.stringLesserEqual(limit.toString());
			else	
				message = LocalizedText.INSTANCE.lessEqual(propertyDisplayName, limit.toString());
			validatorResult.setMessageType(ValidatorMessageType.lessEqual);
			maxVde.setOperands(Arrays.asList(limit, value)); //limit >= value
		}
		
		Object compResult;
		if (value != null)
			compResult = VDE.evaluate(maxVde);
		else {
			if (validationContext.isCompleteConfiguredValidation())
				compResult = null;
			else
				compResult = limit;
		}
		
		validatorResult.setMessageParameter(limit.toString());
		validatorResult.setResult(compResult == limit || (compResult != null && compResult.equals(limit)));
		validatorResult.setResultMessage(message);
		
		return new Future<>(validatorResult);
	}

}
