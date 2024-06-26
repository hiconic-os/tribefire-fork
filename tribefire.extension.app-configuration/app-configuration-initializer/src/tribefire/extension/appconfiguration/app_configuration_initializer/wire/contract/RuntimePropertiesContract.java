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
package tribefire.extension.appconfiguration.app_configuration_initializer.wire.contract;

import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	@Default("org.postgresql.Driver")
	String APP_CONFIGURATION_DB_CONNECTION_DRIVER();
	String APP_CONFIGURATION_DB_CONNECTION_URL();
	String APP_CONFIGURATION_DB_CONNECTION_USER();
	@Decrypt
	String APP_CONFIGURATION_DB_CONNECTION_PASSWORD();
	@Default("10")
	Integer APP_CONFIGURATION_DB_POOL_MAX_CONNECTIONS();

	@Default("access.appconfiguration")
	String APP_CONFIGURATION_ACCESS_ID();

	@Default("app-config-admin")
	String APP_CONFIGURATION_ADMIN_ROLE();

	@Default("false")
	Boolean APP_CONFIGURATION_ENABLE_AUDITING();

}
