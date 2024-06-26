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
package com.braintribe.logging.juli.handlers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.UUID;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.Slow;
import com.braintribe.utils.FileTools;

@Category(Slow.class)
public class CronLogRotateTestWithZip {

	protected static File logFileDir;

	@BeforeClass
	public static void beforeClass() throws Exception {
		final String testId = CronLogRotateTestWithZip.class.getSimpleName();

		File configFile = new File("res/" + testId + "/logging.properties");
		System.out.println(configFile.getAbsolutePath());

		try (FileInputStream fis = new FileInputStream(configFile)) {
			LogManager.getLogManager().readConfiguration(fis);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		final String relativeLogFileDirPath = "logs/" + testId;
		logFileDir = new File(relativeLogFileDirPath);
	}

	@AfterClass
	public static void afterClass() throws Exception {
		if (logFileDir.exists()) {
			FileTools.deleteForcedly(logFileDir);
		}
	}

	@Before
	public void before() throws Exception {
		if (logFileDir.exists()) {
			FileTools.deleteForcedly(logFileDir);
		}
		if (!logFileDir.mkdirs()) {
			throw new RuntimeException("Couldn't create dir " + logFileDir + "!");
		}
	}

	@Test
	public void testCronLogRotateWithZip() throws Exception {

		// Log config is configured to rotate every 10 seconds
		// We will write logs for a minute and check whether we 5-6 non-empty files

		LoggingClass1 lc = new LoggingClass1();
		lc.run();

		File[] listFiles = logFileDir.listFiles((FilenameFilter) (dir, name) -> name.startsWith("cronLogRotateTestWithZip") && name.endsWith(".zip"));

		assertThat(listFiles != null);
		assertThat(listFiles.length >= 5);
		assertThat(listFiles.length <= 7);
		for (File f : listFiles) {
			assertThat(f.length() > 0);
		}
	}

	private static class LoggingClass1 {

		private static Logger logger = Logger.getLogger(LoggingClass1.class.getName());

		protected void run() throws Exception {
			long start = System.currentTimeMillis();
			do {
				logger.info("This is a log message for your convenience. Here's an ID: " + UUID.randomUUID().toString());
				Thread.sleep(500L);
			} while ((System.currentTimeMillis() - start) < 60_000L);
		}
	}
}
