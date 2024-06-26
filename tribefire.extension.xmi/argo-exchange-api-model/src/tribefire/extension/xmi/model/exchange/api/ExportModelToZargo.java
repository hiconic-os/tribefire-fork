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
package tribefire.extension.xmi.model.exchange.api;

import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.PositionalArguments;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.resource.FileResource;
import com.braintribe.model.service.api.ServiceRequest;

@Description("Exports a model given by a model artifact dependency to a zargo file which may already exists. "
		+ "If it exists the export tries to keep as much information as possible (e.g. internal ids, layout to type bindings.")
@PositionalArguments({"model", "zargo"})
public interface ExportModelToZargo extends ArgoExchangeRequest {
	EntityType<ExportModelToZargo> T = EntityTypes.T(ExportModelToZargo.class);
	
	@Description("The zargo target file to which the model should be exported. The file may exist and certain informations such as internal ids will be preserved. If no file is given a file name <artifactId>.zargo in the current working directory will be implied.")
	@Alias("z")
	FileResource getZargo();
	void setZargo(FileResource zargo);

	@Description("The artifact dependency to the model to be exported in the format <groupId>:<artifactId>#<version-expression>.")
	@Mandatory
	@Alias("m")
	String getModel();
	void setModel(String model);
	
	@Override
	EvalContext<GmMetaModel> eval(Evaluator<ServiceRequest> evaluator);
}
