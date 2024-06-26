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
package com.braintribe.gwt.tribefirejs.client.remote;

import com.braintribe.cfg.Required;
import com.braintribe.gwt.gmresource.session.GwtSessionResourceSupport;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.session.api.persistence.auth.SessionAuthorization;
import com.braintribe.model.processing.session.api.resource.ResourceAccess;
import com.braintribe.model.processing.session.impl.persistence.TransientPersistenceGmSession;
import com.braintribe.model.service.api.ServiceRequest;

/**
 * @author peter.gazdik
 */
public class TfjsTransientPersistenceGmSession extends TransientPersistenceGmSession {

	private Evaluator<ServiceRequest> evaluator;
	private SessionAuthorization sessionAuthorization;
	private GwtSessionResourceSupport resourceSupport;

	// @formatter:off
	@Required public void setEvaluator(Evaluator<ServiceRequest> evaluator) { this.evaluator = evaluator; }
	@Required public void setSessionAuthorization(SessionAuthorization sessionAuthorization) { this.sessionAuthorization = sessionAuthorization; }
	@Required public void setResourcesAccessFactory(GwtSessionResourceSupport resourceSupport) { this.resourceSupport = resourceSupport; }
	// @formatter:on
	
	@Override
	protected Evaluator<ServiceRequest> getRequestEvaluator() {
		return evaluator;
	}

	@Override
	public SessionAuthorization getSessionAuthorization() {
		return sessionAuthorization;
	}

	@Override
	public ResourceAccess resources() {
		return resourceSupport.newInstanceForDomainId(accessId);
	}

}
