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
package com.braintribe.testing.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Collections;

import org.junit.Test;

import com.braintribe.utils.FileTools;

/**
 * A dummy test which doesn't actually test anything, but prints info such as System properties and environment variables.
 *
 * @author michael.lafite
 */
public class DummyTest {

	@Test
	public void test() {
		System.out.println("Working directory: " + FileTools.getCanonicalPath("."));
		String separatorLine = "--------------------------------------------------------------------------------\n";

		StringBuilder builder = new StringBuilder();
		Collections.list(System.getProperties().keys()).stream().sorted().forEach(key -> {
			builder.append(key + ": " + System.getProperty((String) key) + "\n");
		});
		System.out.println(separatorLine + "System Properties:\n" + builder.toString() + separatorLine);
		FileTools.writeStringToFile(new File("system-properties.txt"), builder.toString());

		builder.setLength(0);

		System.getenv().keySet().stream().sorted().forEach(key -> {
			builder.append(key + ": " + System.getenv(key) + "\n");
		});
		System.out.println(separatorLine + "Environment Variable:\n" + builder.toString() + separatorLine);
		FileTools.writeStringToFile(new File("environment-variables.txt"), builder.toString());

		// search for temporary junit files such as
		// - junit7448249630582533939.properties
		// - junitvmwatcher17285009842755567807.properties
		// and create backups using fixed file names. The copies won't be deleted by JUnit.
		for (File file : new File(".").listFiles()) {
			if (file.getName().matches("junit.+\\d+\\.properties")) {
				File copy = new File(file.getName().replaceFirst("\\d+", ""));
				System.out.println("Copying " + file.getPath() + " as " + copy.getPath());
				FileTools.copyFile(file, copy);
			}
		}

		// dummy assertion (just to make sure we run one assertion)
		assertThat(true).isTrue();
	}
}
