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
package com.braintribe.wire.test.parenting.wire.derived.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.test.parenting.wire.derived.contract.ParentingTestDerivedContract;
import com.braintribe.wire.test.parenting.wire.parent.contract.DeriveCandidateContract;
import com.braintribe.wire.test.parenting.wire.parent.contract.OverrideCandidateContract;

@Managed
public class ParentingTestDerivedSpace implements ParentingTestDerivedContract {
	@Import
	private OverrideCandidateContract overrideCandidate;
	
	@Import
	private DeriveCandidateContract deriveCandidate;
	
	@Override
	public String textDerived() {
		return deriveCandidate.text();
	}
	
	@Override
	public String textOverridden() {
		return overrideCandidate.text();
	}
}
