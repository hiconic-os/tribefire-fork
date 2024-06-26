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

import java.util.Map;

import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.eval.api.RuntimeQueryEvaluationException;
import com.braintribe.model.processing.smart.query.planner.graph.EntitySourceNode;
import com.braintribe.model.processing.smart.query.planner.graph.QueryPlanStructure;
import com.braintribe.model.processing.smart.query.planner.graph.SimpleValueNode;
import com.braintribe.model.processing.smart.query.planner.graph.SourceNode;
import com.braintribe.model.processing.smartquery.eval.api.ConversionDirection;
import com.braintribe.model.processing.smartquery.eval.api.SmartConversionExpert;
import com.braintribe.model.query.Operand;
import com.braintribe.model.query.PropertyOperand;

/**
 * 
 */
public class SmartConversionHandler {

	private final SmartQueryPlannerContext context;
	private final QueryPlanStructure planStructure;

	private final Map<EntityType<? extends SmartConversion>, SmartConversionExpert<?>> conversionExperts;

	public SmartConversionHandler(SmartQueryPlannerContext smartQueryPlannerContext,
			Map<EntityType<? extends SmartConversion>, SmartConversionExpert<?>> conversionExperts) {

		this.context = smartQueryPlannerContext;
		this.planStructure = context.planStructure();

		this.conversionExperts = conversionExperts;
	}

	public SmartConversion findConversion(Object operand) {
		if (!(operand instanceof Operand) || context.isEvaluationExclude(operand))
			return null;

		if (operand instanceof PropertyOperand) {
			PropertyOperand po = (PropertyOperand) operand;
			String propertyName = po.getPropertyName();

			if (propertyName == null) {
				SourceNode sourceNode = planStructure.getSourceNode(po.getSource());

				// This means our PropertyOpearand represents a joined simple-value collection
				if (sourceNode instanceof SimpleValueNode)
					return ((SimpleValueNode) sourceNode).findSmartConversion();
				else
					return null;
			}

			EntitySourceNode sourceNode = planStructure.getSourceNode(po.getSource());

			return sourceNode.findSmartPropertyConversion(propertyName);
		}

		return null;
	}

	public Object convertToDelegateValue(Object smartValue, SmartConversion conversion) {
		return getExpertFor(conversion).convertValue(conversion, smartValue, ConversionDirection.smart2Delegate);
	}

	private SmartConversionExpert<SmartConversion> getExpertFor(SmartConversion conversion) {
		EntityType<?> conversionType = conversion.entityType();
		SmartConversionExpert<SmartConversion> result = (SmartConversionExpert<SmartConversion>) conversionExperts.get(conversionType);

		if (result == null)
			throw new RuntimeQueryEvaluationException("No expert found for conversion:" + conversionType.getTypeSignature());

		return result;
	}

}
