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

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.GmEntityType;

import tribefire.extension.xml.schemed.model.xsd.List;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;
import tribefire.extension.xml.schemed.model.xsd.SimpleTypeRestriction;
import tribefire.extension.xml.schemed.model.xsd.Union;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.ContextCommons;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.derivations.SimpleTypeRestrictionResolver;

public class SimpleTypeResolver {
	private static Logger log = Logger.getLogger(SimpleTypeResolver.class);
	public static TypeResolverResponse resolve( SchemaMappingContext context, SimpleType simpleType) {
		context.currentEntityStack.push( simpleType);		
		
		try {
			SimpleTypeRestriction restriction = simpleType.getRestriction();
			List list = simpleType.getList();
			Union union = simpleType.getUnion();
			
			String name = simpleType.getName();
			if (name == null) {				
				if (name == null) {  
					name = ContextCommons.getPossibleTypeNameForSimpleType(context);
				}
				name = context.mappingContext.nameMapper.generateJavaCompatibleTypeNameForVirtualPropertyType( name);
			}
			else {
				String overridingName = context.mappingContext.nameMapper.getOverridingName(name, null);
				if (overridingName != null) { 
					name = overridingName;
					log.debug("overriding simple type name [" + name + "] by [" + overridingName + "]");
				}
				
			}
			// intercept here for substitutions
			GmEntityType entityType = context.mappingContext.typeMapper.getSubstitutingType(name); 
			if (entityType != null) {
				TypeResolverResponse response = new TypeResolverResponse();
				response.setGmType(entityType);
				response.setAlreadyAcquired(true);
				response.setActualTypeName("n/a");
				response.setApparentTypeName("n/a");
				return response;
			}
 
			
			
			if (restriction != null) {
				TypeResolverResponse response = SimpleTypeRestrictionResolver.analyze( context, (SimpleTypeRestriction) restriction);
				response.setApparentTypeName(name);
				return response; 
				
			}
			else if (list != null) {
				TypeResolverResponse response = ListResolver.resolve( context, list);
				response.setApparentTypeName(name);
				return response;
				
			}
			else if (union != null) {
				TypeResolverResponse response = UnionResolver.resolve( context, union);
				response.setApparentTypeName(name);
				return response;
			}
			else {
				throw new IllegalStateException("no valid content found for SimpleType [" + name + "] in [" + context.print() + "]");
			}
						
		}
		finally {
			context.currentEntityStack.pop();
		}
	}
}
