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
package com.braintribe.utils.xml.dom.wrapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.braintribe.utils.xml.dom.DomUtils;
import com.braintribe.utils.xml.dom.iterator.FilteringElementIterable;
import com.braintribe.utils.xml.dom.iterator.filters.RegExpTagNameFilter;
import com.braintribe.utils.xml.dom.iterator.filters.TagNameFilter;

/**
 * Wrapper for @link org.w3c.dom.Element. Provides more friendly interface for handling XmlTag.
 */
public class ElementWrapper {

	Element element;

	public ElementWrapper(final Element element) {
		super();
		this.element = element;
	}

	public Element getElement() {
		return this.element;
	}

	public Iterable<Element> getChildrenByName(final String childName) {
		return new FilteringElementIterable(this.element, new TagNameFilter(childName));
	}

	public Iterable<Element> getChildrenByRegExName(final String childName) {
		return new FilteringElementIterable(this.element, new RegExpTagNameFilter(childName));
	}

	public Element getChildByName(final String childName) {
		return DomUtils.getElementByPath(this.element, childName, false);
	}

	public String getAttributeOrNull(final String attributeName) {
		return hasAttribute(attributeName) ? getAttribute(attributeName) : null;
	}

	public String getAttribute(final String attributeName) {
		return this.element.getAttribute(attributeName);
	}

	public boolean hasAttribute(final String attributeName) {
		return this.element.hasAttribute(attributeName);
	}

	public List<Attr> listAllAttributes() {
		return listMatchingAttributes(".*");
	}

	public List<Attr> listMatchingAttributes(final String regex) {
		final List<Attr> result = new LinkedList<Attr>();

		final NamedNodeMap nodeMap = this.element.getAttributes();
		for (int i = 0; i < nodeMap.getLength(); i++) {
			final Node node = nodeMap.item(i);
			if (node instanceof Attr && node.getNodeName().matches(regex)) {
				result.add((Attr) node);
			}
		}

		return result;
	}

	public ElementWrapper appendChildren(final Collection<Element> children) {
		for (final Element child : children) {
			appendChild(child);
		}

		return this;
	}

	public void appendChild(final Element child) {
		this.element.appendChild(child);
	}
}
