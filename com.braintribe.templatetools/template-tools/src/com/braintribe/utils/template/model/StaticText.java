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

import java.util.List;

import com.braintribe.utils.template.TemplateException;

public class StaticText implements TemplateNode {
	private String text;
	private boolean containsEscapes;

	public StaticText(String text) {
		this.text = text;
	}
	
	public StaticText(String text, boolean containsEscapes) {
		this.text = text;
		this.containsEscapes = containsEscapes;
	}
	
	public boolean containsEscapes() {
		return containsEscapes;
	}

	@Override
	public void merge(StringBuilder builder, MergeContext context) throws TemplateException{
		if (context.isSourceMode()) { 
			if (builder.length() > 0) {
				builder.append('+');
			}
			builder.append(context.createSourceLiteral(text));
		}
		else {
			builder.append(text);
		}
	}
	
	@Override
	public void collectVariables(List<Variable> collections) {
		// noop
	}
	
}
