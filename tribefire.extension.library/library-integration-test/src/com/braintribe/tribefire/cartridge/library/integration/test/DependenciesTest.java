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
package com.braintribe.tribefire.cartridge.library.integration.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.library.service.dependencies.Dependencies;
import com.braintribe.model.library.service.dependencies.GetDependencies;

public class DependenciesTest extends LibraryIntegrationTestBase {

	// PGA: I'm commenting these tests out as they are not very robust.
	// Also: DocumentationTest
	/* I have deleted com.bt.gm:action-model and published the change. The I have deleted the dependency to that model from library-deployment-model,
	 * but these tests still fail. They are resolving this test artifact and as such see the already published version, not the locally built one,
	 * thus they still see the action-model dependency and resolution fails. */

	//@Test
	public void getAllDependencies() throws Exception {

		GetDependencies getDeps = GetDependencies.T.create();
		List<String> list = new ArrayList<>();
		list.add("tribefire.extension.library:library-integration-test#[3.0,3.1)");

		getDeps.setArtifactIdList(list);
		getDeps.setResolveDependencies(true);
		getDeps.setIncludeTerminalArtifact(false);

		EvalContext<? extends Dependencies> eval = getDeps.eval(librarySession);
		Dependencies deps = eval.get();

		log("Received dependencies with message: '" + deps.getMessage() + "'.");

		int internalDeps = 0;

		List<String> depsList = deps.getDependencies();
		log("====================================");
		log("Dependencies: (" + depsList.size() + ")");
		for (String l : depsList) {
			if (l.startsWith("com.braintribe") || l.startsWith("tribefire")) {
				internalDeps++;
			}
			log("   " + l);
		}
		log("====================================");

		assertThat(depsList.size()).isGreaterThan(100);
		assertThat(internalDeps).isGreaterThan(00);
	}

	//@Test
	public void getThirdPartyDependencies() throws Exception {

		GetDependencies getDeps = GetDependencies.T.create();
		List<String> list = new ArrayList<>();
		list.add("tribefire.extension.library:library-integration-test#[3.0,3.1)");

		getDeps.setArtifactIdList(list);
		getDeps.setResolveDependencies(true);
		getDeps.setIncludeTerminalArtifact(false);
		getDeps.getIgnoredDependencies().add("com.braintribe");
		getDeps.getIgnoredDependencies().add("tribefire");

		EvalContext<? extends Dependencies> eval = getDeps.eval(librarySession);
		Dependencies deps = eval.get();

		log("Received dependencies with message: '" + deps.getMessage() + "'.");

		int internalDeps = 0;

		List<String> depsList = deps.getDependencies();
		log("====================================");
		log("Dependencies: (" + depsList.size() + ")");
		for (String l : depsList) {
			if (l.startsWith("com.braintribe") || l.startsWith("tribefire")) {
				internalDeps++;
			}
			log("   " + l);
		}
		log("====================================");

		assertThat(depsList.size()).isGreaterThan(40);
		assertThat(depsList.size()).isLessThan(200);
		assertThat(internalDeps).isEqualTo(0);
	}

	@Test
	public void getWithoutDependencies() throws Exception {

		GetDependencies getDeps = GetDependencies.T.create();
		List<String> list = new ArrayList<>();
		list.add("tribefire.extension.library:library-integration-test#[3.0,3.1)");

		getDeps.setArtifactIdList(list);
		getDeps.setResolveDependencies(false);
		getDeps.setIncludeTerminalArtifact(true);

		EvalContext<? extends Dependencies> eval = getDeps.eval(librarySession);
		Dependencies deps = eval.get();

		log("Received dependencies with message: '" + deps.getMessage() + "'.");

		List<String> depsList = deps.getDependencies();
		log("====================================");
		log("Dependencies: (" + depsList.size() + ")");
		for (String l : depsList) {
			log("   " + l);
		}
		log("====================================");

		assertThat(depsList.size()).isEqualTo(1);
	}

	@Test
	public void getTwoWithoutDependencies() throws Exception {

		GetDependencies getDeps = GetDependencies.T.create();
		List<String> list = new ArrayList<>();
		list.add("tribefire.extension.library:library-integration-test#[3.0,3.1)");
		list.add("tribefire.extension.library:library-processing#[3.0,3.1)");

		getDeps.setArtifactIdList(list);
		getDeps.setResolveDependencies(false);
		getDeps.setIncludeTerminalArtifact(true);

		EvalContext<? extends Dependencies> eval = getDeps.eval(librarySession);
		Dependencies deps = eval.get();

		log("Received dependencies with message: '" + deps.getMessage() + "'.");

		List<String> depsList = deps.getDependencies();
		log("====================================");
		log("Dependencies: (" + depsList.size() + ")");
		for (String l : depsList) {
			log("   " + l);
		}
		log("====================================");

		assertThat(depsList.size()).isEqualTo(2);
	}
}
