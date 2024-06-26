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

/**
 * a filter that uses the tag name of an element as filter criterion
 * 
 * @author pit
 * 
 */
public class TagNameFilter implements Predicate<Element> {

	private String tag = null;

	public TagNameFilter(final String name) {
		this.tag = name;
	}

	@Override
	public boolean test(final Element obj) {
		final String suspect = obj.getTagName();
		return suspect.equalsIgnoreCase(this.tag);
	}

}
