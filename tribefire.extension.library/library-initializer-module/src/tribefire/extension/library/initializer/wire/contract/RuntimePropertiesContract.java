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

import com.braintribe.model.library.deployment.service.Profile;
import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	String LIBRARY_DB_URL(String defaulValue);

	@Default("org.postgresql.Driver")
	String LIBRARY_DB_DRIVER();
	
	@Default("<user>")
	String LIBRARY_DB_USER();

	@Decrypt
	String LIBRARY_DB_PASSWORD_ENC();

	@Default("doesnotexist")
	String LIBRARY_WKHTMLTOPDF();

	@Default("dev")
	Profile LIBRARY_PROFILE();
	
	String LIBRARY_LOCAL_REPOSITORY_PATH(String defaultValue);

	String LIBRARY_REPOSITORY_USERNAME();

	@Decrypt
	String LIBRARY_REPOSITORY_PASSWORD_ENC();

	@Default("0 0 2 * * ?")
	String LIBRARY_NVD_MIRROR_UPDATE_CRONTAB();
	
	String LIBRARY_NVD_MIRROR_PATH(String defaultValue);
	
	String LIBRARY_REPOSITORY_URL();
	String LIBRARY_RAVENHURST_URL();
}
