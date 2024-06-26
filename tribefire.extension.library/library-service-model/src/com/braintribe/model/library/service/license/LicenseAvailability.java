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
package com.braintribe.model.library.service.license;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.library.service.LibraryBaseResult;

public interface LicenseAvailability extends LibraryBaseResult {

	final EntityType<LicenseAvailability> T = EntityTypes.T(LicenseAvailability.class);

	public final static String licensesAvailable = "licensesAvailable";
	public final static String licensesMissing = "licensesMissing";

	
	void setLicensesAvailable(List<String> licensesAvailable);
	List<String> getLicensesAvailable();

	void setLicensesMissing(List<String> licensesMissing);
	List<String> getLicensesMissing();

}
