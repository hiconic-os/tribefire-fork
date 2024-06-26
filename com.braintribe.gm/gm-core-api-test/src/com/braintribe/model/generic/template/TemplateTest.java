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

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.braintribe.common.lcd.Pair;

public class TemplateTest {
	@Test
	public void testInvalidExpressions() throws Exception {
		testInvalidExpression("foo ${x");
		testInvalidExpression("foo ${");
		testInvalidExpression("foo ${}");
	}
	
	@Test
	public void testValidExpressions() throws Exception {
		testValidExpression("", true);
		testValidExpression("test", true, Pair.of("test", false));
		testValidExpression("Hello ${name}!", false, Pair.of("Hello ", false), Pair.of("name", true), Pair.of("!", false));
		testValidExpression("${name}", false, Pair.of("name", true));
		testValidExpression("$${name}", true, Pair.of("${name}", false));
		testValidExpression("$name", true, Pair.of("$name", false));
	}
	
	@SafeVarargs
	private final void testValidExpression(String expression, boolean staticOnly, Pair<String, Boolean>... fs) {
		Template expectedTemplate = buildTemplate(fs);
		Template template = Template.parse(expression);
		
		List<TemplateFragment> fragments = template.fragments();
		List<TemplateFragment> expectedFragments = expectedTemplate.fragments();
		
		Assertions.assertThat(fragments.size()).isEqualTo(expectedTemplate.fragments().size());
		
		boolean fail = false;
		
		for (int i = 0; i < fragments.size(); i++) {
			TemplateFragment fragment = fragments.get(i);
			TemplateFragment expectedFragment = expectedFragments.get(i);
			
			if (fragment.isPlaceholder() != expectedFragment.isPlaceholder()) {
				fail = true;
				break;
			}
			
			if (!fragment.getText().equals(expectedFragment.getText())) {
				fail = true;
				break;
			}
		}
		
		if (fail) {
			Assertions.fail("Expression was not parsed as expected: " + expression);
		}
		
		if (template.isStaticOnly() != staticOnly)
			Assertions.fail("Template was not conform to the expected isStaticOnly status: " + expression);
	}

	private void testInvalidExpression(String expression) {
		try {
			Template.parse(expression);
			Assertions.fail("Expression should have thrown an IllegalArgumentException: " + expression);
		}
		catch (IllegalArgumentException e) {
			// noop
		}
	}
	
	private Template buildTemplate(Pair<String, Boolean>... fs) {
		List<TemplateFragment> fragments = new ArrayList<>();
		
		for (Pair<String, Boolean> f: fs) {
			fragments.add(new TemplateFragment() {
				@Override
				public boolean isPlaceholder() {
					return f.getSecond();
				}
				@Override
				public String getText() {
					return f.getFirst();
				}
			});
		}
		
		return new Template() {
			
			@Override
			public List<TemplateFragment> fragments() {
				return fragments;
			}
			
			@Override
			public String expression() {
				return "<n/a>";
			}
		};
	}
}
