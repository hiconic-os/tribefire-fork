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
package tribefire.extension.simple;

/**
 * Provides constants which are used in various other classes.
 *
 * @author michael.lafite
 */
public interface SimpleConstants {

	/**
	 * The group id of the artifacts in this group.
	 */
	String ARTIFACTS_GROUPID = "tribefire.extension.simple";

	/**
	 * The fully qualified name for the deployment model.
	 */
	String DEPLOYMENT_MODEL_QUALIFIEDNAME = ARTIFACTS_GROUPID + ":simple-deployment-model";

	/**
	 * The fully qualified name for the data model.
	 */
	String DATA_MODEL_QUALIFIEDNAME = ARTIFACTS_GROUPID + ":simple-data-model";

	/**
	 * The fully qualified name for the service model.
	 */
	String SERVICE_MODEL_QUALIFIEDNAME = ARTIFACTS_GROUPID + ":simple-service-model";

	/**
	 * The (external) id of the access.
	 */
	String SIMPLE_ACCESS_EXTERNALID = "access.simple";

	/**
	 * The (external) id of the service.
	 */
	String SIMPLE_SERVICE_EXTERNALID = "service.simple";

	/**
	 * The (external) id of the web terminal.
	 */
	String SIMPLE_WEBTERMINAL_EXTERNALID = "webterminal.simple";

}
