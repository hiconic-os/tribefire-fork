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
package tribefire.extension.metrics.model.deployment.service.aspect;

import java.util.Map;

import com.braintribe.model.descriptive.HasName;
import com.braintribe.model.extensiondeployment.ServiceAroundProcessor;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.metrics.model.deployment.service.HasMetricsConnectors;

/**
 * Base for metric aspect - around DDSA services
 * 
 *
 */
@Abstract
public interface MetricsAspect extends ServiceAroundProcessor, HasMetricsConnectors, HasName {

	final EntityType<MetricsAspect> T = EntityTypes.T(MetricsAspect.class);

	String description = "description";
	String tagsSuccess = "tagsSuccess";
	String tagsError = "tagsError";
	String addDomainIdTag = "addDomainIdTag";
	String addPartitionTag = "addPartitionTag";
	String addTypeSignatureTag = "addTypeSignatureTag";
	String addRequiresAuthenticationTag = "addRequiresAuthenticationTag";

	String getDescription();
	void setDescription(String description);

	Map<String, String> getTagsSuccess();
	void setTagsSuccess(Map<String, String> tagsSuccess);

	Map<String, String> getTagsError();
	void setTagsError(Map<String, String> tagsError);

	@Initializer("false")
	boolean getAddDomainIdTag();
	void setAddDomainIdTag(boolean addDomainIdTag);

	@Initializer("false")
	boolean getAddPartitionTag();
	void setAddPartitionTag(boolean addPartitionTag);

	@Initializer("true")
	boolean getAddTypeSignatureTag();
	void setAddTypeSignatureTag(boolean addTypeSignatureTag);

	@Initializer("false")
	boolean getAddRequiresAuthenticationTag();
	void setAddRequiresAuthenticationTag(boolean addRequiresAuthenticationTag);

}
