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
package com.braintribe.model.generic.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.braintribe.model.generic.base.EntityBase;

/**
 * Template for the implementation of the {@link Object#toString() toString} method of an entity type.
 * 
 * This template can also use placeholders for properties and a few special variables:
 * <ul>
 * <li>#type: type name (signature)</li>
 * <li>_type: type name(same as #type)</li>
 * <li>#type_short: simple type name</li>
 * <li>#runtimeId: {@link EntityBase#runtimeId() runtimeId}</li>
 * <li>#id: id if not null, otherwise {@link EntityBase#runtimeId() runtimeId} prefixed with '~'</li>
 * </ul>
 * 
 * A property/variable can be specified like this "${#type_short}" and escaped with doubling the "$" sign, like this "$${#type_short}".
 * <p>
 *
 * Example:<br>
 * Template: "${#type_short} - ${name}" could result in values like "Person - John" or "Item - Hammer"
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface ToStringInformation {
	public String value();
}
