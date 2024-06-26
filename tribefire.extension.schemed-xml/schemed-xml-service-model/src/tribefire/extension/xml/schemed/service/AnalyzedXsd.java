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

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerResponse;

/**
 * the response for Jinni
 * 
 * @author pit
 *
 */
public interface AnalyzedXsd extends SchemedXmlXsdAnalyzerResponse {
	
	
	EntityType<AnalyzedXsd> T = EntityTypes.T(AnalyzedXsd.class);


	/**
	 * @return - the resource that can deliver the skeleton model (staxed as xml)
	 */
	Resource getSkeletonResource();
	void setSkeletonResource( Resource skeleton);
	
	/**
	 * @return - the resource that can deliver the constraint model (staxed as xml)
	 */
	Resource getConstraintResource();
	void setConstraintResource( Resource constraints);
	
	/**
	 * @return - the resource that can deliver the mapping model (staxed as xml)
	 */
	Resource getMappingResource();
	void setMappingResource( Resource constraints);
	
	/**
	 * @return - the resource that can deliver the artifact (zipped into a ZIP)
	 */
	Resource getArtifact();
	void setArtifact( Resource artifact);
	
	/**
	 * @return - the resource that can deliver the exchange package (staxed as xml)
	 */
	Resource getExchangePackage();
	void setExchangePackage( Resource exchangePackage);
	
	
	
}
