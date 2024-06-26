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
package com.braintribe.common.lcd.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that {@link NonNull} is the default null annotation, which means that all parameters/return
 * values/variables must not be <code>null</code> unless {@link Nullable} is set. Note that this annotation can be
 * {@link #value() disabled}. This is especially useful when overriding methods that are not null-annotated.<br/>
 * This annotation is compatible with the annotation-based null analysis of Eclipse.
 *
 * @author michael.lafite
 */
@Retention(RetentionPolicy.CLASS)
@Target({ METHOD, CONSTRUCTOR, TYPE, PACKAGE })
@Documented
public @interface NonNullByDefault {
	/**
	 * Whether the annotation is enabled or not.
	 */
	boolean value() default true;
}
