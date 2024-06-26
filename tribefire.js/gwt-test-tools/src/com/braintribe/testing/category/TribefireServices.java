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
package com.braintribe.testing.category;

/**
 * Signals that the test requires (running) tribefire services accessible through default URLs. Currently these are
 * <code>http://localhost:8080/tribefire-services</code> and <code>https://localhost:8443/tribefire-services</code>.
 *
 * @author michael.lafite
 *
 * @deprecated This category was defined before the introduction of Platform Assets. Now one can use Platform Assets to
 *             create an integration test setup, which includes all the components (e.g. tribefire Services, tribefire
 *             Modeler, some cartridge ...) and also configuration (e.g. users, permissions, deployables) and test data,
 *             which the tests requires. URLs may depend on where the test is run, but these can easily be provided by
 *             the CI (e.g. via system properties). Therefore this category is no longer needed.
 */
@Deprecated
public interface TribefireServices extends SpecialEnvironment {
	// no methods required
}
