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
package tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.derivations;

import java.util.Collection;
import java.util.List;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.data.MetaData;

import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.QName;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;
import tribefire.extension.xml.schemed.model.xsd.SimpleTypeRestriction;
import tribefire.extension.xml.schemed.model.xsd.restrictions.Enumeration;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.ContextCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.commons.AnalyzerCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.TypeResolver;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.TypeResolverResponse;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.structure.EnumerationResolver;
import tribefire.extension.xml.schemed.xsd.mapper.metadata.MetaDataExpert;

public class SimpleTypeRestrictionResolver {

	public static TypeResolverResponse analyze(SchemaMappingContext context, SimpleTypeRestriction restriction) {
		context.currentEntityStack.push( restriction);
		try {
		TypeResolverResponse baseResponse = null;
		String base = restriction.getBase();
		Schema declaringSchema = restriction.getDeclaringSchema();
		if (base != null) {
			QName qBase = QNameExpert.parse(base);
			baseResponse = TypeResolver.acquireType(context, declaringSchema, qBase);
		}
		else {
			SimpleType simpleType = restriction.getSimpleType();
			if (simpleType != null) {
				baseResponse = TypeResolver.acquireType(context, simpleType);
			}
		}
		if (baseResponse == null) {
			throw new IllegalStateException("cannot find base type for restriction");
		}
		
		TypeResolverResponse response = null;
		// 
		// enumeration cannot just be handled by metadata, so it needs to be handled here 
		// enumeration is a GmEnumType regardless of the base, and cannot have any other data 
		List<Enumeration> enumerations = restriction.getEnumerations();
		if (!enumerations.isEmpty()) {
			response = EnumerationResolver.resolve( context, enumerations);
			response.setActualTypeName( baseResponse.getActualTypeName());
			response.setApparentTypeName(baseResponse.getApparentTypeName());
		}	
		else {		
			SimpleType typeToMap = ContextCommons.getNextSimpleType( context);
			String name = typeToMap.getName();
			if (name == null) {  
				name = ContextCommons.getPossibleTypeNameForSimpleType(context);
			}
			if (name == null) {
				name = context.mappingContext.nameMapper.generateJavaCompatibleTypeNameForVirtualRestrictionType(baseResponse.getActualTypeName().getLocalPart());
			}
			response  = AnalyzerCommons.buildEntityTypeOutofSimpleType(context, declaringSchema, typeToMap, name, baseResponse.getGmType(), baseResponse.getActualTypeName());
			Collection<MetaData> collectedMetaDataForTypeRestriction = MetaDataExpert.createMetaDataForSimpleTypeRestriction( restriction);
			if (!collectedMetaDataForTypeRestriction.isEmpty()) {
				((GmEntityType) response.getGmType()).getMetaData().addAll( collectedMetaDataForTypeRestriction);
			}
		}		 
		return response;
		}
		finally {
			context.currentEntityStack.pop();
		}
	}

}
