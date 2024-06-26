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
package tribefire.extension.gcp.initializer.wire.contract;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;
import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

/*
 * For compatibility reasons, this is not using the PropertyDefinitionsContract yet. This will be activated later.
 */
public interface RuntimePropertiesContract extends PropertyLookupContract {

	@Decrypt
	String GCP_JSON_CREDENTIALS();

	String GCP_PRIVATE_KEY_ID();
	@Decrypt
	String GCP_PRIVATE_KEY();
	String GCP_CLIENT_ID();
	String GCP_CLIENT_EMAIL();
	String GCP_TOKEN_SERVER_URI();
	String GCP_PROJECT_ID();
	
	String GCP_STORAGE_BUCKETNAME();

	String GCP_PATH_PREFIX();

	@Default("true")
	boolean GCP_CREATE_DEFAULT_STORAGE_BINARY_PROCESSOR();
}
