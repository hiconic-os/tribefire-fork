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
 * Signals that the test may fail, but that this is a known issue the developer is already aware of. The purpose of this category is to be able to
 * exclude a unit test from automated tests while still being able to run it manually (from the IDE).<br>
 * This category should only be used in very special situations and with good reason!
 *
 * @author michael.lafite
 */
public interface KnownIssue {
	// no methods required
}
