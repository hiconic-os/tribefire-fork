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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.braintribe.model.access.collaboration.CollaborativeAccessManager;
import com.braintribe.model.access.collaboration.persistence.AbstractManipulationPersistence;
import com.braintribe.model.access.smood.collaboration.manager.model.StagedEntity;
import com.braintribe.model.cortexapi.access.collaboration.ResetCollaborativePersistence;
import com.braintribe.model.csa.CollaborativeSmoodConfiguration;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;

/**
 * @see ResetCollaborativePersistence
 * @see CollaborativeAccessManager
 *
 * @author peter.gazdik
 */
public class ResetPersistenceCsaTest extends AbstractCollaborativeAccessManagerTest {

	final String newStageName = "new:Stage";
	final String newStageFolderName = AbstractManipulationPersistence.toLegalFileName(newStageName);

	/** Test data is gone after reset. */
	@Test
	public void resetDeletesData() {
		assertInitialState();

		session.create(StagedEntity.T);
		session.commit();

		resetPersistence();

		assertInitialState();
	}

	/** Test data in two different stages is gone after reset. */
	@Test
	public void resetsMultipleStages() {
		assertInitialState();

		session.create(StagedEntity.T);
		session.commit();

		closeStage();

		session.create(StagedEntity.T);
		session.commit();

		assertTrunkAndStage1();

		resetPersistence();

		assertInitialState();
	}

	/** Test data in two different stages is gone after reset. */
	@Test
	public void resetsToNonTrivialOriginalState() {
		assertInitialState();

		StagedEntity e = session.create(StagedEntity.T);
		e.setName("OriginalEntity");
		session.commit();

		closeStage();
		setCurrentConfigAsOriginal();

		e = session.create(StagedEntity.T);
		e.setName("TempEntity");
		session.commit();

		assertTrunkAndStage1();

		resetPersistence();

		assertNumberOfEntities(1);
		// @formatter:off
		baseFolderFsAssert
			.sub("config.json").isExistingFile_()
			.sub("stage1").isDirectory()
				.sub("data.man").isExistingFile_()
			.sup()
			.sub(trunkStageName).isDirectory()
				.sub("data.man").notExists_();
		// @formatter:on
	}

	private void closeStage() {
		renameStage("trunk", "stage1");
		pushNewStage("trunk");
	}

	private void setCurrentConfigAsOriginal() {
		CollaborativeSmoodConfiguration currentConfig = csaUnit.statePersistence.readConfiguration();
		csaUnit.statePersistence.overwriteOriginalConfiguration(currentConfig);
	}

	private void assertInitialState() {
		List<GenericEntity> allEntities = csa.queryEntities(EntityQueryBuilder.from(GenericEntity.T).done()).getEntities();
		assertThat(allEntities).as("CSA is not empty and so not in the initial state!").isEmpty();

		// @formatter:off
		baseFolderFsAssert
			.sub("config.json").isExistingFile_()
			.sub(trunkStageName).isDirectory()
				.sub("data.man").notExists_();
		// @formatter:on
	}

	private void assertTrunkAndStage1() {
		assertNumberOfEntities(2);

		// @formatter:off
		baseFolderFsAssert
			.sub("config.json").isExistingFile_()
			.sub("stage1").isDirectory()
				.sub("data.man").isExistingFile_()
			.sup()
			.sub(trunkStageName).isDirectory()
				.sub("data.man").isExistingFile_();
		// @formatter:on
	}

	private void assertNumberOfEntities(int entities) {
		List<GenericEntity> allEntities = csa.queryEntities(EntityQueryBuilder.from(GenericEntity.T).done()).getEntities();
		assertThat(allEntities).hasSize(entities);
	}

}
