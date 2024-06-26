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

import java.util.function.Predicate;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;

public class TextIterator extends NodeIterator<Text> {

	private static class ElementFilter implements Predicate<Node> {
		private Predicate<Text> specificFilter;
		
		public ElementFilter(Predicate<Text> specificFilter) {
			super();
			this.specificFilter = specificFilter;
		}

		@Override
		public boolean test(Node node) {
			if (node instanceof Text) {
				Text text = (Text)node;
				return specificFilter == null || specificFilter.test(text);
			}
			else return false;
		}
	}
	
	public TextIterator(Element parent) {
		this(parent, (Predicate<Text>)null);
	}
	
	public TextIterator(Element parent, Predicate<Text> filter) {
		super(parent, new ElementFilter(filter));
	}
}
