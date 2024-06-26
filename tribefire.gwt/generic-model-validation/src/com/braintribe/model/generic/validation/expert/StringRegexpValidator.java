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
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.validation.ValidatorResult;
import com.braintribe.model.generic.validation.util.ValidatorUtil;
import com.braintribe.model.meta.data.constraint.Pattern;

public class StringRegexpValidator extends AbstractValidator<Pattern> {

	@Override
	public Future<ValidatorResult> validateValue(ValidationContext validationContext, GenericEntity entity, Pattern metadata,
			EntitySignatureAndPropertyName entitySignatureAndPropertyName, Object value) {
		String propertyName = entitySignatureAndPropertyName.getPropertyName();
		
		ValidatorResult validatorResult = ValidatorUtil.prepareValidatorResult(propertyName);
		validatorResult.setMessageType(ValidatorMessageType.regex);

		String stringValue = (String) value;
		if (stringValue != null)
			validatorResult.setResult(stringValue.matches(metadata.getExpression()));
		
		String propertyDisplayName = ValidatorUtil.getPropertyDisplayName(getGmSession(), entity, propertyName);
		String message = propertyDisplayName + " " + LocalizedText.INSTANCE.stringRegexpMessage();
		validatorResult.setResultMessage(message);
		
		return new Future<>(validatorResult);
	}

}
