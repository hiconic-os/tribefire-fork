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
package com.braintribe.swagger.model;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.processing.meta.cmd.builders.ModelMdResolver;
import com.braintribe.model.processing.meta.oracle.ModelOracle;
import com.braintribe.model.swaggerapi.SwaggerRequest;

public class ApiCreationContext {

	public ModelOracle oracle;
	public ModelMdResolver mdResolver;
	public IncrementalAccess access;
	public SwaggerRequest request;
	public boolean useFullyQualifiedDefinitionName;

	public ApiCreationContext(SwaggerRequest request, ModelOracle oracle, ModelMdResolver mdResolver, IncrementalAccess access,
			boolean useFullyQualifiedDefinitionName) {
		this.oracle = oracle;
		this.mdResolver = mdResolver;
		this.access = access;
		this.request = request;
		this.useFullyQualifiedDefinitionName = useFullyQualifiedDefinitionName;
	}

}
