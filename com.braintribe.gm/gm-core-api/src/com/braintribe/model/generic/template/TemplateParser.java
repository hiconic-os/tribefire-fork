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
package com.braintribe.model.generic.template;

import java.util.LinkedList;
import java.util.List;

class TemplateParser {

	static Template parse(String expression) throws IllegalArgumentException {
		RuleContextImpl ruleContext = new RuleContextImpl(expression);
		return ruleContext.parse();
	}
	
	interface RuleContext {
		void activateRule(Rule rule);
		void activateRuleReplayLastCharacter(Rule rule);
		void appendFragment(String text, boolean placeholder);
		int pos();
	}
	
	interface Rule {
		void accept(char c, RuleContext context);
		void end(RuleContext context);
	}
	
	private static class TextRule implements Rule {
		private final StringBuilder builder = new StringBuilder();
		private boolean escape;

		@Override
		public void accept(char c, RuleContext context) {
			if (escape) {
				escape = false;
				
				switch (c) {
				
				case '$':
					builder.append('$');
					break;
					
				case '{':
					context.activateRule(new BracedVarRule());
					break;
					
				default:
					builder.append('$');
					builder.append(c);
					break;
				}	
			}
			else {
				if (c == '$')
					escape = true;
				else
					builder.append(c);
			}
		}

		@Override
		public void end(RuleContext context) {
			if (escape)
				throw new IllegalArgumentException("unexpected end of text at pos " + context.pos());
			
			if (builder.length() > 0)
				context.appendFragment(builder.toString(), false);
		}
		
	}
	
	private static class BracedVarRule implements Rule {
		private final StringBuilder builder = new StringBuilder();
		private boolean closed;
		
		@Override
		public void accept(char c, RuleContext context) {
			if (c == '}') {
				closed = true;
				context.activateRule(new TextRule());
			}
			else {
				builder.append(c);
			}
		}
		
		@Override
		public void end(RuleContext context) {
			if (!closed || builder.length() == 0)
				throw new IllegalArgumentException("unexpected end of variable at pos " + context.pos());
		
			context.appendFragment(builder.toString(), true);
		}
	}
	
	private static class RuleContextImpl implements RuleContext {
		private Rule rule = new TextRule();
		private int pos;
		private char lastChar;
		private final List<TemplateFragment> elements = new LinkedList<>();
		private final String expression;

		public RuleContextImpl(String expression) {
			this.expression = expression;
		}
		
		public Template parse() {
			for (char res :expression.toCharArray()) {
				lastChar = res;
				pos++;
				this.rule.accept(lastChar, this);
			}

			this.rule.end(this);

			return new Template() {
				@Override
				public String expression() {
					return expression;
				}
				
				@Override
				public List<TemplateFragment> fragments() {
					return elements;
				}
			};
		}
		
		@Override
		public void activateRule(Rule rule) {
			this.rule.end(this);
			this.rule = rule;
		}
		
		@Override
		public void activateRuleReplayLastCharacter(Rule rule) {
			activateRule(rule);
			this.rule.accept(lastChar, this);
		}
		

		@Override
		public void appendFragment(String text, boolean placeholder) {
			elements.add(new TemplateFragmentImpl(placeholder, text));
		}

		@Override
		public int pos() {
			return pos;
		}

	}
	
	private static class TemplateFragmentImpl implements TemplateFragment {
		private final boolean placeholder;
		private final String text;
		
		public TemplateFragmentImpl(boolean placeholder, String text) {
			this.placeholder = placeholder;
			this.text = text;
		}
		
		@Override
		public String getText() {
			return text;
		}
		
		@Override
		public boolean isPlaceholder() {
			return placeholder;
		}
	}
	
}
