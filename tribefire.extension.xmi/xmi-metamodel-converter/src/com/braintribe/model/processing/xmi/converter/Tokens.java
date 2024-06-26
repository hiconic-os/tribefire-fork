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
package com.braintribe.model.processing.xmi.converter;

/**
 * @author pit
 *
 */
public class Tokens {
	public static final String TOKEN_STEREOTYPE_IDPROPERTY = "IdProperty";
	public static final String TOKEN_STEREOTYPE_ROOT_TYPE = "RootType";
	public static final String TOKEN_STEREOTYPE_PLAIN = "Plain";
	public static final String TOKEN_STEREOTYPE_DISCARD = "Discard";
	public static final String TOKEN_STEREOTYPE_NONNULLABLE = "NonNullable";

	public static final String TOKEN_SUPPORTEDVERSION = "1.2";

	public static final String TOKEN_GE_TYPESIGNATURE = "com.braintribe.model.generic.GenericEntity";

	public static final String TOKEN_PATH_MODEL = "XMI.content/.*Model";

	public static final String TOKEN_TAG_ARTIFACTBINDING = "DeclaringModel";
	public static final String TOKEN_TAG_MODEL_DEPENDENCIES = "ModelDependencies";
	
	public static final String TOKEN_TAG_DOCUMENTATION_SEE = "see";
	public static final String TOKEN_TAG_DOCUMENTATION_SINCE = "since";
	public static final String TOKEN_TAG_DOCUMENTATION = "documentation";
	public static final String TOKEN_TAG_DEPRECATED = "deprecated";
	

	public static final String TOKEN_TAG_GENERALIZATION_PRIORITY = "GeneralizationPriority";
	public static final String TOKEN_TAG_GLOBALID = "GlobalId";

}
