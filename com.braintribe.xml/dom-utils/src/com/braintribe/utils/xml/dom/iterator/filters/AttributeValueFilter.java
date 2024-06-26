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

import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class AttributeValueFilter implements Predicate<Element> {

	private String name = null;
	private String value = null;

	public AttributeValueFilter(final String attributeName, final String attributeValue) {
		this.name = attributeName;
		this.value = attributeValue;
	}

	@Override
	public boolean test(final Element obj) {
		final Attr attrNode = obj.getAttributeNode(this.name);
		if (attrNode == null) {
			return false;
		}
		if (attrNode.getNodeValue().matches(this.value)) {
			return true;
		}

		return false;
	}

}
