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

import org.junit.Test;

import com.braintribe.model.access.collaboration.CollaborativeAccessManager;
import com.braintribe.model.access.smood.collaboration.manager.model.StagedEntity;
import com.braintribe.model.cortexapi.access.collaboration.CollaborativeStageStats;
import com.braintribe.model.cortexapi.access.collaboration.GetCollaborativeStageStats;
import com.braintribe.testing.junit.assertions.assertj.core.api.Assertions;

/**
 * @see GetCollaborativeStageStats
 * @see CollaborativeAccessManager
 *
 * @author peter.gazdik
 */
public class GetStageStatsCsaTest extends AbstractCollaborativeAccessManagerTest {

	final String newStageName = "newStage";

	@Test
	public void emptyIsReallyEmpty() {
		CollaborativeStageStats stats = getStageStats(trunkStageName);
		assertStatsIsEmpty(stats);
	}

	@Test
	public void withEntityIsNotEmpty() {
		StagedEntity e = session.create(StagedEntity.T);
		e.setName("e");
		session.commit();

		CollaborativeStageStats stats = getStageStats(trunkStageName);
		assertStats(stats, 1, 2 /* name + globalId */, 0);
	}

	@Test
	public void deleteInNewStage() {
		StagedEntity e = session.create(StagedEntity.T);
		session.commit();

		pushNewStage(newStageName);
		session.deleteEntity(e);
		session.commit();

		CollaborativeStageStats stats = getStageStats(newStageName);
		assertStats(stats, 0, 0, 1);
	}

	public static void assertStatsIsEmpty(CollaborativeStageStats stats) {
		Assertions.assertThat(stats.isEmpty()).isTrue();
	}

	public static void assertStats(CollaborativeStageStats stats, int instantiations, int updates, int deletes) {
		Assertions.assertThat(stats.getInstantiations()).isEqualTo(instantiations);
		Assertions.assertThat(stats.getUpdates()).isEqualTo(updates);
		Assertions.assertThat(stats.getDeletes()).isEqualTo(deletes);
	}


}
