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
package com.braintribe.model.platform.setup.api;

import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.service.api.result.Neutral;

/**
 * 
 * @deprecated Licensing in an Open Source framework has a different meaning. It cannot be the platform but rather some features to be licensed. Sinc
 *             jinni also runs for older TF installations, InstallLicense is anyway needed for the time being. It cannot be removed yet. 
 *
 */
@Deprecated
@Description("Installs a tribefire license given by a license file into the local maven repository as asset 'tribefire.cortex.assets:tribefire-license'")
@PositionalArguments({ "file", "version" })
public interface InstallLicense extends SetupRequest {
	EntityType<InstallLicense> T = EntityTypes.T(InstallLicense.class);

	@Alias("f")
	@Mandatory
	@Description("The license file issued for a tribefire platform")
	Resource getFile();
	void setFile(Resource resource);

	@Alias("v")
	@Mandatory
	@Description("The tribefire version for which the license was issued.")
	String getVersion();
	void setVersion(String version);

	@Override
	EvalContext<Neutral> eval(Evaluator<ServiceRequest> evaluator);
}
