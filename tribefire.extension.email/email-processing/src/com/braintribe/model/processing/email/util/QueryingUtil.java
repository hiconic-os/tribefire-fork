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
package com.braintribe.model.processing.email.util;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelException;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;

/**
 * Utility class related to querying
 * 
 *
 */
public class QueryingUtil {

	private QueryingUtil() {
		// This just pffers static convenience methods
	}

	public static final <T extends GenericEntity> T queryEntityByProperty(EntityType<T> entityType, String property, String propertyValue,
			PersistenceGmSession session) {

		// @formatter:off
		EntityQuery query = EntityQueryBuilder.from(entityType)
				.where()
				.property(property).eq(propertyValue)
				.done();
		// @formatter:on
		T entity = session.query().entities(query).unique();

		return entity;
	}

	public static final <T extends GenericEntity> T queryEntityByPropertyStrict(EntityType<T> entityType, String property, String propertyValue,
			PersistenceGmSession session) {

		// @formatter:off
		EntityQuery query = EntityQueryBuilder.from(entityType)
				.where()
				.property(property).eq(propertyValue)
				.done();
		// @formatter:on
		T entity = session.query().entities(query).unique();
		if (entity == null) {
			throw new GenericModelException("Could not find entity: '" + entityType.getTypeName() + "' with property: '" + property
					+ "' propertyValue: '" + propertyValue + "'");
		}

		return entity;
	}
}
