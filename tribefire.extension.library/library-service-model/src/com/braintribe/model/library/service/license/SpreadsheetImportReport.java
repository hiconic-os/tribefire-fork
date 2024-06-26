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

public interface SpreadsheetImportReport extends LibraryBaseResult {

	final EntityType<SpreadsheetImportReport> T = EntityTypes.T(SpreadsheetImportReport.class);

	public final static String librariesImported = "librariesImported";
	public final static String librariesNotImported = "librariesNotImported";
	public final static String librariesAlreadyImported = "librariesAlreadyImported";
	public final static String licensesFound = "licensesFound";
	public final static String licensesMissing = "licensesMissing";

	void setLibrariesImported(List<String> librariesImported);
	List<String> getLibrariesImported();

	void setLibrariesNotImported(List<String> librariesNotImported);
	List<String> getLibrariesNotImported();

	void setLibrariesAlreadyImported(List<String> librariesAlreadyImported);
	List<String> getLibrariesAlreadyImported();

	void setLicensesFound(List<String> licensesFound);
	List<String> getLicensesFound();

	void setLicensesMissing(List<String> licensesMissing);
	List<String> getLicensesMissing();

}
