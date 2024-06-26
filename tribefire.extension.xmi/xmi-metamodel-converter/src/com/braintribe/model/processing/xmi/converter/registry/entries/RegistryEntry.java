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
package com.braintribe.model.processing.xmi.converter.registry.entries;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * the base for all other entries in this package
 * @author pit
 *
 */
public class RegistryEntry {
	private String xmiId;
	private Element element;
	private Document document;

	public String getXmiId() {
		return xmiId;
	}

	public void setXmiId(String xmiId) {
		this.xmiId = xmiId;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public boolean isEqual(Object obj) {
		if (obj instanceof RegistryEntry == false)
			return super.equals(obj);
		RegistryEntry entry = (RegistryEntry) obj;
		return (entry.getClass().getName().equalsIgnoreCase(getClass().getName()));
	}

}
