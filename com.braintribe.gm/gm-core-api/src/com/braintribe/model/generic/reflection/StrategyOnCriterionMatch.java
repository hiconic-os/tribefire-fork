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
package com.braintribe.model.generic.reflection;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.pr.criteria.TraversingCriterion;
import com.braintribe.model.generic.pr.criteria.matching.Matcher;

import jsinterop.annotations.JsType;

/**
 * Cloning parameter that controls what to do in case of a "match" on a property. That is important, for collection
 * elements a match always means we skip the element, i.e. the cloned collection will not contain it. Only for
 * properties we have the following three options.
 * 
 * @see Matcher
 * @see CloningContext#isTraversionContextMatching()
 * @see TraversingCriterion
 * @see GenericModelType#clone(Object, Matcher, StrategyOnCriterionMatch)
 * @see GenericModelType#clone(CloningContext, Object, StrategyOnCriterionMatch)
 */
@JsType(namespace = GmCoreApiInteropNamespaces.reflection)
public enum StrategyOnCriterionMatch {

	/**
	 * Specifies that property of the cloned entity will be set as absent, i.e. an {@link AbsenceInformation} value
	 * descriptor will be set. The actual {@linkplain AbsenceInformation} instance will be taken from
	 * {@link CloningContext#createAbsenceInformation(GenericModelType, GenericEntity, Property)}.
	 */
	partialize,

	/**
	 * Specifies that property value of the original entity will be taken "as is" to the cloned entity, i.e. no cloning
	 * of the property value itself will take place.
	 */
	reference,

	/** Specifies that the matching property will be completely ignored by the cloning algorithm. */
	skip

}
