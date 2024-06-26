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
package com.braintribe.model.library.service.vulnerabilities;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.library.service.LibraryBaseResult;
import com.braintribe.model.resource.Resource;

public interface Vulnerabilities extends LibraryBaseResult {

	final EntityType<Vulnerabilities> T = EntityTypes.T(Vulnerabilities.class);

	public final static String cleanLibraries = "cleanLibraries";
	public final static String missingLibraries = "missingLibraries";
	public final static String librariesNotChecked = "librariesNotChecked";
	public final static String vulnerableLibraries = "vulnerableLibraries";
	public final static String report = "report";

	
	void setCleanLibraries(List<String> cleanLibraries);
	List<String> getCleanLibraries();

	void setMissingLibraries(List<String> missingLibraries);
	List<String> getMissingLibraries();

	void setLibrariesNotChecked(List<String> librariesNotChecked);
	List<String> getLibrariesNotChecked();

	void setVulnerableLibraries(List<VulnerableLibrary> vulnerableLibraries);
	List<VulnerableLibrary> getVulnerableLibraries();
	
	void setReport(Resource report);
	Resource getReport();


}
