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

import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.SimpleTypes;
import com.braintribe.model.weaving.ProtoGmDoubleType;

public interface GmDoubleType extends ProtoGmDoubleType, GmSimpleType {

	EntityType<GmDoubleType> T = EntityTypes.T(GmDoubleType.class);

	@Initializer("'double'")
	@Override
	String getTypeSignature();

	@Initializer("'type:double'")
	@Override
	String getGlobalId();

	@Override
	default GmTypeKind typeKind() {
		return GmTypeKind.DOUBLE;
	}

	@Override
	default GenericModelType reflectionType() {
		return SimpleTypes.TYPE_DOUBLE;
	}

	@Override
	default boolean isGmNumber() {
		return true;
	}

}
