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
package com.braintribe.model.malaclypse.cfg.denotations;

import com.braintribe.model.generic.base.EnumBase;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.generic.reflection.EnumTypes;

/**
 * currently, two different types 
 * - classpath : standard solution enrichment, no special order
 * - buildOrder: no enrichment, yet builder order order (so it can be built by a process), *not implemented yet*
 * @author Pit
 *
 */
public enum WalkKind implements EnumBase {
	classpath, buildOrder, hierarchy;
	
	
	final EnumType T = EnumTypes.T(WalkKind.class);

	@Override
	public EnumType type() {
		return T;
	}
}
