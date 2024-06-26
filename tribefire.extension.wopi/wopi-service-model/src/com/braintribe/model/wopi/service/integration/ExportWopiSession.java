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

import java.util.Set;

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.wopi.WopiSession;

/**
 * Export a WOPI session
 * 
 *
 */
public interface ExportWopiSession extends WopiRequest {

	EntityType<ExportWopiSession> T = EntityTypes.T(ExportWopiSession.class);

	@Override
	EvalContext<? extends ExportWopiSessionResult> eval(Evaluator<ServiceRequest> evaluator);

	String wopiSessions = "wopiSessions";
	String includeDiagnosticPackage = "includeDiagnosticPackage";
	String includeCurrentResource = "includeCurrentResource";
	String includeResourceVersions = "includeResourceVersions";
	String includePostOpenResourceVersions = "includePostOpenResourceVersions";

	@Mandatory
	Set<WopiSession> getWopiSessions();
	void setWopiSessions(Set<WopiSession> wopiSessions);

	@Initializer("false")
	@Mandatory
	boolean getIncludeDiagnosticPackage();
	void setIncludeDiagnosticPackage(boolean includeDiagnosticPackage);

	@Initializer("true")
	@Mandatory
	boolean getIncludeCurrentResource();
	void setIncludeCurrentResource(boolean includeCurrentResource);

	@Initializer("true")
	@Mandatory
	boolean getIncludeResourceVersions();
	void setIncludeResourceVersions(boolean includeResourceVersions);

	@Initializer("true")
	@Mandatory
	boolean getIncludePostOpenResourceVersions();
	void setIncludePostOpenResourceVersions(boolean includePostOpenResourceVersions);

}
