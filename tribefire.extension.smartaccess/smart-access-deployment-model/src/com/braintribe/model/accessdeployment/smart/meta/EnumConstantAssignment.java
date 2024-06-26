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
package com.braintribe.model.accessdeployment.smart.meta;

import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.data.EnumConstantMetaData;

/**
 * Mapping which is configured for a {@link GmEnumConstant} of a smart enum, defining what given constant is mapped to
 * in the delegate.
 * 
 * IMPORTANT NOTE: In case we also have a {@link SmartConversion} defined for an enum property, only the conversion is
 * considered (so the constant assignment is ignored). So you could say this {@link EnumConstantAssignment} is just a
 * convenient way to configure all the conversions (for all the enum-related properties) at the same time. This means a
 * conversion for an enum property must always say how the original enum is converted, without having to consider what
 * is mapped to by default via this meta-data.
 * 
 * @see ConversionEnumConstantAssignment
 * @see IdentityEnumConstantAssignment
 * @see QualifiedEnumConstantAssignment
 */
@Abstract
public interface EnumConstantAssignment extends EnumConstantMetaData {

	EntityType<EnumConstantAssignment> T = EntityTypes.T(EnumConstantAssignment.class);

}
