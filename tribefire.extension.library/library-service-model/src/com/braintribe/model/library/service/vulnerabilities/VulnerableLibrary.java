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

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface VulnerableLibrary extends StandardIdentifiable {

	final EntityType<VulnerableLibrary> T = EntityTypes.T(VulnerableLibrary.class);

	public final static String libraryId = "libraryId";
	public final static String name = "name";
	public final static String url = "url";
	public final static String severity = "severity";
	public final static String description = "description";
	public final static String cvssScore = "cvssScore";


	void setLibraryId(String libraryId);
	String getLibraryId();
	
	void setName(String name);
	String getName();

	void setUrl(String url);
	String getUrl();

	void setSeverity(Severity severity);
	Severity getSeverity();
	
	void setDescription(String description);
	String getDescription();
	
	void setCvssScore(float cvssScore);
	float getCvssScore();
	
	
}
