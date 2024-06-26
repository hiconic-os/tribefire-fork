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

import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONTEXT_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.CONTEXT_NAME;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * Close all WOPI sessions
 * 
 *
 */
public interface CloseAllWopiSessions extends WopiRequest {

	EntityType<CloseAllWopiSessions> T = EntityTypes.T(CloseAllWopiSessions.class);

	@Override
	EvalContext<? extends CloseAllWopiSessionsResult> eval(Evaluator<ServiceRequest> evaluator);

	String context = "context";

	@Name(CONTEXT_NAME)
	@Description(CONTEXT_DESCRIPTION)
	String getContext();
	void setContext(String context);

}