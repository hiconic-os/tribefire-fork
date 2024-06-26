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
package com.braintribe.model.processing.query;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.TraversingContext;
import com.braintribe.model.generic.reflection.TraversingVisitor;
import com.braintribe.model.generic.value.Variable;
import com.braintribe.model.query.Query;

/**
 * 
 * 
 * @deprecated use standard template evaluation mechanisms instead
 */
@Deprecated
public class TemplateEvaluation {
	
	private Query query;
	private GenericModelTypeReflection typeReflection;
	private EntityType<Query> queryType;
	

	public TemplateEvaluation(Query query){
		this.query = query;
	}

	public void setTypeReflection(GenericModelTypeReflection typeReflection) {
		this.typeReflection = typeReflection;
	}
	
	public GenericModelTypeReflection getTypeReflection() {
		if(typeReflection == null)
			typeReflection = GMF.getTypeReflection();
		return typeReflection;
	}

	/**
	 * @return the queryType
	 */
	public EntityType<Query> getQueryType() {
		if (queryType == null) {
			queryType = getTypeReflection().getEntityType(query);
		}
		return queryType;
	}
	
	public Map<String, Variable> getVariables(){
		final Map<String, Variable> variables = new HashMap<String, Variable>();
		TraversingVisitor traversingVisitor = new TraversingVisitor() {			
			@Override
			public void visitTraversing(TraversingContext traversingContext) {
				Object candidate = traversingContext.getObjectStack().peek();
				if(candidate instanceof Variable){
					Variable variable = (Variable) candidate;
					variables.put(variable.getName(), variable);					
				}
			}
		};
		getTypeReflection().getEntityType(query).traverse(query, null, traversingVisitor);
		return variables;
	}

	public Query evaluate(Map<String, Object> values) throws TemplateEvaluationException{
		VariableReplacingEvolutionContext evaluationContext = new VariableReplacingEvolutionContext();
		evaluationContext.setValues(values);
		return evaluate(evaluationContext);
	}
	
	public Query evaluate(VariableReplacingEvolutionContext context) throws TemplateEvaluationException {
		return (Query) getQueryType().clone(context, query, null);
	}
	
}
