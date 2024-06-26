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
package com.braintribe.model.processing.securityservice.basic.test.wire.space.access;

import com.braintribe.gm._UserStatisticsModel_;
import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.wire.api.annotation.Managed;

@Managed
public class UserStatisticsAccessSpace extends SystemAccessSpaceBase {

	private static final String id = "user-statistics";
	private static final String modelName = _UserStatisticsModel_.reflection.name();

	@Override
	public String id() {
		return id;
	}

	@Override
	public String modelName() {
		return modelName;
	}

	@Managed
	@Override
	public IncrementalAccess rawAccess() {
		return smood();
	}

}
