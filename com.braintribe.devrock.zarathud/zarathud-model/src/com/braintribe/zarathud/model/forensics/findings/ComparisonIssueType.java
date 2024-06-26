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
package com.braintribe.zarathud.model.forensics.findings;

import com.braintribe.model.generic.base.EnumBase;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.generic.reflection.EnumTypes;

public enum ComparisonIssueType implements IssueType, EnumBase {
	noContentToCompare,				// corresponding entity not found  
	typeMismatch, 					// wrong/differing type
	
	additionalContent,				// new entities in other 
	
	genericityMismatch,
	
	// modifiers
	abstractModifierMismatch, 		// abstract declaration mismatch 
	accessModifierMismatch, 		// public/protected/private/package-private declaration mismatch
	scopeModifierMismatch, 			// final/volatile/default declaration mismatch	
	staticModifierMismatch,			// static declaration mismatch
	synchronizedModifierMismatch,	// synchronized declaration mismatch
	
	
	
	referencedTypeMismatch,			// referenced type differs
	
	// class / enum
	superTypeMismatch,				// wrong differing type as super-type
	missingSubTypes,
	surplusSubTypes,
	
	missingImplementedInterfaces,
	surplusImplementedInterfaces,
	
	missingMethods,
	surplusMethods,
	
	// enum
	missingEnumValues,
	surplusEnumValues,
	
	// interfaces
	missingImplementingClasses,
	surplusImplementingClasses,
	
	missingSuperInterfaces,
	surplusSuperInterfaces,
	
	missingSubInterfaces,
	surplusSubInterfaces,
	
	// method
	missingMethodArguments,
	surplusMethodArguments,
	methodReturnTypeMismatch,
	missingMethodExceptions,
	surplusMethodExceptions,
	methodMismatch,					// method issue ? (split: argumentMismatch, returnTypeMismatch?) 
	
	// fields
	missingFields,
	surplusFields,
	fieldTypeMismatch,					// field issue ?
	
	//
	missingAnnotations,
	surplusAnnotations,
	missingAnnotationContainers,
	surplusAnnotationContainers,
	annotationValueMismatch,
	
	// template parameters
	missingTemplateParameters,
	surplusTemplateParameters,
	
	missingInCollection,			// entry misses (arguments/sub-types/implemented-interfaces)? 
	surplusInCollection,			// entry surplus (arguments/sub-types/implemented-interfaces)?
	;
	
	public static EnumType T = EnumTypes.T(ComparisonIssueType.class);

	
	@Override
	public EnumType type() {
		return T;
	}
	
}
