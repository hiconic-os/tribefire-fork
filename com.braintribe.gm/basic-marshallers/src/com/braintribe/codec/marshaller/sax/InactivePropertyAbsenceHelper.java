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
package com.braintribe.codec.marshaller.sax;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;

class InactivePropertyAbsenceHelper extends PropertyAbsenceHelper {
	
	public static InactivePropertyAbsenceHelper instance = new InactivePropertyAbsenceHelper();
	@Override
	public void addPresent(Property property) {
		//Intentionally left blank
	}
	
	@Override
	public void ensureAbsenceInformation(EntityType<?> entityType, GenericEntity entity) {
		//Intentionally left blank
	}
}
