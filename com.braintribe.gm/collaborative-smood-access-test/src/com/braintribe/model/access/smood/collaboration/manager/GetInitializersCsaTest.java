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
package com.braintribe.model.access.smood.collaboration.manager;

import static com.braintribe.utils.lcd.CollectionTools2.first;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.braintribe.model.access.smood.collaboration.manager.model.StagedEntity;
import com.braintribe.model.cortexapi.access.collaboration.GetCollaborativeInitializers;
import com.braintribe.model.csa.SmoodInitializer;
import com.braintribe.model.meta.GmMetaModel;

/**
 * @author peter.gazdik
 */
public class GetInitializersCsaTest extends AbstractCollaborativeAccessManagerTest {

	/** Smoke test for {@link GetCollaborativeInitializers}. */
	@Test
	public void emptyConfiguration() {
		List<SmoodInitializer> initializers = getInitializers();

		assertThat(initializers).isNotNull().hasSize(1);

		assertSmoodInitializer(first(initializers), trunkStageName);
	}

	/**
	 * We simply create a Collaborative setup and test that we have wired it correctly. So just check that after
	 * creating an entity the persistence resources have the right structure.
	 */
	@Test
	public void simpleConfiguration() {
		session.create(GmMetaModel.T);
		session.create(StagedEntity.T);
		session.commit();

		pushNewStage("stage1");
		pushNewStage("stage2");

		List<SmoodInitializer> initializers = getInitializers();
		assertThat(initializers).hasSize(3);

		Iterator<SmoodInitializer> it = initializers.iterator();
		assertSmoodInitializer(it.next(), trunkStageName);
		assertSmoodInitializer(it.next(), "stage1");
		assertSmoodInitializer(it.next(), "stage2");
	}

	@Test
	public void simpleConfigurationAfterRename() {
		session.create(GmMetaModel.T);
		session.create(StagedEntity.T);
		session.commit();

		pushNewStage("stage1");

		renameStage("stage1", "stage2");

		List<SmoodInitializer> initializers = getInitializers();
		assertThat(initializers).hasSize(2);

		Iterator<SmoodInitializer> it = initializers.iterator();
		assertSmoodInitializer(it.next(), trunkStageName);
		assertSmoodInitializer(it.next(), "stage2");
	}

	private void assertSmoodInitializer(SmoodInitializer initializer, String name) {
		assertThat(initializer.getName()).isEqualTo(name);
	}

}
