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
package tribefire.extension.demo.test.integration;

import org.junit.Test;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.testing.internal.suite.FolderStructure;

import tribefire.extension.demo.test.integration.utils.AbstractDemoTest;

public class WorkbenchFolderStructureTest extends AbstractDemoTest {

	@Test
	public void queryWorkbenchAccess() {
		logger.info("Assert that the structure of the workbench folders is like expected...");
		PersistenceGmSession demoWorkbenchSession = globalCortexSessionFactory.newSession(DEMO_WORKBENCH_ACCESS_ID);

		FolderStructure.Factory assertFolderStructure = new FolderStructure.Factory(demoWorkbenchSession);

		// @formatter:off

		logger.info("root...");
		assertFolderStructure.fromWorkbenchPerspective("root")
			.subFolder("tribefire")
				.subFolder("demo")
				.assertHasSubFolders("persons", "companies");

		logger.info("homeFolder...");
		assertFolderStructure.fromWorkbenchPerspective("homeFolder")
			.assertHasSubFolders("persons", "companies");

		logger.info("actionbar...");
		assertFolderStructure.fromWorkbenchPerspective("actionbar")
			.subFolder("actionbar")
			.assertHasExactlyTheseSubfoldersAndNoOthers(
					"$workWithEntity",
					"$gimaOpener",
					"$deleteEntity",
					"$changeInstance",
					"$clearEntityToNull",
					"$addToCollection",
					"$insertBeforeToList",
					"$removeFromCollection",
					"$clearCollection",
					"$refreshEntities",
					"$ResourceDownload",
					"$executeServiceRequest",
					"$addToClipboard");

		logger.info("headerbar...");
		assertFolderStructure.fromWorkbenchPerspective("headerbar")
			.subFolder("headerbar")
			.assertHasSubFolders(
					"tb_Logo",
					"$quickAccess-slot",
					"$settingsMenu",
						"$userMenu");

		logger.info("global-actionbar...");
		assertFolderStructure.fromWorkbenchPerspective("global-actionbar")
			.subFolder("global-actionbar")
			.assertHasSubFolders(
					"$new",
					"$dualSectionButtons",
					"$upload",
					"$undo",
					"$redo",
					"$commit");

		// @formatter:on

		logger.info("Test succeeded!");
	}

}
