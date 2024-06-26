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
import static tribefire.extension.wopi.model.WopiMetaDataConstants.FORCE_REMOVE_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.FORCE_REMOVE_NAME;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * Remove all WOPI sessions
 * 
 *
 */
public interface RemoveAllWopiSessions extends WopiRequest {

	EntityType<RemoveAllWopiSessions> T = EntityTypes.T(RemoveAllWopiSessions.class);

	@Override
	EvalContext<? extends RemoveAllWopiSessionsResult> eval(Evaluator<ServiceRequest> evaluator);

	String forceRemove = "forceRemove";
	String context = "context";

	@Name(FORCE_REMOVE_NAME)
	@Description(FORCE_REMOVE_DESCRIPTION)
	@Mandatory
	@Initializer("false")
	boolean getForceRemove();
	void setForceRemove(boolean forceRemove);

	@Name(CONTEXT_NAME)
	@Description(CONTEXT_DESCRIPTION)
	String getContext();
	void setContext(String context);

}
