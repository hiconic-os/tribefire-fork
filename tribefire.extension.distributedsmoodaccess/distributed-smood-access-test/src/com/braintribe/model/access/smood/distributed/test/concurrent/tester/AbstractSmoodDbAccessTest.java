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
package com.braintribe.model.access.smood.distributed.test.concurrent.tester;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.access.IncrementalAccess;
import com.braintribe.model.access.smood.distributed.test.concurrent.entities.MultiExtensionType;
import com.braintribe.model.access.smood.distributed.test.concurrent.entities.SingleExtensionType;
import com.braintribe.model.access.smood.distributed.test.concurrent.worker.WorkerFactory;
import com.braintribe.model.access.smood.distributed.test.wire.contract.DistributedSmoodAccessTestContract;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.user.User;

public abstract class AbstractSmoodDbAccessTest {

	protected WorkerFactory factory = null;
	protected DistributedSmoodAccessTestContract space = null;
	protected int workerCount = 1;

	public void executeTest() throws Exception {
		
		space.utils().clearTables();

		IncrementalAccess access = space.concurrentAccess(); 
		PersistenceGmSession session = space.utils().openSession(access);
		
		int iterations = this.factory.getIterations();
		int expectedCount = workerCount * iterations;

		long start = System.currentTimeMillis();
		this.executeWorkers(workerCount, iterations);
		long duration = System.currentTimeMillis() - start;

		List<User> users = space.utils().getUsers(session, "Mustermann");
		int finalCount = users.size();
		Assert.assertEquals(expectedCount, finalCount);
		System.out.println("Test was OK ("+finalCount+" equals "+expectedCount+") after "+duration+" ms");
		
		// Test ClassDataStorage interface
		
		Set<String> qualifiedNames = space.utils().getClassDataStorage().getQualifiedNamesOfStoredClasses();
		System.out.println("Known types:");
		System.out.println(qualifiedNames);
		Assert.assertEquals(workerCount+1, qualifiedNames.size());
		
		String entityTypeSignatureSingle = SingleExtensionType.class.getName()+"_runtime";
		EntityType<GenericEntity> entityTypeSingle = GMF.getTypeReflection().getEntityType(entityTypeSignatureSingle);
		
		List<GenericEntity> list = session.query().entities(EntityQueryBuilder.from(entityTypeSingle).done()).list();
		Assert.assertEquals(workerCount, list.size());
		
		for (GenericEntity ge : list) {
			Object value = entityTypeSingle.getProperty("name").get(ge);
			System.out.println(value);
		}
		this.assertSingleEntityListValidity(list, entityTypeSingle);
		
		for (int i=0; i<workerCount; ++i) {
			String entityTypeSignatureMulti = MultiExtensionType.class.getName()+"_runtime_"+i;
			EntityType<GenericEntity> entityTypeMulti = GMF.getTypeReflection().getEntityType(entityTypeSignatureMulti);

			list = session.query().entities(EntityQueryBuilder.from(entityTypeMulti).done()).list();
			Assert.assertEquals(1, list.size());
			GenericEntity ge = list.get(0);
			Object value = entityTypeMulti.getProperty("name").get(ge);
			Assert.assertEquals(""+i, value.toString());
		}
	}
	
	protected void assertSingleEntityListValidity(List<GenericEntity> list, EntityType<GenericEntity> entityType) throws AssertionError {
		
		Set<String> expectedSet = new HashSet<String>();
		for (int i=0; i<workerCount; ++i) {
			expectedSet.add(""+i);
		}
		for (GenericEntity ge : list) {
			Object value = entityType.getProperty("name").get(ge);
			String valueString = (String) value;
			Assert.assertEquals(true, expectedSet.remove(valueString));
		}
		Assert.assertEquals(0, expectedSet.size());
	}

	protected abstract void executeWorkers(int workerCountParam, int iterations) throws Exception;

	@Required
	public void setFactory(WorkerFactory factory) {
		this.factory = factory;
	}
	
	public int getWorkerCount() {
		return workerCount;
	}
	@Configurable
	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}
	public DistributedSmoodAccessTestContract getSpace() {
		return space;
	}
	@Configurable
	@Required
	public void setSpace(DistributedSmoodAccessTestContract space) {
		this.space = space;
	}

}
