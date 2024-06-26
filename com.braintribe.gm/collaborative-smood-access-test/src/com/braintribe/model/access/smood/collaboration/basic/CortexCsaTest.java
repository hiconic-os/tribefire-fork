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
package com.braintribe.model.access.smood.collaboration.basic;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.data.prompt.Visible;
import com.braintribe.model.processing.session.api.collaboration.CollaborativeAccess;

/**
 * @see CollaborativeAccess
 * 
 * @author peter.gazdik
 */
public class CortexCsaTest extends AbstractCollaborativePersistenceTest {

	private static final String MODEL_NAME = "test:CsaTestModel";

	@Test
	public void creatingModelOnlyStoresInModelFile() {
		GmMetaModel model = session.create(GmMetaModel.T);
		model.setName(MODEL_NAME);
		session.commit();

		// @formatter:off
		baseFolderFsAssert
			.sub("config.json").isExistingFile_()
			.sub(trunkStageName).isDirectory()
				.sub("model.man").isExistingFile_()
				.sub("data.man").notExists_();
		// @formatter:on
	}

	@Test
	public void creatingModelWithMetaDataStoresInBothFiles() {
		Visible visible = session.create(Visible.T);

		GmMetaModel model = session.create(GmMetaModel.T);
		model.setName(MODEL_NAME);
		model.getMetaData().add(visible);
		session.commit();

		// @formatter:off
		baseFolderFsAssert
			.sub("config.json").isExistingFile_()
			.sub(trunkStageName).isDirectory()
				.sub("model.man").isExistingFile_()
				.sub("data.man").isExistingFile();
		// @formatter:on
	}

	@Test
	public void deletingModelCleansNonSkeletonProperties() {
		Visible visible = session.create(Visible.T);

		GmMetaModel model = session.create(GmMetaModel.T);
		model.setGlobalId(MODEL_NAME);
		model.getMetaData().add(visible);
		session.commit();

		session.deleteEntity(model);
		session.commit();

		model = session.create(GmMetaModel.T);
		model.setGlobalId(MODEL_NAME);
		session.commit();

		redeploy();

		model = session.findEntityByGlobalId(MODEL_NAME);

		assertThat(model).isNotNull();
		assertThat(model.getMetaData()).isEmpty();
	}
}
