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
package com.braintribe.gwt.codec.dom.client;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public class ElementIterator extends NodeIterator<Element> {

	private static class ElementFilter implements Predicate<Node> {
		private Predicate<Element> specificFilter;
		
		public ElementFilter(Predicate<Element> specificFilter) {
			super();
			this.specificFilter = specificFilter;
		}

		@Override
		public boolean test(Node node) {
			if (node instanceof Element) {
				Element element = (Element)node;
				return specificFilter == null || specificFilter.test(element);
			}
			else return false;
		}
	}
	
	public ElementIterator(Element parent) {
		this(parent, (Predicate<Element>)null);
	}
	
	public ElementIterator(Element parent, final String... varArgTagNames) {
		this(parent, new Predicate<Element>() {
			private final List<String> tagNames = Arrays.asList(varArgTagNames);
			@Override
			public boolean test(Element element) {
				return tagNames.contains(element.getTagName());
			}
		});
	}
	
	public ElementIterator(Element parent, Predicate<Element> filter) {
		super(parent, new ElementFilter(filter));
	}
}
