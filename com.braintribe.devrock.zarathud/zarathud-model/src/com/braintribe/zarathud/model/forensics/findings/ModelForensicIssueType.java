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

public enum ModelForensicIssueType implements IssueType, EnumBase {
	//
	// model
	//
	MissingGetter, 
	MissingSetter,
	AmbiguousGetter,
	AmbiguousSetter,
	FieldInitialized, // not used anymore
	TypeMismatch,
	TypeMismatchInPropertyHierarchy, // still missing
	WrongSignature, // not used yet
	MissingAnnotation, // not used yet
	InvalidTypes, 
	NonConformMethods,
	ConformMethods,
	MultipleIdProperties, // not used yet
	MissingIdProperty, // not used yet
	ContainmentError, // not used yet 
	CollectionInCollection,
	
	PropertyNameLiteralMissing, 
	PropertyNameLiteralTypeMismatch, 
	PropertyNameLiteralMismatch,
	UnexpectedField,
		
	ContainsNoGenericEntities,
	InvalidEntityTypeDeclaration, // wrong T literal
	MissingEntityTypeDeclaration, // missing T literal
	
	EnumTypeNoEnumbaseDerivation, // a enum type doesn't derive from EnumBase
	EnumTypeNoTypeFunction,
	EnumTypeNoTField,

	// structure
	NonCanonic;
	
	
	public static EnumType T = EnumTypes.T(ModelForensicIssueType.class);

	@Override
	public EnumType type() {
		return T;
	}
	
	
}
