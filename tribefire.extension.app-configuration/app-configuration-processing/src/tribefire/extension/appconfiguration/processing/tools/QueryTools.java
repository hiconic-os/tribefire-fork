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
package tribefire.extension.appconfiguration.processing.tools;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

public class QueryTools {

	private QueryTools() {
		// no instantiation required
	}

	public static <T extends GenericEntity> T getEntity(PersistenceGmSession gmSession, EntityType<T> type, String propertyName,
			Object propertyValue) {
		return gmSession.query().entities(EntityQueryBuilder.from(type).where().property(propertyName).eq(propertyValue).done()).first();
	}

	public static <T extends GenericEntity> T getFirstEntity(PersistenceGmSession gmSession, EntityType<T> type) {
		return gmSession.query().entities(EntityQueryBuilder.from(type).done()).first();
	}

	public static <T extends GenericEntity> T getEntityById(PersistenceGmSession gmSession, EntityType<T> type, Object id) {
		return gmSession.query().entities(EntityQueryBuilder.from(type).where().property(GenericEntity.id).eq(id).done()).first();
	}

}
