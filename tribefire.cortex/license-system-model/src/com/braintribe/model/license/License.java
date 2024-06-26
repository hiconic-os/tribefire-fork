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
package com.braintribe.model.license;

import java.util.Date;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

  
public interface License extends StandardStringIdentifiable {

	final EntityType<License> T = EntityTypes.T(License.class);

	public static final String systemLicenseId = "systemLicenseId";
	public static final String uploadDate = "uploadDate";
	public static final String uploader = "uploader";
	public static final String expiryDate = "expiryDate";
	public static final String licensee = "licensee";
	public static final String licensor = "licensor";
	public static final String licenseResource = "licenseResource";
	public static final String active = "active";
	public static final String issueDate = "issueDate";
	public static final String licenseeAccount = "licenseeAccount";
	
	void setSystemLicenseId(String systemLicenseId);
	String getSystemLicenseId();
	
	void setUploadDate(Date uploadDate);
	Date getUploadDate();
	
	void setUploader(String uploader);
	String getUploader();
	
	void setExpiryDate(Date expiryDate);
	Date getExpiryDate();
	
	void setLicensee(String licensee);
	String getLicensee();
	
	void setLicensor(String licensor);
	String getLicensor();
	
	void setLicenseResource(Resource licenseResource);
	Resource getLicenseResource();
	
	void setActive(boolean active);
	boolean getActive();
	
	void setIssueDate(Date issueDate);
	Date getIssueDate();

	void setLicenseeAccount(String licenseeAccount);
	String getLicenseeAccount();

}
