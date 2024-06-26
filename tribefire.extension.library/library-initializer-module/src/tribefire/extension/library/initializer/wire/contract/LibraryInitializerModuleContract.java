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
package tribefire.extension.library.initializer.wire.contract;

import java.util.Set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.deployment.database.pool.HikariCpConnectionPool;
import com.braintribe.model.deployment.resource.sql.SqlBinaryProcessor;
import com.braintribe.model.extensiondeployment.meta.BinaryProcessWith;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.library.deployment.service.LibraryService;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.job_scheduling.deployment.model.JobScheduling;

public interface LibraryInitializerModuleContract extends WireSpace {

	HikariCpConnectionPool dbConnector();

	SqlBinaryProcessor sqlBinaryProcessor();

	IncrementalAccess libraryAccess();

	LibraryService libraryService();

	BinaryProcessWith binaryProcessWith();
	ProcessWith processWith();

	Boolean isDbAccess();

	void metaData();

	JobScheduling updateNvdMirrorScheduledJob();

	Set<DdraMapping> ddraMappings();

}
