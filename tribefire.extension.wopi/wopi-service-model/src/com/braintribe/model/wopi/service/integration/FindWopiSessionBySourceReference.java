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
package com.braintribe.model.wopi.service.integration;

import static tribefire.extension.wopi.model.WopiMetaDataConstants.SOURCE_REFERENCE_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.SOURCE_REFERENCE_NAME;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.MaxLength;
import com.braintribe.model.generic.annotation.meta.MinLength;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Unmodifiable;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * Finds a WOPI session by source reference
 * 
 *
 */
public interface FindWopiSessionBySourceReference extends WopiRequest {

	EntityType<FindWopiSessionBySourceReference> T = EntityTypes.T(FindWopiSessionBySourceReference.class);

	@Override
	EvalContext<? extends FindWopiSessionBySourceReferenceResult> eval(Evaluator<ServiceRequest> evaluator);

	String sourceReference = "sourceReference";

	@Name(SOURCE_REFERENCE_NAME)
	@Description(SOURCE_REFERENCE_DESCRIPTION)
	@Unmodifiable
	@Mandatory
	@MinLength(1)
	@MaxLength(255)
	String getSourceReference();
	void setSourceReference(String sourceReference);

}
