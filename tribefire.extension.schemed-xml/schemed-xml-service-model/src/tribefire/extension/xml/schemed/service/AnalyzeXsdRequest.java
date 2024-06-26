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

import java.util.List;

import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.service.api.DomainRequest;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.BidirectionalLink;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.CollectionOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.MappingOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ShallowSubstitutingModel;

@Abstract
public interface AnalyzeXsdRequest extends DomainRequest {
	
	EntityType<AnalyzeXsdRequest> T = EntityTypes.T(AnalyzeXsdRequest.class);	
	
	public static final String DOMAIN_ID = "serviceDomain.schemed-xml";
		
	
	@Initializer("'"+DOMAIN_ID + "'")
	@Override
	String getDomainId();
	
	
	/** 
	 * @return - name of the skeleton model, others are derived 
	 */
	@Description("name of the skeleton model, others are derived")
	@Alias("n")
	String getSkeletonModelName();
	void setSkeletonModelName(String name);
	
	/**	
	 * @return -  name of the top package (imported are attached)
	 */
	@Description("name of the top package (imported are attached)")
	@Alias("p")
	String getTopPackageName();
	void setTopPackageName( String name);
	
	
	/**
	 
	 * @return -  auto-determination {@link CollectionOverride} for multiple entries (maxOccurs > 1) 
	 */
	@Description("auto-determination overrides for multiple entries (maxOccurs > 1)")
	@Alias("oc")
	List<CollectionOverride> getCollectionOverrides();
	void setCollectionOverrides( List<CollectionOverride> overrides);
	
	/**
	 *  
	 * @return overrides - list of auto-determination {@link MappingOverride} for names
	 */
	@Description("auto-determination overrides for names")
	@Alias("om")
	List<MappingOverride> getMappingOverrides();
	void setMappingOverrides( List<MappingOverride> overrides);
	
	
	/**	 
	 * @return - list of {@link BidirectionalLink}
	 */
	@Description("bidirectional links")
	@Alias("bi")
	List<BidirectionalLink> getBidirectionalLinks();
	void setBidirectionalLinks( List<BidirectionalLink> links);
	
	/** 
	 * @return - a list of {@link ShallowSubstitutingModel}
	 */
	@Description("type substitutions" )
	@Alias("ot")
	List<ShallowSubstitutingModel> getShallowSubstitutingModels();
	void setShallowSubstitutingModels( List<ShallowSubstitutingModel> substitutions);
	
	
	/** 
	 * @return - the local output directory
	 */
	@Description("the output directory where the different output should be put into")
	@Alias( "o")
	String getOutputDir();
	void setOutputDir(String outputDir);
	
	/**
	 * @return - true if an exchange package with the 4 models is made
	 */ 
	@Description("set if you want to have an exchange package produced in addition")
	@Alias("e")
	boolean getExchangePackageOutput();
	void setExchangePackageOutput( boolean output);
	
	/**
	 * @return - true if the skeleton model should also be made into an artifact
	 */
	@Description("set if you want to the skeleton produced as a full artifact")
	@Alias("j")
	boolean getJarOutput();
	void setJarOutput( boolean output);
	
}
