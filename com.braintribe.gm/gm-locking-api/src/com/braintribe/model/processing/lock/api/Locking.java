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

import java.util.concurrent.locks.ReadWriteLock;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.processing.lock.impl.SemaphoreBasedLocking;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;

/**
 * Represents a registry where @link{ReadWriteLock}s can be retrieved from, based on an identifier or an entity.
 * <p>
 * For sample implementation (which can be used in tests for example) see {@link SemaphoreBasedLocking}.
 */
public interface Locking {

	ReadWriteLock forIdentifier(String id);

	/**
	 * Equivalent to {@code forIdentifier(namespace + ":" + id)}
	 */
	default ReadWriteLock forIdentifier(String namespace, String id) {
		return forIdentifier(namespace + ":" + id);
	}

	/**
	 * Creates a String s representing given entity and calls {@code forIdentifier("entity", s)}
	 */
	default ReadWriteLock forEntity(GenericEntity entity) {
		StringBuilder builder = new StringBuilder();
		EntityType<?> entityType = entity.entityType();
		builder.append(entityType.getTypeSignature());

		Object id = entity.getId();
		if (id != null) {
			builder.append('[');
			builder.append(id.toString());
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
		return forIdentifier("entity", identifier);
	}

	/**
	 * Equivalent to {@code forIdentifier("entity-type", entityType.getTypeSignature())}
	 */
	default ReadWriteLock forType(EntityType<?> entityType) {
		return forIdentifier("entity-type", entityType.getTypeSignature());
	}

}
