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
package com.braintribe.model.meta.override;

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.info.GmEntityTypeInfo;
import com.braintribe.model.meta.info.GmPropertyInfo;
import com.braintribe.model.weaving.override.ProtoGmPropertyOverride;

@SelectiveInformation("${property.name} (override)")
public interface GmPropertyOverride extends ProtoGmPropertyOverride, GmPropertyInfo {

	EntityType<GmPropertyOverride> T = EntityTypes.T(GmPropertyOverride.class);

	@Override
	GmProperty getProperty();
	void setProperty(GmProperty property);

	@Override
	GmEntityTypeInfo getDeclaringTypeInfo();
	void setDeclaringTypeInfo(GmEntityTypeInfo declaringTypeInfo);

	@Override
	default GmProperty relatedProperty() {
		return getProperty();
	}

	@Override
	default GmEntityTypeInfo declaringTypeInfo() {
		return getDeclaringTypeInfo();
	}

}
