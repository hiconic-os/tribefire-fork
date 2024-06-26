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
package com.braintribe.model.library.deployment.service;

import com.braintribe.model.extensiondeployment.access.AccessRequestProcessor;
import com.braintribe.model.generic.annotation.meta.Confidential;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface LibraryService extends AccessRequestProcessor {

	final EntityType<LibraryService> T = EntityTypes.T(LibraryService.class);

	void setProfile(Profile profile);
	Profile getProfile();

	void setWkHtmlToPdf(WkHtmlToPdf wkHtmlToPdf);
	WkHtmlToPdf getWkHtmlToPdf();

	void setRepositoryBasePath(String repositoryBasePath);
	String getRepositoryBasePath();

	void setRepositoryUsername(String repositoryUsername);
	String getRepositoryUsername();

	void setRepositoryPassword(String repositoryPassword);
	@Confidential
	String getRepositoryPassword();

	void setRepositoryUrl(String url);
	String getRepositoryUrl();

	void setRavenhurstUrl(String library_RAVENHURST_URL);
	String getRavenhurstUrl();

	void setNvdMirrorBasePath(String nvdMirrorBasePath);
	String getNvdMirrorBasePath();

}
