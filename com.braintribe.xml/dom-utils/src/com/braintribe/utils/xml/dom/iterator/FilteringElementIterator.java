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
package com.braintribe.utils.xml.dom.iterator;

import java.util.Iterator;
import java.util.function.Predicate;

import org.w3c.dom.Element;

import com.braintribe.utils.xml.dom.DomUtils;

/**
 * 
 * an implementation of an iterator that uses the FilterApi to implement a filtering while iterating.
 * 
 * @author pit
 * 
 */
public class FilteringElementIterator implements Iterator<Element> {

	private Element element = null;
	private Predicate<Element> filter = null;

	public FilteringElementIterator(final Element parent, final Predicate<Element> filter) {
		this.filter = filter;
		this.element = DomUtils.getFirstElement(parent);
		if (this.element == null) {
			return;
		}
		do {
			if (filter.test(this.element)) {
				return;
			} else {
				this.element = DomUtils.getNextElement(this.element);
			}
		} while (this.element != null);
	}

	@Override
	public Element next() {
		final Element retval = this.element;
		do {
			this.element = DomUtils.getNextElement(this.element);
		} while ((this.element != null) && (!this.filter.test(this.element)));
		return retval;
	}

	@Override
	public boolean hasNext() {
		return this.element != null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
