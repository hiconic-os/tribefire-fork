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
package com.braintribe.swagger;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.service.api.ServiceRequest;

@Abstract
public interface ExportSwaggerApi extends ExportSwaggerModelRequest {

	EntityType<ExportSwaggerApi> T = EntityTypes.T(ExportSwaggerApi.class);

	String getResource();
	void setResource(String resource);

	String getBasePath();
	void setBasePath(String basePath);

	@Mandatory
	@Initializer("'2.0'")
	String getVersion();
	void setVersion(String version);

	GmMetaModel getModel();
	void setModel(GmMetaModel model);

	@Initializer("true")
	@Override
	boolean getUseFullyQualifiedDefinitionName();
	void setUseFullyQualifiedDefinitionName(boolean useFullyQualifiedDefinitionName);

	@Override
	EvalContext<ExportSwaggerModelResponse> eval(Evaluator<ServiceRequest> evaluator);
}
