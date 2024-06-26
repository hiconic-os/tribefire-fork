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
package com.braintribe.utils.xml.dom.iterator.filters;

import java.util.function.Predicate;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * a filter that uses the name of an attribute as filter criterion the mask is a regular expression
 * 
 * @author pit
 * 
 */
public class RegExpAttributeNameFilter implements Predicate<Element> {

	private String regExp = null;

	public RegExpAttributeNameFilter(final String expr) {
		this.regExp = expr;
	}

	@Override
	public boolean test(final Element obj) {
		final NamedNodeMap attrnodes = obj.getAttributes();
		for (int i = 0; i < attrnodes.getLength(); i++) {
			final Node attrNode = attrnodes.item(i);
			if (attrNode.getTextContent().matches(this.regExp)) {
				return true;
			}
		}
		return false;
	}
}
