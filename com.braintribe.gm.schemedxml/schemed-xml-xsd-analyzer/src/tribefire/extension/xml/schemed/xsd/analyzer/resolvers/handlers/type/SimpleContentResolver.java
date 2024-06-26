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

import tribefire.extension.xml.schemed.model.xsd.Extension;
import tribefire.extension.xml.schemed.model.xsd.SimpleContent;
import tribefire.extension.xml.schemed.model.xsd.SimpleContentRestriction;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.context.SchemaMappingContext;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.derivations.ExtensionResolver;
import tribefire.extension.xml.schemed.xsd.analyzer.resolvers.handlers.type.derivations.SimpleContentRestrictionResolver;

public class SimpleContentResolver {
	public static TypeResolverResponse acquireEntityType(SchemaMappingContext context, SimpleContent content) {
		context.currentEntityStack.push( content);
		try {
			SimpleContentRestriction restriction = content.getRestriction();
			Extension extension = content.getExtension();
			if (restriction != null) {			
				return SimpleContentRestrictionResolver.acquireEntityType(context, restriction);
			}
			else if (extension != null) {
				return ExtensionResolver.acquireEntityType(context, extension);
			}
			else {
				throw new IllegalStateException("no valid content found for simple content in [" + context.print() + "]");
			}	
		}
		finally {
			context.currentEntityStack.pop();
		}
	}
}
