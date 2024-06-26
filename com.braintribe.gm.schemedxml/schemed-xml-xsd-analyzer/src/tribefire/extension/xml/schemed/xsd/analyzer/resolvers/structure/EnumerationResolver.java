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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;

import tribefire.extension.xml.schemed.mapping.metadata.EnumConstantMappingMetaData;
import tribefire.extension.xml.schemed.mapping.metadata.EnumTypeMappingMetaData;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;
import tribefire.extension.xml.schemed.model.xsd.restrictions.Enumeration;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.ContextCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.QPath;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.commons.AnalyzerCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.TypeResolverResponse;

public class EnumerationResolver {

	public static TypeResolverResponse resolve(SchemaMappingContext context, List<Enumeration> enumerations) {
		SimpleType typeToMap = ContextCommons.getNextSimpleType( context);
		String name = typeToMap.getName();
		if (name == null) {  
			name = ContextCommons.getPossibleTypeNameForSimpleType(context);
		}
		//Restriction restriction = ContextCommons.getNextSchemaEntity( context.currentEntityStack, Restriction.T);
		
		name = AnalyzerCommons.assertNonCollidingTypeName( context, name);

		QPath qpath = context.qpathGenerator.generateQPathForSchemaEntity( typeToMap.getDeclaringSchema());
		
		GmEnumType enumType = context.mappingContext.typeMapper.generateGmEnumType( qpath, typeToMap, name);
		
		EnumTypeMappingMetaData enumMetaData = context.mappingContext.metaDataMapper.acquireMetaData(enumType);
		enumMetaData.setXsdName(name);
		Namespace targetNamespace = typeToMap.getDeclaringSchema().getTargetNamespace();
		if (targetNamespace != null) {
			enumMetaData.setNamespace( targetNamespace.getUri());
		}
		
		List<String> enumValues = new ArrayList<String>();
		for (Enumeration enumeration : enumerations) {
			String value = enumeration.getValue();
			enumValues.add(value);
		}
		Map<String,String> mappedValues = context.mappingContext.nameMapper.generateJavaCompatibleEnumValues( enumValues);
		for (Entry<String, String> entry : mappedValues.entrySet()) {
			GmEnumConstant constant = context.mappingContext.typeMapper.generateGmEnumConstant( entry.getKey(), enumType);
			EnumConstantMappingMetaData constantMetaData = context.mappingContext.metaDataMapper.acquireMetaData( constant);
			constantMetaData.setXsdName( entry.getValue());
			
			//context.mappingContext.metaDataMapper.mapEnumConstant(constant, entry.getValue());
		}
		
		TypeResolverResponse response = new TypeResolverResponse();
		response.setGmType( enumType);
		response.setApparentTypeName( name);
		response.setActualTypeName( name);
		return response;
	}

}
