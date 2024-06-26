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
package com.braintribe.gwt.browserfeatures.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

/**
 * This Util class is used for providing access to the meta tags for gwt:property
 * Example: <meta name="gwt:property" content="locale=de">
 * @author michel.docouto
 *
 */
public class GWTMetaPropertiesUtil {
	private static final String ICON_SET_PROPERTY = "iconSet";
	private static final String COLOURED_ICON_SET = "coloured";
	
	public enum IconSet {
		coloured, bw;
	}
	
	private static Map<String, String> metaProperties = null;
	
	/**
	 * Returns all the properties defined  in gwt:property meta tags.
	 */
	public static Map<String, String> getMetaProperties() {
		if (metaProperties == null) {
			metaProperties = new HashMap<String, String>();

			NodeList<Element> metaElements = Document.get().getElementsByTagName("meta");
			
			for (int i = 0; i < metaElements.getLength(); i++) {
				Element metaElement = metaElements.getItem(i);
				if ("gwt:property".equals(metaElement.getAttribute("name"))) {
					String content = metaElement.getAttribute("content");
					int index = content.indexOf("=");
					String key = content;
					String value = "";
					
					if (index != -1) {
						key = content.substring(0, index); 
						value = content.substring(index + 1); 
					}
					
					metaProperties.put(key, value);
				}
			}
		}

		return metaProperties;
	}
	
	public static String getMetaPropertyValue(String propertyName, String defaultValue) {
		Map<String, String> properties = getMetaProperties();
		String value = properties.get(propertyName);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * Returns the IconSet currently configured using the iconSet gwt:property
	 */
	public static IconSet getIconSet() {
		return COLOURED_ICON_SET.equals(getMetaProperties().get(ICON_SET_PROPERTY)) ? IconSet.coloured : IconSet.bw;
	}

}
