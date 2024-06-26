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
package com.braintribe.model.bvd.string;


import com.braintribe.model.generic.value.type.StringDescriptor;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * A {@link StringDescriptor} that represents the localisation of a string with respect to a certain locale
 *
 */

public interface Localize extends StringDescriptor {

	final EntityType<Localize> T = EntityTypes.T(Localize.class);
	
	void setLocalizedString(Object localizedString); // LocalizedString
	Object getLocalizedString();
	
	void setLocale(Object locale); // String
	Object getLocale();
}
