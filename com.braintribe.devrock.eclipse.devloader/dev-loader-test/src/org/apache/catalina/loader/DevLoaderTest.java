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
package org.apache.catalina.loader;

import static org.apache.catalina.loader.Helpers.list;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.braintribe.testing.test.AbstractTest;

/**
 * Provides tests for {@link DevLoader}.
 *
 * @author michael.lafite
 */
public class DevLoaderTest extends AbstractTest {

	static {
		DevLoader.dummyPathReplacement = testDir(DevLoaderTest.class).getParentFile().getParentFile().getAbsolutePath();
	}

	@Test
	public void test_ac_ac() {
		// @formatter:off
		List<String> expectedClassPath = list(
				"/absolute/path/to/res/DevLoaderTest/localRepository/com/braintribe/model/test-model/2.3/test-model-2.3.jar",
				"/absolute/path/to/res/DevLoaderTest/projects/ac_ac/simple-data-model/2.0/bin",
				"/absolute/path/to/res/DevLoaderTest/projects/ac_ac/simple-module/2.0/classes");
		// @formatter:on
		test(existingTestDir("projects/ac_ac/simple-module/2.0/context"), expectedClassPath);
	}

	@Test
	public void test_ac_ac_noContainerInTomcatPluginSettings() {
		// @formatter:off
		List<String> expectedClassPath = list(
				"/absolute/path/to/res/DevLoaderTest/localRepository/com/braintribe/model/test-model/2.3/test-model-2.3.jar",
				"/absolute/path/to/res/DevLoaderTest/projects/ac_ac_noContainerInTomcatPluginSettings/simple-data-model/2.0/bin",
				"/absolute/path/to/res/DevLoaderTest/projects/ac_ac_noContainerInTomcatPluginSettings/simple-module/2.0/classes");
		// @formatter:on
		test(existingTestDir("projects/ac_ac_noContainerInTomcatPluginSettings/simple-module/2.0/context"), expectedClassPath);
	}

	@Test
	public void test_gradle_etp() {
		// @formatter:off
		List<String> expectedClassPath = list(
			"/absolute/path/to/res/DevLoaderTest/localRepository/com/braintribe/model/test-model/2.3/test-model-2.3.jar",
			"/absolute/path/to/res/DevLoaderTest/projects/gradle_etp/simple-data-model/bin",
			"/absolute/path/to/res/DevLoaderTest/projects/gradle_etp/simple-module/bin");
		// @formatter:on
		test(existingTestDir("projects/gradle_etp/simple-module/src/main/webapp"), expectedClassPath);
	}

	@Test
	public void test_gradle_gradle() {
		// @formatter:off
		List<String> expectedClassPath = list(
			"/absolute/path/to/res/DevLoaderTest/localRepository/com/braintribe/model/test-model/2.3/test-model-2.3.jar",
			"/absolute/path/to/res/DevLoaderTest/projects/gradle_gradle/simple-data-model/bin",
			"/absolute/path/to/res/DevLoaderTest/projects/gradle_gradle/simple-module/bin");
		// @formatter:on
		test(existingTestDir("projects/gradle_gradle/simple-module/src/main/webapp"), expectedClassPath);
	}

	@Test
	public void test_maven_etp() {
		// @formatter:off
		List<String> expectedClassPath = list(
			"/absolute/path/to/res/DevLoaderTest/localRepository/com/braintribe/model/test-model/2.3/test-model-2.3.jar",
			"/absolute/path/to/res/DevLoaderTest/projects/maven_etp/simple-data-model/target/classes",
			"/absolute/path/to/res/DevLoaderTest/projects/maven_etp/simple-module/target/classes");
		// @formatter:on
		test(existingTestDir("projects/maven_etp/simple-module/src/main/webapp"), expectedClassPath);
	}

	@Test
	public void test_maven_maven() {
		// @formatter:off
		List<String> expectedClassPath = list(
			"/absolute/path/to/res/DevLoaderTest/localRepository/com/braintribe/model/test-model/2.3/test-model-2.3.jar",
			"/absolute/path/to/res/DevLoaderTest/projects/maven_maven/simple-data-model/target/classes",
			"/absolute/path/to/res/DevLoaderTest/projects/maven_maven/simple-module/target/classes");
		// @formatter:on
		test(existingTestDir("projects/maven_maven/simple-module/src/main/webapp"), expectedClassPath);
	}

	private void test(File webappDir, List<String> expectedClassPath) {
		List<String> actualClassPath = DevLoader.readWebClassPathEntries(webappDir);
		expectedClassPath = DevLoader.replaceDummyPaths(expectedClassPath);
		assertThat(actualClassPath).isEqualTo(expectedClassPath);
	}
}
