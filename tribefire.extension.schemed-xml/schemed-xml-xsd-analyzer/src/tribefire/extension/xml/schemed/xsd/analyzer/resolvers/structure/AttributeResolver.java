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

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmProperty;
import com.braintribe.model.processing.itw.analysis.JavaTypeAnalysis;

import tribefire.extension.xml.schemed.mapping.metadata.PropertyMappingMetaData;
import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.Attribute;
import tribefire.extension.xml.schemed.model.xsd.QName;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.commons.AnalyzerCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.ResolverCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.TypeResolver;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.TypeResolverResponse;

public class AttributeResolver {
	private static Logger log = Logger.getLogger(AttributeResolver.class);

	public static GmProperty resolve(SchemaMappingContext context, Attribute attribute) {
		Attribute actualAttribute = AnalyzerCommons.retrieveAttribute(context, attribute);
		context.currentEntityStack.push(actualAttribute);
		
		String attributeName = actualAttribute.getName();
		// TODO: check on this
		// basically, it cannot contain a colon, as this implies a prefix. That could only be used in a reference, 
		// but this is here is a simple declaration, probably a definition of 'xml:lang' or so..
		// TODO : MARKER 'XML-CLUDGE'
		if (attributeName.contains(":")) {
			attributeName = attributeName.replace(':', '_');  
		}
		String name = context.mappingContext.nameMapper.generateJavaCompatiblePropertyName( attributeName);		
		String parentTypeName = ResolverCommons.findParentName( context.currentEntityStack);		
		
		
		// intercept here for substitution
		GmEntityType substitionType = context.mappingContext.typeMapper.getSubstitutingType(parentTypeName, name);
		
		Schema declaringSchema = actualAttribute.getDeclaringSchema();
		try {
			
			QName typeReference = actualAttribute.getTypeReference();
			SimpleType type = actualAttribute.getType();
			TypeResolverResponse response;		
			
			// intercept here for substitution
			if (substitionType != null) {
				response = new TypeResolverResponse();
				response.setGmType(substitionType);
				response.setAlreadyAcquired(true);
				response.setActualTypeName("n/a");
				response.setApparentTypeName("n/a");
			}
			else {
				if (typeReference != null) {
					response = TypeResolver.acquireType(context, declaringSchema, typeReference);
				}
				else if (type != null) {
					response = TypeResolver.acquireType(context, type);
				}
				else {
					throw new IllegalStateException("no type found for attribute");
				}
			}
		
			String overridingName = context.mappingContext.nameMapper.getOverridingName( parentTypeName, name);
			if (overridingName != null) {
				name = overridingName;
				log.debug("overriding attribute property [" + parentTypeName + ":" + name + "] by [" + overridingName + "]");
			}
			

			String typeName = context.mappingContext.typeMapper.getMappedNameOfType( response.getGmType());
			boolean isId = false;
			if (typeName != null && typeName.equalsIgnoreCase( "ID")) {
				name = "_" + name;
				isId = true;
			}
			GmProperty gmProperty = context.mappingContext.typeMapper.generateGmProperty( name);
			gmProperty.setType( response.getGmType());
			gmProperty.setGlobalId( JavaTypeAnalysis.propertyGlobalId( response.getGmType().getTypeSignature(), name));
			
			PropertyMappingMetaData propertyMappingMetaData = context.mappingContext.metaDataMapper.acquireMetaData(gmProperty);
			propertyMappingMetaData.setIsAttribute(true);
			propertyMappingMetaData.setActualXsdType( QNameExpert.toString(response.getActualTypeName()));
			propertyMappingMetaData.setApparentXsdType( QNameExpert.toString( response.getApparentTypeName()));
			//TODO: ev use real name with colon? 
			propertyMappingMetaData.setXsdName( attributeName);
			propertyMappingMetaData.setFixedValue( actualAttribute.getFixed());
			propertyMappingMetaData.setDefaultValue( actualAttribute.getDefault());
			if (isId) {
				propertyMappingMetaData.setIsIdProperty(true);
			}
		
			
			
											
			return gmProperty;		
		}
		finally {
			context.currentEntityStack.pop();
		}
		
	}

}
