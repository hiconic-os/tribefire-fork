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
package com.braintribe.model.processing.smart.query.planner.context;

import static com.braintribe.model.processing.query.planner.condition.QueryConditionBuilder.disjunction;
import static com.braintribe.model.processing.query.planner.condition.QueryConditionBuilder.valueComparison;
import static com.braintribe.utils.lcd.CollectionTools2.first;
import static com.braintribe.utils.lcd.CollectionTools2.newList;

import java.util.Collection;
import java.util.List;

import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.processing.smart.query.planner.graph.EntitySourceNode;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.ConstantPropertyMapping;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.conditions.Condition;
import com.braintribe.model.query.conditions.ValueComparison;
import com.braintribe.model.query.functions.EntitySignature;
import com.braintribe.model.smartqueryplan.functions.DiscriminatorValue;

class ConstantPropertyTools {

	public static DiscriminatorValue buildDiscriminatorValue(EntitySourceNode sourceNode, String smartProperty) {
		ConstantPropertyMapping cpm = sourceNode.resolveConstantPropertyMapping(smartProperty);

		GmEntityType smartType = sourceNode.getSmartGmType();

		DiscriminatorValue result = DiscriminatorValue.T.createPlain();
		result.setEntityPropertySignature(smartType.getTypeSignature() + "#" + smartProperty);
		result.setSignaturePosition(sourceNode.getDelegateSignaturePosition());
		result.setSignatureToStaticValue(cpm.delegateToValue);

		return result;
	}

	public static Condition buildEntitySignatureCondition(Source source, Collection<String> signatures) {
		if (signatures.size() == 1)
			return toValueComparison(entitySignatureFor(source), first(signatures));

		List<Condition> vcs = toValueComparisons(source, signatures);
		return disjunction(vcs);
	}

	public static List<Condition> toValueComparisons(Source source, Collection<String> signatures) {
		List<Condition> result = newList(signatures.size());

		EntitySignature sourceSignature = entitySignatureFor(source);

		for (String signature: signatures)
			result.add(toValueComparison(sourceSignature, signature));

		return result;
	}

	public static EntitySignature entitySignatureFor(Source source) {
		EntitySignature result = EntitySignature.T.createPlain();
		result.setOperand(source);

		return result;
	}

	public static ValueComparison toValueComparison(EntitySignature sourceSignature, String signature) {
		return valueComparison(sourceSignature, signature, Operator.equal);
	}

}
