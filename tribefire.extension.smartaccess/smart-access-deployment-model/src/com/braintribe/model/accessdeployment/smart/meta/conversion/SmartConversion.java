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
package com.braintribe.model.accessdeployment.smart.meta.conversion;

import java.util.Date;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Represents a conversion of given (property) value.
 * <p>
 * The direction of the conversion is determined by the {@link #setInverse(boolean) inverse} flag. The standard
 * direction is the direction from delegate to the smart level, so for example a {@link DateToString} conversion (not
 * inverted) could be used if the property has type {@link Date} in the delegate, but is a {@link String} on the smart
 * level.
 */
@Abstract
public interface SmartConversion extends GenericEntity {

	EntityType<SmartConversion> T = EntityTypes.T(SmartConversion.class);

	void setInverse(boolean inverse);
	boolean getInverse();

}
