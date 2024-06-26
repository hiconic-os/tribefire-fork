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
package com.braintribe.model.processing.library.service.owasp;

import java.io.File;

import org.owasp.dependencycheck.utils.Settings;

import com.braintribe.logging.Logger;

public class OwaspSettings {

	private final static Logger logger = Logger.getLogger(OwaspSettings.class);

	protected static void clearSettings(Settings settings) {
		if (settings == null) {
			return;
		}
		try {
			settings.cleanup(true);
		} catch (Exception e) {
			logger.info("Could not cleanup settings of vulnerability parser.", e);
		}
	}

	protected static void populateSettings(Settings settings, File dataDirectory, String nvdMirrorBasePath) {

		settings.setString(Settings.KEYS.DATA_DIRECTORY, dataDirectory.getAbsolutePath());
		settings.setBoolean(Settings.KEYS.ANALYZER_ASSEMBLY_ENABLED, false);
		settings.setString(Settings.KEYS.CONNECTION_TIMEOUT, "10000");
		settings.setInt(Settings.KEYS.CVE_START_YEAR, 2020);
		/* settings.setBoolean(Settings.KEYS.ANALYZER_CENTRAL_ENABLED, false); settings.setBoolean(Settings.KEYS.ANALYZER_DEPENDENCY_BUNDLING_ENABLED,
		 * false); settings.setBoolean(Settings.KEYS.ANALYZER_DEPENDENCY_MERGING_ENABLED, false); */
		settings.setBoolean("failOnError", false);

		String pathWithProtocol = nvdMirrorBasePath.startsWith("file://") ? nvdMirrorBasePath : "file://" + nvdMirrorBasePath;

		String baseUrl = pathWithProtocol + "/nvdcve-1.1-%d.json.gz";
		String modifiedUrl = pathWithProtocol + "/nvdcve-1.1-modified.json.gz";

		settings.setString(Settings.KEYS.CVE_BASE_JSON, baseUrl);
		settings.setString(Settings.KEYS.CVE_MODIFIED_JSON, modifiedUrl);

	}
}
