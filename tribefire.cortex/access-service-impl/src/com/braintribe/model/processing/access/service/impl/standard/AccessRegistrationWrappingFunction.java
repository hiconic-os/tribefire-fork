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
package com.braintribe.model.processing.access.service.impl.standard;

import com.braintribe.common.lcd.annotations.NonNull;
import com.braintribe.model.processing.access.service.api.registry.AccessRegistrationInfo;
import com.braintribe.model.processing.access.service.impl.standard.OriginAwareAccessRegistrationInfo.Origin;
import com.google.common.base.Function;

/**
 * {@link Function} implementation for wrapping an {@link AccessRegistrationInfo} in an
 * {@link OriginAwareAccessRegistrationInfo} . The origin of the wrapped elements has to be configured during
 * initialization.
 * 
 * 
 */
public class AccessRegistrationWrappingFunction implements
		Function<AccessRegistrationInfo, OriginAwareAccessRegistrationInfo> {

	private Origin originOfAccesses;

	public AccessRegistrationWrappingFunction(Origin originOfAccesses) {
		this.originOfAccesses = originOfAccesses;
	}

	/**
	 * Wraps an <code>accessRegistrationInfo</code> in a new {@link OriginAwareAccessRegistrationInfo}. Must not be
	 * <code>null</code>.
	 */
	@Override
	@NonNull
	public OriginAwareAccessRegistrationInfo apply(@NonNull AccessRegistrationInfo accessRegistrationInfo) {
		return new OriginAwareAccessRegistrationInfo(accessRegistrationInfo, originOfAccesses);
	}
}
