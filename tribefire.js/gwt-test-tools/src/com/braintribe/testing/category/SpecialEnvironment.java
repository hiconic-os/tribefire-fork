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
 * Signals that the test can only be executed in a special environment. Main purpose of this category is to indicate
 * that a (default) CI can't run the test.<br>
 * Sub types of this category further specify the requirements.
 *
 * @author michael.lafite
 *
 * @see SpecialExternalSystems
 * @see SpecialResources
 */
public interface SpecialEnvironment {
	// no methods required
}
