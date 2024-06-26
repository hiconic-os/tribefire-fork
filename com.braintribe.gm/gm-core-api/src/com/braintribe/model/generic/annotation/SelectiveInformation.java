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

/**
 * Specifies how a given entity should be displayed as text (in the client). It is similar to
 * {@link ToStringInformation}, but that one is not meant to be displayed for the user, but for example as an entity
 * print in the logs.
 * 
 * This annotation is not perfectly compatible with the SelectiveInformation meta data, because the latter has a
 * property of type LocalizedString. So the value of this annotation is stored as the default value of that
 * LocalizedString, and the same applies in the opposite direction, when we create source code or bytecode based on that
 * meta data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
public @interface SelectiveInformation {
	String value();

	String globalId() default "";
}
