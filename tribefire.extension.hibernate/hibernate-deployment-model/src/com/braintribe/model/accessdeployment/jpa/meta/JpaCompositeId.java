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
package com.braintribe.model.accessdeployment.jpa.meta;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Hibernate mapping meta-data which specifies the mapping for the id property in case the corresponding table uses a composite key.
 * 
 * Note that this mapping is only considered if attached to an id property.
 * 
 * See HibernateAccess for more information regarding composite id mappins.
 */
public interface JpaCompositeId extends JpaPropertyMapping {

	EntityType<JpaCompositeId> T = EntityTypes.T(JpaCompositeId.class);

	List<JpaColumn> getColumns();
	void setColumns(List<JpaColumn> columns);

}
