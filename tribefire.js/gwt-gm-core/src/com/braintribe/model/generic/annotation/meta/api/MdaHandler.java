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
package com.braintribe.model.generic.annotation.meta.api;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.annotation.meta.api.analysis.MdaAnalysisContext;
import com.braintribe.model.generic.annotation.meta.api.synthesis.MdaSynthesisContext;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.data.MetaData;

/**
 * Implementation is responsible for converting an annotation of type A into one or more MD.
 * 
 * The interface has therefore two methods, but is structured so that only one of the methods has to be overridden, because the JTA framework only
 * calls on returning a list.
 * 
 * @see RepeatableMdaHandler
 * 
 * @author peter.gazdik
 */
public interface MdaHandler<A extends Annotation, M extends MetaData> {

	Class<A> annotationClass();

	Class<M> metaDataClass();

	default EntityType<M> metaDataType() {
		return GMF.getTypeReflection().getEntityType(metaDataClass());
	}

	// Analyzer

	default List<M> buildMdList(A annotation, MdaAnalysisContext context) {
		return Collections.singletonList(buildMd(annotation, context));
	}

	@SuppressWarnings("unused")
	default M buildMd(A annotation, MdaAnalysisContext context) {
		throw new UnsupportedOperationException("Implementation class must either implement this method or the one that returns a list");
	}

	// Synthesizer

	void buildAnnotation(MdaSynthesisContext context, M metaData);

}
