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
package com.braintribe.model.library;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

@SelectiveInformation("${name}")
public interface DistributionLicense extends StandardStringIdentifiable {

	final EntityType<DistributionLicense> T = EntityTypes.T(DistributionLicense.class);

	public final static String name = "name";
	public final static String url = "url";
	public final static String licenseFile = "licenseFile";
	public final static String licenseFilePdf = "licenseFilePdf";
	public final static String commercial = "commercial";
	public final static String internalDocumentationUrl = "internalDocumentationUrl";
	public final static String spdxLicenseId = "spdxLicenseId";
	public final static String spdxListedLicense = "spdxListedLicense";

	String getName();
	void setName(String name);

	String getUrl();
	void setUrl(String url);

	Resource getLicenseFile();
	void setLicenseFile(Resource licenseFile);

	Resource getLicenseFilePdf();
	void setLicenseFilePdf(Resource licenseFilePdf);

	boolean getCommercial();
	void setCommercial(boolean commercial);

	String getInternalDocumentationUrl();
	void setInternalDocumentationUrl(String internalDocumentationUrl);

	String getSpdxLicenseId();
	void setSpdxLicenseId(String spdxLicenseId);

	boolean getSpdxListedLicense();
	void setSpdxListedLicense(boolean spdxListedLicense);
}
