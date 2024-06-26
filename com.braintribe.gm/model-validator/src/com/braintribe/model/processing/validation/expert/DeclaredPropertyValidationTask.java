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
package com.braintribe.model.processing.validation.expert;

import static com.braintribe.model.processing.validation.ValidationMessageLevel.ERROR;
import static com.braintribe.model.processing.validation.expert.CommonChecks.isNotNull;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.validation.ValidationContext;

public class DeclaredPropertyValidationTask implements ValidationTask {

	private GmMetaModel model;
	private GmEntityType declaringType;
	private GmProperty property;

	public DeclaredPropertyValidationTask(GmMetaModel model, GmEntityType declaringType, GmProperty property) {
		this.model = model;
		this.declaringType = declaringType;
		this.property = property;
	}

	@Override
	public void execute(ValidationContext context) {
		if (!declaringType.equals(property.getDeclaringType())) {
			context.addValidationMessage(property, ERROR, "Declaring type is wrong");
		}
		context.addValidationTask(new PropertyValidationTask(property));
		if (isNotNull(property.getType())) {
			context.addValidationTask(new ReferencedTypeValidationTask(model, property.getType()));
		}
	}
}
