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

import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SESSION_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SESSION_NAME;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.wopi.WopiSession;

/**
 * Download current resource of WOPI session
 * 
 *
 */
public interface DownloadCurrentResource extends WopiRequest {

	EntityType<DownloadCurrentResource> T = EntityTypes.T(DownloadCurrentResource.class);

	@Override
	EvalContext<? extends DownloadCurrentResourceResult> eval(Evaluator<ServiceRequest> evaluator);

	String wopiSession = "wopiSession";

	@Name(WOPI_SESSION_NAME)
	@Description(WOPI_SESSION_DESCRIPTION)
	WopiSession getWopiSession();
	void setWopiSession(WopiSession wopiSession);

}