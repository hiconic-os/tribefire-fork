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

/**
 * Implementation of {@link java.lang.Iterable} using FilterElementIterator. Enables usage of java
 * "for (Element e: iterable )" construction
 */
public class FilteringElementIterable implements Iterable<Element> {

	private final Element parent;
	private final Predicate<Element> filter;

	public FilteringElementIterable(final Element parent, final Predicate<Element> filter) {
		super();
		this.parent = parent;
		this.filter = filter;
	}

	// removed because of Java5 issue: @Override
	@Override
	public Iterator<Element> iterator() {
		return new FilteringElementIterator(this.parent, this.filter);
	}

}
