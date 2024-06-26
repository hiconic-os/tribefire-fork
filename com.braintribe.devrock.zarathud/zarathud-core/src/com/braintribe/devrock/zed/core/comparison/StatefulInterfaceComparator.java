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

import java.util.List;

import com.braintribe.devrock.zed.api.comparison.ComparisonContext;
import com.braintribe.zarathud.model.data.InterfaceEntity;
import com.braintribe.zarathud.model.data.TypeReferenceEntity;
import com.braintribe.zarathud.model.forensics.findings.ComparisonProcessFocus;

/**
 * 
 * @author pit
 */
public class StatefulInterfaceComparator {	
	private ComparisonContext context;
	
	public StatefulInterfaceComparator(ComparisonContext contex) {
		this.context = contex;		
	}

	public void compare( InterfaceEntity base, InterfaceEntity other) {
		
		if (context.isProcessed(base.getName()))
			return;
		
		context.addProcessed( base.getName());
		
		context.pushCurrentEntity(base);
		context.pushCurrentOther(other);
		
		// super interfaces - shallow		
		context.pushCurrentProcessFocus( ComparisonProcessFocus.superInterfaces);
		CommonStatelessComparators.compareTypeReferences(context,  base.getSuperInterfaces(), other.getSuperInterfaces());
		context.popCurrentProcessFocus();
				
		// sub interfaces - shallow
		List<TypeReferenceEntity> cbases = CommonStatelessComparators.wrapInterfaces(base.getSubInterfaces());
		List<TypeReferenceEntity> cothers = CommonStatelessComparators.wrapInterfaces(other.getSubInterfaces());
		
		context.pushCurrentProcessFocus( ComparisonProcessFocus.subInterfaces);
		CommonStatelessComparators.compareTypeReferences( context,  cbases, cothers);
		context.popCurrentProcessFocus();
		
		
		// fields - deep
		context.pushCurrentProcessFocus( ComparisonProcessFocus.fields);
		CommonStatelessComparators.compareFields(context, base.getFields(), other.getFields());
		context.popCurrentProcessFocus();
		
		// methods - deep
		context.pushCurrentProcessFocus( ComparisonProcessFocus.methods);
		CommonStatelessComparators.compareMethods( context, base.getMethods(), other.getMethods());
		context.popCurrentProcessFocus();
	
		// generic entity
		context.pushCurrentProcessFocus( ComparisonProcessFocus.genericity);
		CommonStatelessComparators.compareGenericNature(context, base, other);
		context.popCurrentProcessFocus();
			
		// implementing classes - shallow
		cbases = CommonStatelessComparators.wrapClasses( base.getImplementingClasses());
		cothers = CommonStatelessComparators.wrapClasses( other.getImplementingClasses());
		
		context.pushCurrentProcessFocus( ComparisonProcessFocus.implementingClasses);  
		CommonStatelessComparators.compareTypeReferences( context, cbases, cothers);
		context.popCurrentProcessFocus();
		
		// template parameters
		context.pushCurrentProcessFocus( ComparisonProcessFocus.templateParameters);
		CommonStatelessComparators.compareTemplateParameters( context, base.getTemplateParameters(), other.getTemplateParameters());
		context.popCurrentProcessFocus();
		
		// annotations 
		context.pushCurrentProcessFocus( ComparisonProcessFocus.annotations);
		CommonStatelessComparators.compareAnnotations( context, base.getAnnotations(), other.getAnnotations());
		context.popCurrentProcessFocus();
		
		context.popCurrentEntity();
		context.popCurrentOther();
								
	}
	
}
