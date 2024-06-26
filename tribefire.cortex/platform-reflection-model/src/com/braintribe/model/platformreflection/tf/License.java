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
package com.braintribe.model.platformreflection.tf;

import java.util.Date;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.platformreflection.request.PlatformReflectionResponse;

public interface License extends PlatformReflectionResponse {

	EntityType<License> T = EntityTypes.T(License.class);

	void setLicensee(String licensee);
	String getLicensee();

	void setLicenseeAccount(String licenseeAccount);
	String getLicenseeAccount();

	void setExpiryDate(Date expiryDate);
	Date getExpiryDate();

	void setLicensor(String licensor);
	String getLicensor();

	void setActive(boolean active);
	boolean getActive();

	void setIssueDate(Date issueDate);
	Date getIssueDate();

	void setLicenseResourceId(String licenseResourceId);
	String getLicenseResourceId();

	void setUploadDate(Date uploadDate);
	Date getUploadDate();

	void setUploader(String uploader);
	String getUploader();

}
