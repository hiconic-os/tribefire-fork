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
package com.braintribe.utils.template;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import com.braintribe.utils.template.model.MergeContext;
import com.braintribe.utils.template.model.Sequence;
import com.braintribe.utils.template.model.StaticText;
import com.braintribe.utils.template.model.TemplateNode;
import com.braintribe.utils.template.model.Variable;

public class Template {
	private TemplateNode rootNode;
	private boolean containsVariables = true;

	public Template(TemplateNode rootNode) {
		this.rootNode = rootNode;
	}
	public Template(TemplateNode rootNode, boolean containsVariables) {
		this.rootNode = rootNode;
		this.containsVariables = containsVariables;
	}

	public TemplateNode getRootNode() {
		return rootNode;
	}

	public String merge(MergeContext context) throws TemplateException {
		StringBuilder builder = new StringBuilder();
		rootNode.merge(builder, context);
		return builder.toString();
	}

	public String merge(Map<String, Object> variables) {
		return merge(new MergeContext() {
			@Override
			public Object getVariableValue(String variableName) throws TemplateException {
				return variables.get(variableName);
			}
		});
	}

	public MergeContextBuilder merge() {
		return new MergeContextBuilder() {

			private Map<String, Object> variables = new HashMap<>();

			@Override
			public String done() {
				return merge(this.variables);
			}

			@Override
			public MergeContextBuilder add(String key, Object value) {
				this.variables.put(key, value);
				return this;
			}
		};
	}

	public static String merge(String template, MergeContext context) {
		return parse(template).merge(context);
	}

	public static String merge(String template, Map<String, Object> variables) {
		return parse(template).merge(variables);
	}

	public static MergeContextBuilder merge(String template) {
		return parse(template).merge();
	}

	public static Template parse(String template) {
		return parse(template, true);
	}
	
	public static Template parse(String template, boolean consumeEscapes) {
		Sequence sequence = new Sequence();

		int index = 0;
		boolean foundVariable = false;

		while (index < template.length()) {
			int start = findNextPropertyStart(template, index);
			if (start != -1) {
				foundVariable = true;

				StaticText staticText = staticText(template.substring(index, start), consumeEscapes);
				if (staticText.containsEscapes())
					foundVariable = true;
				sequence.add(staticText);
				index = start;
				int end = template.indexOf('}', start);
				if (end != -1) {
					String propertyName = template.substring(start + 2, end);
					sequence.add(new Variable(propertyName));
					index = end + 1;
				} else {
					staticText = staticText(template.substring(index), consumeEscapes);
					if (staticText.containsEscapes())
						foundVariable = true;
					sequence.add(staticText);
					break;
				}
			} else {
				// end here
				String subString = template.substring(index);
				StaticText staticText = staticText(subString, consumeEscapes);
				if (staticText.containsEscapes())
					foundVariable = true;
				
				sequence.add(staticText);
				break;
			}
		}
		

		return new Template(sequence, foundVariable);
	}
	
	private static StaticText staticText(String source, boolean consumeEscapes) {
		if (!consumeEscapes)
			return new StaticText(source);
		
		StringBuilder builder = new StringBuilder();
		
		boolean escapes = false;
		
		int index = 0;
		int len = source.length();
		
		while (index < len) {
			int i = source.indexOf("$${", index);
			if (i == -1) {
				builder.append(source.substring(index, len));
				break;
			}
			else {
				escapes = true;
				builder.append("${");
				index +=3;
			}
		}
		
		return new StaticText(builder.toString(), escapes);
	}
	
	protected static int findNextPropertyStart(String str, int start) {
		int index = start;
		while (index < str.length()) {
			index = str.indexOf('$', index);
			if (index == -1)
				return -1;
			int previousIndex = index - 1;
			int nextIndex = index + 1;
			if ((previousIndex < start || str.charAt(previousIndex) != '$') && nextIndex < str.length() && str.charAt(nextIndex) == '{') {

				return index;
			}
			index++;
		}

		return -1;
	}

	public boolean containsVariables() {
		return containsVariables;
	}

	public interface MergeContextBuilder {
		MergeContextBuilder add(String key, Object value);
		String done();
	}

}
