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
package tribefire.extension.xml.schemed.service;

import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.service.api.ServiceRequest;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchemata;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;

/**
 * a version of the {@link SchemedXmlXsdAnalyzerRequest} for Jinni's command-line tooling 
 * 
 * @author pit
 *
 */
public interface AnalyzeXsd extends AnalyzeXsdRequest {
	
	EntityType<AnalyzeXsd> T = EntityTypes.T(AnalyzeXsd.class);
	
	/** 
	 * @return - the resource that contains the XSD that should be parsed 
	 */
	@Description("the resource that contains the XSD that should be parsed")
	@Alias("s")
	@Mandatory
	Resource getSchema();
	void setSchema( Resource resource);
	
	/**
	 * @return - {@link ReferencedSchemata} that contains all referenced Schemata - if any
	 */
	@Description("all referenced schema - if any")
	@Alias("r")
	ReferencedSchemata getReferencedSchemata();
	void setReferencedSchemata( ReferencedSchemata schemata);
	
	@Override
	EvalContext<? extends AnalyzedXsd> eval(Evaluator<ServiceRequest> evaluator);
	
}
