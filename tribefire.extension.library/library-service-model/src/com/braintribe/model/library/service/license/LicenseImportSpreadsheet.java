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

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.library.service.LibraryBaseResult;
import com.braintribe.model.resource.Resource;

public interface LicenseImportSpreadsheet extends LibraryBaseResult {

	final EntityType<LicenseImportSpreadsheet> T = EntityTypes.T(LicenseImportSpreadsheet.class);

	public final static String licenseImportSpreadsheet = "licenseImportSpreadsheet";
	
	void setLicenseImportSpreadsheet(Resource licenseImportSpreadsheet);
	Resource getLicenseImportSpreadsheet();
}
