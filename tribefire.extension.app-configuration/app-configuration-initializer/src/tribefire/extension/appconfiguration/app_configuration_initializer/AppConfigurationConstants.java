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
package tribefire.extension.appconfiguration.app_configuration_initializer;

public final class AppConfigurationConstants {

	private static final String GROUP_ID = "tribefire.extension.app-configuration";

	private static final String MODEL_GLOBAL_ID_PREFIX = "model:";
	private static final String HARDWIRED_GLOBAL_ID_PREFIX = "hardwired:";

	private static final String APP_CONFIGURATION_MODEL_GLOBAL_ID_PREFIX = MODEL_GLOBAL_ID_PREFIX + GROUP_ID + ":";

	public static final String APP_CONFIGURATION_DEPLOYMENT_MODEL_GLOBAL_ID = APP_CONFIGURATION_MODEL_GLOBAL_ID_PREFIX
			+ "app-configuration-deployment-model";
	public static final String APP_CONFIGURATION_API_MODEL_GLOBAL_ID = APP_CONFIGURATION_MODEL_GLOBAL_ID_PREFIX + "app-configuration-api-model";
	public static final String APP_CONFIGURATION_MODEL_GLOBAL_ID = APP_CONFIGURATION_MODEL_GLOBAL_ID_PREFIX + "app-configuration-model";

	public static final String APP_CONFIGURATION_UX_MODULE = "js-ux-module://" + GROUP_ID + ":app-configuration-ux";

	public static final String WORKBENCH_ACCESS_GLOBAL_ID = HARDWIRED_GLOBAL_ID_PREFIX + "access/workbench";

	private AppConfigurationConstants() {
		// no instantiation required
	}

}
