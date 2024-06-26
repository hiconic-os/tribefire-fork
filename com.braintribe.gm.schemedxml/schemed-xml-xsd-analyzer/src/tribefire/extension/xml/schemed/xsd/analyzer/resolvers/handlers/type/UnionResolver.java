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
package tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.data.MetaData;

import tribefire.extension.xml.schemed.mapping.metadata.MappingMetaData;
import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.QName;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;
import tribefire.extension.xml.schemed.model.xsd.Union;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.ContextCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.QPath;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.commons.AnalyzerCommons;

/**
 * a resolver for the {@link Union} type.. <br/>
 * doesn't make sense in the XSD world other to combine restrictions, must be of the same base type 
 * @author pit
 *
 */
public class UnionResolver {
	private static Logger log = Logger.getLogger(UnionResolver.class);
	public static TypeResolverResponse resolve(SchemaMappingContext context, Union union) {
		context.currentEntityStack.push( union);
		try {
			SimpleType currentSimpleType = ContextCommons.getCurrentSimpleType(context);
			QPath qpath = context.qpathGenerator.generateQPathForSchemaEntity( union.getDeclaringSchema());
			String simpleTypeName = currentSimpleType.getName();
			String name = simpleTypeName;
			if (name == null) {  
				name = ContextCommons.getPossibleTypeNameForSimpleType(context);
				name = context.mappingContext.nameMapper.generateJavaCompatibleTypeNameForVirtualType(name);
			}	//name = context.mappingContext.nameMapper.generateJavaCompatibleTypeName(name);
			name = AnalyzerCommons.assertNonCollidingTypeName( context, name);
			QName actualTypeName = QNameExpert.parse( name);
			
			List<QName> simpleTypeReferences = union.getSimpleTypeReferences();
			List<GmType> gmTypes = new ArrayList<>();
			
			for (QName simpleTypeReference : simpleTypeReferences) {
				TypeResolverResponse acquiredTypeResponse = TypeResolver.acquireType(context, union.getDeclaringSchema(), simpleTypeReference);
				gmTypes.add( acquiredTypeResponse.getGmType());				
			}
			// 
			GmEntityType unifiedType = context.mappingContext.typeMapper.generateGmEntityType(qpath, currentSimpleType, name);
			for (GmType gmType : gmTypes) {
				if (gmType instanceof GmEntityType == false ) {
					String msg = "can only process GmEntityType and [" + gmType.getTypeSignature() + "] is not a GmEntityType. Skipped, i.e. not contributing to union";
					log.warn( msg);					
					continue;
				}
				else {
					for (MetaData metadata : ((GmEntityType) gmType).getMetaData()) {
						if (metadata instanceof MappingMetaData == false) {
							unifiedType.getMetaData().add( metadata);
						}
					}
				}
			}
			TypeResolverResponse response = new TypeResolverResponse();
			response.setGmType(unifiedType);
			response.setActualTypeName( actualTypeName);
			response.setApparentTypeName( actualTypeName);
			return response;
		}
		finally {
			context.currentEntityStack.pop();
		}
	}

}
