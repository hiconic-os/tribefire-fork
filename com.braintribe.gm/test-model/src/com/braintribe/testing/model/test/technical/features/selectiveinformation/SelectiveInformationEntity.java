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
package com.braintribe.testing.model.test.technical.features.selectiveinformation;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * An entity with a {@link SelectiveInformation}.
 *
 * @author michael.lafite
 */

@SelectiveInformation("[SelectiveInfo: stringProperty==${stringProperty},booleanProperty==${booleanProperty}]")
public interface SelectiveInformationEntity extends StandardStringIdentifiable {

	EntityType<SelectiveInformationEntity> T = EntityTypes.T(SelectiveInformationEntity.class);

	String getStringProperty();
	void setStringProperty(String stringProperty);

	Boolean getBooleanProperty();
	void setBooleanProperty(Boolean booleanProperty);
}
