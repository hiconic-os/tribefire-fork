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
package com.braintribe.model.processing.lock.api;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

/** @deprecated use {@link Locking} */
@Deprecated
public interface LockManager {

	LockBuilder forIdentifier(String id);

	default LockBuilder forEntity(GenericEntity entity) {
		StringBuilder builder = new StringBuilder();
		EntityType<?> entityType = entity.entityType();
		builder.append(entityType.getTypeSignature());

		Object value = entity.getId();
		if (value != null) {
			builder.append('[');
			builder.append(value.toString());
			builder.append(']');

			GmSession session = entity.session();
			if (session instanceof PersistenceGmSession) {
				PersistenceGmSession persistenceGmSession = (PersistenceGmSession) session;
				String accessId = persistenceGmSession.getAccessId();
				if (accessId != null) {
					builder.append('@');
					builder.append(accessId);
				}
			}
		}

		String identifier = builder.toString();
		return forIdentifier(identifier);
	}

	default LockBuilder forType(EntityType<?> entityType) {
		return forIdentifier(entityType.getTypeSignature());
	}

	default LockBuilder forType(Class<? extends GenericEntity> entityType) {
		return forIdentifier(entityType.getName());
	}

	default LockBuilder forReference(PersistentEntityReference reference) {
		return forIdentifier(reference.getTypeSignature() + '[' + reference.getRefId() + ']');
	}

	String description();
}
