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
package com.braintribe.wire.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.braintribe.wire.api.context.WireContext;
import com.braintribe.wire.api.scope.DefaultScope;
import com.braintribe.wire.api.scope.WireScope;
import com.braintribe.wire.api.space.BeanSpace;

/**
 * The Beans annotation is to be placed on any real implementation (class and not interface) of {@link BeanSpace}.
 * It will mark the class for wire bytecode enriching.
 * 
 * The annotation also controls the default scope of the {@link BeanSpace} it placed on.
 * @author dirk.scheffler
 * @deprecated use {@link Managed} instead. The annotation will be removed in future versions of Wire
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Deprecated
public @interface Beans {
	/**
	 * The default scope for the whole {@link BeanSpace} the annotation is placed on.
	 * The default of this will be the {@link DefaultScope} which leads to a dynamic resolution
	 * of the actual scope from the {@link WireContext}
	 */
	Class<? extends WireScope> defaultScope() default DefaultScope.class;
}
