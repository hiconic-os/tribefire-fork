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

import tribefire.extension.xml.schemed.model.xsd.ComplexContent;
import tribefire.extension.xml.schemed.model.xsd.ComplexContentRestriction;
import tribefire.extension.xml.schemed.model.xsd.Extension;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.derivations.ComplexTypeRestrictionResolver;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.derivations.ExtensionResolver;

public class ComplexContentResolver {

	public static TypeResolverResponse acquireEntityType(SchemaMappingContext context, ComplexContent complexContent) {

		context.currentEntityStack.push( complexContent);
		try {
			ComplexContentRestriction restriction = complexContent.getRestriction();
			Extension extension = complexContent.getExtension();
			
			if (restriction != null ) {
				return ComplexTypeRestrictionResolver.acquireEntityType(context, restriction);
			}
			else if (extension != null) {
				return ExtensionResolver.acquireEntityType( context, extension);
			}
			else {
				throw new IllegalStateException( "no valid content found for complexContent in [" + context.print() +"]");
			}	
		}
		finally {
			context.currentEntityStack.pop();
		}
		
	}

}
