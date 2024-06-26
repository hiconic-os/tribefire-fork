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
package com.braintribe.model.generic.annotation.meta.api.synthesis;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;

import com.braintribe.model.generic.annotation.meta.CompoundUnique;
import com.braintribe.model.generic.annotation.meta.CompoundUniques;

/**
 * 
 */
public interface MdaSynthesisContext {

	/**
	 * Creates a new {@link SingleAnnotationDescriptor} and makes sure the inner globalId is set properly.
	 */
	SingleAnnotationDescriptor newDescriptor(Class<? extends Annotation> annotationClass);

	SingleAnnotationDescriptor newDescriptor(Class<? extends Annotation> annotationClass, boolean setAsCurrentDescriptor);

	/**
	 * @return {@link AnnotationDescriptor} associated with the type of currently processed meta-data. Usually this returns null, because we don't
	 *         expect multiple MetaData of the same type, but in some cases this is possible when the annotation is handled as {@link Repeatable},
	 *         like {@link CompoundUnique} / {@link CompoundUniques}.
	 */
	AnnotationDescriptor getCurrentDescriptor();

	/**
	 * associates given {@link AnnotationDescriptor} with the type of currently processed meta-data
	 * 
	 * @see #getCurrentDescriptor()
	 */
	void setCurrentDescriptor(AnnotationDescriptor descriptor);

	/**
	 * Associates given {@link AnnotationDescriptor} with the type of currently processed meta-data. In case there already is a descriptor associated,
	 * it creates a new or extends existing {@link RepeatedAnnotationDescriptor} which corresponds to given {@link SingleAnnotationDescriptor}.
	 * 
	 * @see #getCurrentDescriptor()
	 */
	void setCurrentDescriptorMulti(SingleAnnotationDescriptor descriptor, Class<? extends Annotation> repeatabeAnnoClass);

}
