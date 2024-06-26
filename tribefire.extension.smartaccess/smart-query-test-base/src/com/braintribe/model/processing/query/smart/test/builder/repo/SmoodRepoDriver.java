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

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.smood.Smood;

/**
 * 
 * @author peter.gazdik
 */
public class SmoodRepoDriver implements RepositoryDriver {

	private final Smood smood;
	private GenericEntity entity;
	// This is just necessary, cause querying the underlying access for entity.partition property never sets the value automatically
	private final String defaultPartition;

	public SmoodRepoDriver(Smood smood, String defaultPartition) {
		this.smood = smood;
		this.defaultPartition = defaultPartition;
	}

	@Override
	public <T extends GenericEntity> T newInstance(Class<T> clazz) {
		EntityType<T> entityType = GMF.getTypeReflection().getEntityType(clazz);
		T t = entityType.create();
		t.setPartition(defaultPartition);
		entity = t;

		return t;
	}

	@Override
	public void commit() {
		smood.registerEntity(entity, true);
	}

	@Override
	public void commitNoId() {
		smood.registerEntity(entity, false);
	}

	@Override
	public RepositoryDriver newRepoDriver() {
		return new SmoodRepoDriver(smood, defaultPartition);
	}

}
