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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.ValueDescriptor;

/**
 * This annotation says that given entity does not belong to the model corresponding to the artifact it is declared in, but to another one
 * specified by the {@link #value()}.
 * 
 * The use-case that lead to the introduction was that {@link GenericEntity} has a dependency on e.g. {@link ValueDescriptor}, so they have
 * to be in the same artifact, but we still want it to be part of the ValueDescriptorModel. So the entity is "physically" present in the
 * RootModel, but logically part of the ValueDescriptorModel.
 * 
 * Name is taken from <a href="https://en.wikipedia.org/wiki/Forward_declaration">this</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForwardDeclaration {
	/**
	 * Name of the model (groupId:artifactId) to which this type should belongs.
	 */
	String value();
}
