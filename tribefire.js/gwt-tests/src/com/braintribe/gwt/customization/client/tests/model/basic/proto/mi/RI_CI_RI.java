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
package com.braintribe.gwt.customization.client.tests.model.basic.proto.mi;

import com.braintribe.gwt.customization.client.tests.model.basic.non_dynamic.CI1;
import com.braintribe.gwt.customization.client.tests.model.basic.proto.si.RI_CI;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@SuppressWarnings("unused")

public interface RI_CI_RI extends CI1, RI_CI {

	EntityType<RI_CI_RI> T = EntityTypes.T(RI_CI_RI.class);
}
