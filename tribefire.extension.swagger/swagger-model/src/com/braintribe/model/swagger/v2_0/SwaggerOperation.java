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
package com.braintribe.model.swagger.v2_0;

import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * See https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md#operationObject
 */
public interface SwaggerOperation extends GenericEntity {

	final EntityType<SwaggerOperation> T = EntityTypes.T(SwaggerOperation.class);

	String getSummary();
	void setSummary(String summary);

	String getDescription();
	void setDescription(String description);

	/**
	 * The key here is either a code (200, 500, ...) or "default" (which is why it's a string and not an it).
	 */
	@Mandatory
	Map<String, SwaggerResponse> getResponses();
	void setResponses(Map<String, SwaggerResponse> responses);

	List<String> getTags();
	void setTags(List<String> tags);

	List<SwaggerParameter> getParameters();
	void setParameters(List<SwaggerParameter> parameters);

	/**
	 * @return the mimeTypes that this API consumes
	 */
	List<String> getConsumes();
	void setConsumes(List<String> consumes);

	// /**
	// * @return the mimeTypes that this API produces
	// */
	// List<String> getProduces();
	// void setProduces(List<String> produces);
}
