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
package tribefire.extension.wopi.templates.wire.contract;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.accessdeployment.smood.CollaborativeSmoodAccess;
import com.braintribe.model.deployment.database.pool.DatabaseConnectionPool;
import com.braintribe.model.deployment.resource.filesystem.FileSystemBinaryProcessor;
import com.braintribe.model.deployment.resource.sql.SqlBinaryProcessor;
import com.braintribe.model.wopi.connector.WopiWacConnector;
import com.braintribe.model.wopi.service.CleanupWopiSessionWorker;
import com.braintribe.model.wopi.service.ExpireWopiSessionWorker;
import com.braintribe.model.wopi.service.WopiApp;
import com.braintribe.model.wopi.service.WopiIntegrationExample;
import com.braintribe.model.wopi.service.WopiServiceProcessor;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.wopi.templates.api.WopiTemplateContext;

public interface WopiTemplatesContract extends WireSpace {

	/**
	 * Setup WOPI with a specified {@link WopiTemplateContext}
	 */
	void setupWopi(WopiTemplateContext context);

	IncrementalAccess access(WopiTemplateContext context);

	WopiServiceProcessor wopiServiceProcessor(WopiTemplateContext context);

	WopiApp wopiApp(WopiTemplateContext context);

	WopiIntegrationExample wopiIntegrationExample(WopiTemplateContext context);

	WopiWacConnector wopiWacConnector(WopiTemplateContext context);

	CleanupWopiSessionWorker cleanupWopiSessionWorker(WopiTemplateContext context);

	ExpireWopiSessionWorker expireWopiSessionWorker(WopiTemplateContext context);

	IncrementalAccess storageAccess(WopiTemplateContext context);

	DatabaseConnectionPool connectionPool(WopiTemplateContext context);

	SqlBinaryProcessor sqlBinaryProcessor(WopiTemplateContext context);

	FileSystemBinaryProcessor filesystemBinaryProcessor(WopiTemplateContext context);

	CollaborativeSmoodAccess wopiWorkbenchAccess(WopiTemplateContext context);

}
