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
package com.braintribe.utils.template.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.braintribe.utils.template.TemplateException;

public class Sequence implements TemplateNode {
	private List<TemplateNode> nodes = new ArrayList<TemplateNode>();
	
	public void add(TemplateNode templateNode) {
		nodes.add(templateNode);
	}
	
	@Override
	public void merge(StringBuilder builder, MergeContext context)
			throws TemplateException {
		for (TemplateNode node: nodes) {
			node.merge(builder, context);
		}
	}
	
	@Override
	public void collectVariables(List<Variable> collections) {
		for (TemplateNode node: nodes) {
			node.collectVariables(collections);
		}
	}
	
	@Override
	public boolean walk(Predicate<TemplateNode> visitor) {
		if (!visitor.test(this))
			return false;
		
		for (TemplateNode node: nodes) {
			if (!node.walk(visitor))
				return false;
		}
		return true;
	}
}
