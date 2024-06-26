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
package com.braintribe.model.processing.modellergraph.filter.experts;

import java.util.List;

import com.braintribe.model.modellerfilter.JunctionRelationshipFilter;
import com.braintribe.model.modellerfilter.RelationshipFilter;
import com.braintribe.model.processing.modellergraph.filter.FilterStringifier;
import com.braintribe.model.processing.modellergraph.filter.FilterStringifyContext;

public class JunctionRelationshipStringifier<T extends JunctionRelationshipFilter> implements FilterStringifier<T> {
	private String name;
	
	public JunctionRelationshipStringifier(String name) {
		super();
		this.name = name;
	}

	@Override
	public void stringify(FilterStringifyContext context, T relationshipFilter, StringBuilder builder) {
		List<RelationshipFilter> operands = relationshipFilter.getOperands();
		
		if (operands != null && !operands.isEmpty()) {
			for (int i = 0; i < operands.size(); i++) {
				if (i > 0) {
					builder.append(' ');
					builder.append(name);
					builder.append(' ');
				}
				RelationshipFilter operand = operands.get(i);
				if (operand instanceof JunctionRelationshipFilter) {
					builder.append('(');
					context.stringify(operand, builder);
					builder.append(')');
				}
				else
					context.stringify(operand, builder);
			}
		}
		else {
			builder.append("true");
		}
	}

}
