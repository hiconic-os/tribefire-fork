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

import org.junit.Test;

import com.braintribe.model.access.smood.collaboration.manager.model.StagedEntity;
import com.braintribe.model.cortexapi.access.collaboration.CollaborativeStageData;
import com.braintribe.model.cortexapi.access.collaboration.GetCollaborativeStageData;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.resource.FileResource;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.BlobSource;
import com.braintribe.model.resource.source.FileSystemSource;

/**
 * @author peter.gazdik
 */
public class GetStagedDataCsaTest extends AbstractCollaborativeAccessManagerTest {

	/** Smoke test for {@link GetCollaborativeStageData}. */
	@Test
	public void emptySmood() {
		CollaborativeStageData stageData = getStageData(trunkStageName);

		assertStagedData(stageData, false, false, 0);
	}

	/**
	 * We simply create a Collaborative setup and test that we have wired it correctly. So just check that after
	 * creating an entity the persistence resources have the right structure.
	 */
	@Test
	public void dataOnlyStageConfiguration() {
		session.create(GmMetaModel.T);
		session.create(StagedEntity.T);
		session.commit();

		pushNewStage("dataOnly");
		session.create(StagedEntity.T);
		session.commit();

		CollaborativeStageData stageData = getStageData("dataOnly");

		assertStagedData(stageData, true, false, 0);
	}

	@Test
	public void dataOnlyWithResourcesConfiguration() {
		session.create(GmMetaModel.T);
		session.create(StagedEntity.T);
		session.commit();
		
		String RELATIVE_PATH = "relative/path/x.jpg";

		pushNewStage("dataOnly");
		createFileSystemResource(session, RELATIVE_PATH);
		createNonFileSystemResource(session);
		session.commit();
		
		CollaborativeStageData stageData = getStageData("dataOnly");
		
		assertStagedData(stageData, true, false, 1);
		
		Resource resource = stageData.getContentResources().get(RELATIVE_PATH);
		assertThat(resource).isNotNull().isInstanceOf(FileResource.class);

		FileResource fileResource = (FileResource) resource;
		assertThat(fileResource.getPath()).isEqualTo(csaUnit.resourceFile(RELATIVE_PATH).getAbsolutePath());
	}
	
	private void createFileSystemResource(PersistenceGmSession session, String relativePath) {
		FileSystemSource resourceSource  = session.create(FileSystemSource.T);
		resourceSource.setPath(relativePath);
		
		Resource resource = session.create(Resource.T);
		resource.setName("resource1");
		resource.setResourceSource(resourceSource);
	}

	private void createNonFileSystemResource(PersistenceGmSession session) {
		Resource resource = session.create(Resource.T);
		resource.setName("ignored");
		resource.setResourceSource(session.create(BlobSource.T));
	}

	public void modelOnlyStageConfiguration() {
		session.create(GmMetaModel.T);
		session.create(StagedEntity.T);
		session.commit();
		
		pushNewStage("modelOnly");
		session.create(GmMetaModel.T);
		session.commit();

		CollaborativeStageData stageData = getStageData("modelOnly");

		assertStagedData(stageData, false, true, 0);
	}
	
	@Test
	public void simpleConfigurationAfterRename() {
		session.create(GmMetaModel.T);
		session.create(StagedEntity.T);
		session.commit();

		pushNewStage("dataOnly");
		session.create(StagedEntity.T);
		session.commit();

		renameStage("dataOnly", "dataOnly2");

		CollaborativeStageData stageData = getStageData("dataOnly2");

		assertStagedData(stageData, true, false, 0);
	}

	private void assertStagedData(CollaborativeStageData stageData, boolean hasData, boolean hasModel, int numberOfResources) {
		assertNullity(stageData.getModelResource(), !hasModel);
		assertNullity(stageData.getDataResource(), !hasData);

		if (numberOfResources == 0)
			assertThat(stageData.getContentResources()).isEmpty();
		else
			assertThat(stageData.getContentResources()).hasSize(numberOfResources);
	}

	private void assertNullity(Object object, boolean expectedToBeNull) {
		if (expectedToBeNull)
			assertThat(object).isNull();
		else
			assertThat(object).isNotNull();
	}

	private CollaborativeStageData getStageData(String stageName) {
		return eval(getConfigurationRequest(stageName));
	}

	private GetCollaborativeStageData getConfigurationRequest(String stageName) {
		GetCollaborativeStageData result = GetCollaborativeStageData.T.create();
		result.setName(stageName);

		return result;
	}

}
