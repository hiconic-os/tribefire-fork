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
package com.braintribe.model.processing.query.smart.test.builder.repo;

import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.session.exception.GmSessionException;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;

/**
 * 
 * @author peter.gazdik
 */
public class IncrementalAccessRepoDriver implements RepositoryDriver {

	private final IncrementalAccess access;
	private final BasicPersistenceGmSession session;

	public IncrementalAccessRepoDriver(IncrementalAccess access) {
		this.access = access;
		this.session = new BasicPersistenceGmSession(access);
	}

	@Override
	public <T extends GenericEntity> T newInstance(Class<T> clazz) {
		EntityType<T> entityType = GMF.getTypeReflection().getEntityType(clazz);
		return session.create(entityType);
	}

	@Override
	public void commit() {
		try {
			session.commit();

		} catch (GmSessionException e) {
			throw new RuntimeException("Test setup failed", e);
		}
	}

	@Override
	public void commitNoId() {
		try {
			session.commit();

		} catch (GmSessionException e) {
			throw new RuntimeException("Test setup failed", e);
		}
	}

	@Override
	public RepositoryDriver newRepoDriver() {
		return new IncrementalAccessRepoDriver(access);
	}
}
