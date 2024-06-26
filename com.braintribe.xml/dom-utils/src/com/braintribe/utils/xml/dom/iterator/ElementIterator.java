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
/**
 * 
 */
package com.braintribe.utils.xml.dom.iterator;

import java.util.Iterator;

import org.w3c.dom.Element;

import com.braintribe.utils.xml.dom.DomUtils;

/**
 * a very handy iterator class for navigating in a dom structure
 * 
 * @author Pit
 * 
 */
public class ElementIterator implements Iterator<Element> {

	private Element element;
	private final String tagName;

	public ElementIterator(final Element parent, final String tagName) {
		this.tagName = tagName;
		this.element = DomUtils.getFirstElement(parent, tagName);
	}

	@Override
	public boolean hasNext() {
		return this.element != null;
	}

	@Override
	public Element next() {
		final Element retVal = this.element;
		this.element = DomUtils.getNextElement(this.element, this.tagName);
		return retVal;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
