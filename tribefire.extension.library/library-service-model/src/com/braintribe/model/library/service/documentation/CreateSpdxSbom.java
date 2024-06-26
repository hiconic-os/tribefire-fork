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
package com.braintribe.model.library.service.documentation;

import java.util.Date;

import com.braintribe.model.accessapi.AccessDataRequest;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.library.service.ArtifactReferences;
import com.braintribe.model.library.service.LibraryBaseRequest;
import com.braintribe.model.service.api.ServiceRequest;

public interface CreateSpdxSbom extends LibraryBaseRequest, AccessDataRequest, ArtifactReferences, HasIgnoreMissing {

	final EntityType<CreateSpdxSbom> T = EntityTypes.T(CreateSpdxSbom.class);

	public final static String title = "title";
	public final static String timestamp = "timestamp";

	String getTitle();
	void setTitle(String title);

	Date getTimestamp();
	void setTimestamp(Date timestamp);

	String getDocumentUri();
	void setDocumentUri(String documentUri);

	@Initializer("'Library Module'")
	String getCreatorTool();
	void setCreatorTool(String creatorTool);

	@Initializer("'Braintribe Technology GmbH'")
	String getCreatorOrganization();
	void setCreatorOrganization(String creatorOrganization);

	String getPackageName();
	void setPackageName(String packageName);

	String getCopyrightText();
	void setCopyrightText(String copyrightText);

	String getPackageComment();
	void setPackageComment(String packageComment);

	String getHomepage();
	void setHomepage(String homepage);

	String getPackageDescription();
	void setPackageDescription(String packageDescription);

	String getPackageOriginator();
	void setPackageOriginator(String packageOriginator);

	String getDownloadLocation();
	void setDownloadLocation(String downloadLocation);

	@Override
	EvalContext<SpdxSbom> eval(Evaluator<ServiceRequest> evaluator);

}
