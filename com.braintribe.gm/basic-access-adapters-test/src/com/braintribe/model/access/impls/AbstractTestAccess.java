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
package com.braintribe.model.access.impls;

import com.braintribe.model.access.BasicAccessAdapter;

/**
 * This implementation simply overrides some methods to avoid cloning of the data before returning it, thus ensuring
 * entities retrieved via queries are exactly the same s ones registered in the smood for the test.
 */
public abstract class AbstractTestAccess extends BasicAccessAdapter {

	// empty (there was code, overriding some methods, but I didn't like it as it was not really testing the BAA then)

}
