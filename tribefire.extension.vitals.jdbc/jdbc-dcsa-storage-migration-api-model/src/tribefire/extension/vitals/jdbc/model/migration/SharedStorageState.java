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
package tribefire.extension.vitals.jdbc.model.migration;

import java.util.Date;
import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

/**
 * @author peter.gazdik
 */
public interface SharedStorageState extends GenericEntity {

	EntityType<SharedStorageState> T = EntityTypes.T(SharedStorageState.class);

	String getImplementation();
	void setImplementation(String implementation);

	Date getDate();
	void setDate(Date date);

	SharedStorageStatus getStatus();
	void setStatus(SharedStorageStatus status);

	Resource getDetails();
	void setDetails(Resource details);

	List<AccessMigrationState> getMigratedAccesses();
	void setMigratedAccesses(List<AccessMigrationState> migratedAccesses);

}
