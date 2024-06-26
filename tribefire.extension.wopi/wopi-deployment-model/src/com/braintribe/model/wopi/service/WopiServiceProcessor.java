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
package com.braintribe.model.wopi.service;

import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_APP_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_APP_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SERVICE_PROCESSOR_LOG_ERROR_THRESHOLD_IN_MS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SERVICE_PROCESSOR_LOG_ERROR_THRESHOLD_IN_MS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SERVICE_PROCESSOR_LOG_WARNING_THRESHOLD_IN_MS_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SERVICE_PROCESSOR_LOG_WARNING_THRESHOLD_IN_MS_NAME;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SERVICE_PROCESSOR_TEST_DOC_COMMAND_DESCRIPTION;
import static tribefire.extension.wopi.model.WopiMetaDataConstants.WOPI_SERVICE_PROCESSOR_TEST_DOC_COMMAND_NAME;

import com.braintribe.model.extensiondeployment.access.AccessRequestProcessor;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Max;
import com.braintribe.model.generic.annotation.meta.Min;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 *
 */
public interface WopiServiceProcessor extends AccessRequestProcessor {

	final EntityType<WopiServiceProcessor> T = EntityTypes.T(WopiServiceProcessor.class);

	String wopiApp = "wopiApp";
	String logWarningThresholdInMs = "logWarningThresholdInMs";
	String logErrorThresholdInMs = "logErrorThresholdInMs";
	String testDocCommand = "testDocCommand";

	@Name(WOPI_APP_NAME)
	@Description(WOPI_APP_DESCRIPTION)
	@Mandatory
	WopiApp getWopiApp();
	void setWopiApp(WopiApp wopiApp);

	@Name(WOPI_SERVICE_PROCESSOR_LOG_WARNING_THRESHOLD_IN_MS_NAME)
	@Description(WOPI_SERVICE_PROCESSOR_LOG_WARNING_THRESHOLD_IN_MS_DESCRIPTION)
	@Mandatory
	@Initializer("5000l") // 5s
	@Min("1l") // 1ms
	@Max("600000l") // 10min
	long getLogWarningThresholdInMs();
	void setLogWarningThresholdInMs(long logWarningThresholdInMs);

	@Name(WOPI_SERVICE_PROCESSOR_LOG_ERROR_THRESHOLD_IN_MS_NAME)
	@Description(WOPI_SERVICE_PROCESSOR_LOG_ERROR_THRESHOLD_IN_MS_DESCRIPTION)
	@Mandatory
	@Initializer("10000l") // 10s
	@Min("1l") // 1ms
	@Max("600000l") // 10min
	long getLogErrorThresholdInMs();
	void setLogErrorThresholdInMs(long logErrorThresholdInMs);

	@Name(WOPI_SERVICE_PROCESSOR_TEST_DOC_COMMAND_NAME)
	@Description(WOPI_SERVICE_PROCESSOR_TEST_DOC_COMMAND_DESCRIPTION)
	@Mandatory
	@Initializer("'/usr/local/bin/docker run --rm tylerbutler/wopi-validator -- -w %s -t %s -l %d %s'")
	String getTestDocCommand();
	void setTestDocCommand(String testDocCommand);

}
