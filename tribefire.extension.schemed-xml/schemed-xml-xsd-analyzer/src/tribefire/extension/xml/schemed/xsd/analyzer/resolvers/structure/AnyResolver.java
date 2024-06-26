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
package tribefire.extension.xml.schemed.xsd.analyzer.resolvers.structure;

import com.braintribe.model.meta.GmCollectionType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.meta.GmType;

import tribefire.extension.xml.schemed.mapping.metadata.PropertyMappingMetaData;
import tribefire.extension.xml.schemed.model.xsd.Any;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;

public class AnyResolver {

	public static GmProperty resolve(SchemaMappingContext context, Any any, boolean overrideMultiple) {
		context.currentEntityStack.push( any);
		String propertyName = "any";		
		
		GmType anyType = context.mappingContext.typeMapper.acquireGenericEntityType();
		try {		
			int maxOccurs = any.getMaxOccurs();
			boolean multiple = (maxOccurs > 1 || maxOccurs < 0); 
			GmProperty gmProperty;
			
			if  (multiple || overrideMultiple) {
				propertyName = context.mappingContext.nameMapper.generateCollectionName( propertyName);
				gmProperty = context.mappingContext.typeMapper.generateGmProperty( propertyName);
				// 
				GmCollectionType gmCollectionType = context.mappingContext.typeMapper.acquireCollectionType( anyType, false);				
				gmProperty.setType(gmCollectionType);
			}
			else {
				propertyName = context.mappingContext.nameMapper.generateJavaCompatiblePropertyName( propertyName);
				gmProperty = context.mappingContext.typeMapper.generateGmProperty( propertyName);
				gmProperty.setType( anyType);
			}
			// create a mapping for the entity 
			PropertyMappingMetaData propertyMappingMetaData = context.mappingContext.metaDataMapper.acquireMetaData(gmProperty);
			propertyMappingMetaData.setIsMultiple( multiple);
			propertyMappingMetaData.setActualXsdType( "anyType");
			propertyMappingMetaData.setApparentXsdType( "anyType");
			propertyMappingMetaData.setXsdName( "any");
			
			return gmProperty;
		}
		finally {
			context.currentEntityStack.pop();
		}
	}
}
