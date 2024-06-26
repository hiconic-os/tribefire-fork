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
package com.braintribe.model.generic.pr.criteria;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.annotation.GmSystemInterface;

import jsinterop.annotations.JsType;

/**
 * 
 */
@GmSystemInterface
@JsType (namespace = GmCoreApiInteropNamespaces.tc)
public enum CriterionType {
	BASIC, 
	MAP, 
	ROOT, 
	ENTITY, 
	PROPERTY, 
	LIST_ELEMENT, 
	SET_ELEMENT, 
	MAP_ENTRY, 
	MAP_KEY, 
	MAP_VALUE, 
	CONJUNCTION, 
	DISJUNCTION, 
	NEGATION,
	JOKER, 
	VALUE_CONDITION, 
	TYPE_CONDITION, 
	RECURSION, 
	PROPERTY_TYPE, 
	PATTERN,
	ACL,
	PLACEHOLDER,
}
