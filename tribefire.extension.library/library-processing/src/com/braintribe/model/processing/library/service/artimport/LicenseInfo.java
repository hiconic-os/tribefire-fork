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
package com.braintribe.model.processing.library.service.artimport;

import com.braintribe.utils.StringTools;

public class LicenseInfo implements Comparable<LicenseInfo>{
	private String artifactId;
	private String licenseName;
	private String licenseUrl;
	private String organization;
	private String packageName;
	private String artifact;
	private String version;
	
	public LicenseInfo(String artifactId, String licenseName, String licenseUrl, String organization) {
		super();
		this.artifactId = artifactId;
		this.licenseName = licenseName;
		this.licenseUrl = licenseUrl;
		this.organization = organization;
		
		this.packageName = StringTools.getSubstringBefore(artifactId, ":");
		this.artifact = StringTools.getSubstringBetween(artifactId, ":", "#");
		this.version = StringTools.getSubstringAfter(artifactId, "#");
	}
	
	public String getArtifactId() {
		return artifactId;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public String getLicenseUrl() {
		return licenseUrl;
	}

	public String getOrganization() {
		return organization;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getArtifact() {
		return artifact;
	}

	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return artifactId+";"+licenseName+";"+licenseUrl+";"+organization;
	}

	@Override
	public int compareTo(LicenseInfo o) {
		return this.artifactId.compareTo(o.artifactId);
	}
		
}
