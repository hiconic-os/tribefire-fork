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
package com.braintribe.model.meta;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.weaving.ProtoGmScalarType;

@Abstract
public interface GmScalarType extends ProtoGmScalarType, GmType, IsDeclaredInModel {

	EntityType<GmScalarType> T = EntityTypes.T(GmScalarType.class);

	@Override
	@Mandatory
	GmMetaModel getDeclaringModel();

	@Override
	default boolean isGmScalar() {
		return true;
	}
	
	@Override
	default GmMetaModel declaringModel() {
		return getDeclaringModel();
	}
}
