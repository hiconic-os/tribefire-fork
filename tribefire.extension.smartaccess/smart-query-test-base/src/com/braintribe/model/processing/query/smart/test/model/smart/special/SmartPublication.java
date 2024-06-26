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
package com.braintribe.model.processing.query.smart.test.model.smart.special;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.smart.BasicSmartEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartGenericEntity;

/**
 * TODO I had to change this entity to also have an id, so that I can reference it
 * {@link SmartReaderA#setFavoritePublication}. Maybe in the future that will not be needed (now I
 * need id cause SmartAccess cannot use unique properties for sub-type DQJ correlation yet).
 */
public interface SmartPublication extends StandardIdentifiable, SmartGenericEntity, BasicSmartEntity {

	EntityType<SmartPublication> T = EntityTypes.T(SmartPublication.class);

	String getTitle();
	void setTitle(String title);

	String getAuthor();
	void setAuthor(String author);

}
