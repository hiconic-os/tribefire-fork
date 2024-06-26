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

import static tribefire.extension.wopi.model.WopiMetaDataConstants.NUMBER_OF_CHECKS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.NUMBER_OF_CHECKS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.SIMPLE_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.SIMPLE_NAME;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Max;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * Health check - opens all supported WOPI documents
 * 
 *
 */
public interface WopiHealthCheck extends WopiRequest {

	EntityType<WopiHealthCheck> T = EntityTypes.T(WopiHealthCheck.class);

	@Override
	EvalContext<? extends WopiHealthCheckResult> eval(Evaluator<ServiceRequest> evaluator);

	String simple = "simple";
	String prepareDocuments = "prepareDocuments";
	String numberOfChecks = "numberOfChecks";

	@Name(SIMPLE_NAME)
	@Description(SIMPLE_DESCRIPTION)
	@Mandatory
	@Initializer("true")
	boolean getSimple();
	void setSimple(boolean simple);

	@Name(NUMBER_OF_CHECKS_NAME)
	@Description(NUMBER_OF_CHECKS_DESCRIPTION)
	@Initializer("1")
	@Min("1")
	@Max("50")
	int getNumberOfChecks();
	void setNumberOfChecks(int numberOfChecks);

}