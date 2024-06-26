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
package com.braintribe.devrock.zed.core.comparison;

import com.braintribe.devrock.zed.api.comparison.ComparisonContext;
import com.braintribe.zarathud.model.data.EnumEntity;
import com.braintribe.zarathud.model.forensics.findings.ComparisonProcessFocus;

/**
 * 
 * @author pit
 */
public class StatefulEnumComparator {

	private String baseName;
	private ComparisonContext context;
	
	public StatefulEnumComparator(ComparisonContext context) {
		this.context = context;
	}
	
	public void compare( EnumEntity base, EnumEntity other) {		
		baseName = base.getName();
		
		if (context.isProcessed(baseName))
			return;
		
		context.addProcessed(baseName);
				
		
		// static
		context.pushCurrentProcessFocus(ComparisonProcessFocus.staticModifier);
		CommonStatelessComparators.compareStaticModifiers(context, base, other);
		context.popCurrentProcessFocus();
		
		// abstract
		context.pushCurrentProcessFocus(ComparisonProcessFocus.abstractModifier);
		CommonStatelessComparators.compareAbstractNature(context, base, other);
		context.popCurrentProcessFocus();
		
		// access modifier 
		context.pushCurrentProcessFocus(ComparisonProcessFocus.accessModifier);
		CommonStatelessComparators.compareAccessModifiers(context, base, other);
		context.popCurrentProcessFocus();
		
		// fields	
		context.pushCurrentProcessFocus(ComparisonProcessFocus.fields);
		CommonStatelessComparators.compareFields(context, base.getFields(), other.getFields());
		context.popCurrentProcessFocus();
		
		// methods 
		context.pushCurrentProcessFocus(ComparisonProcessFocus.methods);
		CommonStatelessComparators.compareMethods(context, base.getMethods(), other.getMethods());
		context.popCurrentProcessFocus();
		
		// implemented interfaces
		context.pushCurrentProcessFocus( ComparisonProcessFocus.implementedInterfaces);
		CommonStatelessComparators.compareTypeReferences(context, base.getImplementedInterfaces(), other.getImplementedInterfaces());
		context.popCurrentProcessFocus();
		
		// sub types
		context.pushCurrentProcessFocus( ComparisonProcessFocus.subTypes);
		CommonStatelessComparators.compareTypeReferences(context, CommonStatelessComparators.wrapClasses( base.getSubTypes()), CommonStatelessComparators.wrapClasses( other.getSubTypes()));
		context.popCurrentProcessFocus();
		
		// super type 
		context.pushCurrentProcessFocus( ComparisonProcessFocus.superType);
		CommonStatelessComparators.compareTypeReferenceNullSafe(context, base.getSuperType(), other.getSuperType());
		context.popCurrentProcessFocus();
		
		// template parameters
		context.pushCurrentProcessFocus( ComparisonProcessFocus.templateParameters);
		CommonStatelessComparators.compareTemplateParameters( context, base.getTemplateParameters(), other.getTemplateParameters());
		context.popCurrentProcessFocus();
				
		// values
		context.pushCurrentProcessFocus( ComparisonProcessFocus.enumValues);
		CommonStatelessComparators.compareStringValues( context, base.getValues(), other.getValues());
		context.popCurrentProcessFocus();
		
		
		// template parameters
		context.pushCurrentProcessFocus( ComparisonProcessFocus.templateParameters);
		CommonStatelessComparators.compareTemplateParameters(context, base.getTemplateParameters(), other.getTemplateParameters());
		context.popCurrentProcessFocus();
		
		// annotations 
		context.pushCurrentProcessFocus( ComparisonProcessFocus.annotations);
		CommonStatelessComparators.compareTypeReferences( context, base.getAnnotations(), other.getAnnotations());
		context.popCurrentProcessFocus();
			
	}
}
