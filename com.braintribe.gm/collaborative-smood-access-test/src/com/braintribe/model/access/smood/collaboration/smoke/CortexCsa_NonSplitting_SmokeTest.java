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
package com.braintribe.model.access.smood.collaboration.smoke;

import static com.braintribe.utils.lcd.CollectionTools2.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.braintribe.model.access.smood.collaboration.deployment.CsaBuilder;
import com.braintribe.model.access.smood.collaboration.deployment.CsaDeployedUnit;
import com.braintribe.model.access.smood.collaboration.manager.model.CsaTestModel;
import com.braintribe.model.csa.CollaborativeSmoodConfiguration;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.ManipulationPersistenceException;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializer;
import com.braintribe.model.processing.session.api.collaboration.SimplePersistenceInitializer;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.smoodstorage.stages.PersistenceStage;

/**
 * @author peter.gazdik
 */
public class CortexCsa_NonSplitting_SmokeTest {

	private CsaDeployedUnit csaUnit;

	private static final String MODEL_GLOBAL_ID  = "static.SimpleNonSplittingSmokeTestModel";
	private static final String MODEL_NAME = "SimpleNonSplittingSmokeTestModel";

	@Test
	public void startsIfModelStageBuildsUponPreviousInitializerDataStage() throws Exception {
		csaUnit = deployCsa();

		PersistenceGmSession session = csaUnit.session;

		GmMetaModel staticModelEntity = session.findEntityByGlobalId(MODEL_GLOBAL_ID);
		checkEntity(staticModelEntity, ModelInitializer1.class.getName());

	}

	private CsaDeployedUnit deployCsa() {
		return CsaBuilder.create() //
				.baseFolder(new File("res/SimpleCsaSmokeTest")) //
				.cortex(true) //
				.mergeModelAndData(true) //
				.configurationSupplier(this::prepareNewConfiguration) // CONFIG
				.staticInitializers(staticInitializers()) // PRE
				.model(CsaTestModel.raw()) //
				.done();
	}

	private void checkEntity(GenericEntity entity, String expectedStageName) {
		assertThat(entity).isNotNull();

		PersistenceStage persitenceStage = csaUnit.csa.findStageForReference(entity.reference());
		assertThat(persitenceStage).isNotNull();
		assertThat(persitenceStage.getName()).isEqualTo(expectedStageName);
	}

	// ##########################################
	// ## . . . . . . . CONFIG . . . . . . . . ##
	// ##########################################

	private CollaborativeSmoodConfiguration prepareNewConfiguration() {
		return CollaborativeSmoodConfiguration.T.create();
	}

	// ##########################################
	// ## . . . . . . . . PRE . . . . . . . . .##
	// ##########################################

	private List<PersistenceInitializer> staticInitializers() {
		return asList(new ModelInitializer1(), new ModelInitializer2());
	}

	private static class ModelInitializer1 extends SimplePersistenceInitializer {


		@Override
		public void initializeData(PersistenceInitializationContext context) throws ManipulationPersistenceException {
			ManagedGmSession session = context.getSession();
			session.create(GmMetaModel.T, MODEL_GLOBAL_ID);
		}
	}

	private static class ModelInitializer2 extends SimplePersistenceInitializer {


		@Override
		public void initializeModels(PersistenceInitializationContext context) throws ManipulationPersistenceException {
			ManagedGmSession session = context.getSession();
			GmMetaModel model = session.getEntityByGlobalId(MODEL_GLOBAL_ID);
			model.setName(MODEL_NAME);
		}

	}

}
