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
package tribefire.extension.wopi.initializer.wire.contract;

import com.braintribe.model.accessdeployment.hibernate.HibernateDialect;
import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;
import tribefire.extension.wopi.processing.EntityStorageType;
import tribefire.extension.wopi.processing.StorageType;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	@Default("false")
	boolean WOPI_CREATE_DEFAULT();

	// -----------------------------------------------------------------------
	// WopiTemplateContext
	// -----------------------------------------------------------------------

	@Default("default")
	String WOPI_CONTEXT();

	String WOPI_STORAGE_FOLDER();

	@Default("fs")
	StorageType WOPI_STORAGE_TYPE();

	@Default("smood")
	EntityStorageType WOPI_ENTITY_STORAGE_TYPE();

	// -----------------------------------------------------------------------
	// WopiTemplateDatabaseContext
	// -----------------------------------------------------------------------

	@Default("org.postgresql.Driver")
	String WOPI_DB_DRIVER();

	@Default("jdbc:postgresql://localhost:5432/cortex")
	String WOPI_DB_URL();

	@Default("cortex")
	String WOPI_DB_USER();

	@Decrypt
	@Default("Ck6fxSjtrititl7cOQ7+C75K6LWN+40spCAJeZFkL6K+mYc+le+ERFM4uZ7e4FxLldgBUg==") // cortex
	String WOPI_DB_PASSWORD();

	HibernateDialect WOPI_DB_HIBERNATEDIALECT();

	@Default("WOPI_")
	String WOPI_TABLE_PREFIX();

	@Default("2")
	int WOPI_DB_MIN_POOL_SIZE();
	@Default("50")
	int WOPI_DB_MAX_POOL_SIZE();
}
