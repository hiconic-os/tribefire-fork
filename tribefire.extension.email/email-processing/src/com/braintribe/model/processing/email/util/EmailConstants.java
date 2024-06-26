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
package com.braintribe.model.processing.email.util;

/**
 *
 */
public interface EmailConstants {

	// -----------------------------------------------------------------------
	// MISC
	// -----------------------------------------------------------------------

	String GROUP_ID = "com.braintribe.model.email";

	String GLOBAL_ID_SERVICE_PROCESSOR_PREFIX = "tribefire:serviceProcessor/";
	String GLOBAL_ID_EMAIL_PROFILE_PREFIX = "tribefire:emailProfile/";
	String GLOBAL_ID_CONNECTION_PREFIX = "tribefire:connection/";
	String GLOBAL_ID_ACCESS_PREFIX = "tribefire:access/";

	// -----------------------------------------------------------------------
	// MODELS
	// -----------------------------------------------------------------------

	String EMAIL_MODEL_NAME = "email-model";
	String EMAIL_MODEL_QUALIFIEDNAME = GROUP_ID + ":" + EMAIL_MODEL_NAME;

	String EMAIL_DEPLOYMENT_MODEL_NAME = "email-deployment-model";
	String EMAIL_DEPLOYMENT_MODEL_QUALIFIEDNAME = GROUP_ID + ":" + EMAIL_DEPLOYMENT_MODEL_NAME;

	String EMAIL_SERVICE_MODEL_NAME = "email-service-model";
	String EMAIL_SERVICE_MODEL_QUALIFIEDNAME = GROUP_ID + ":" + EMAIL_SERVICE_MODEL_NAME;

	// -----------------------------------------------------------------------
	// EXTERNAL IDs
	// -----------------------------------------------------------------------

	String EXTERNAL_ID_EMAIL_SERVICE_PROCESSOR = "email.serviceProcessor";
	String EXTERNAL_ID_EMAIL_HEALTHCHECK_PROCESSOR = "email.healthCheckProcessor";

}
