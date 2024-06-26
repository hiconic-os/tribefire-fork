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

import com.braintribe.model.generic.reflection.PropertyAccessInterceptor;

/**
 * Specifies that a given getter/setter pair does not represent a real property, but just a holder for some value only
 * accessible by an expressive code. This means this property is not reflected in the GM reflection, even though the
 * getter and setter code is created at runtime. This code, however, does not use the
 * {@link PropertyAccessInterceptor}s, but only stores the value directly in a field.
 * 
 * NOTE: This is currently not supported, but will be in the future.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface Transient {
	// empty
}
