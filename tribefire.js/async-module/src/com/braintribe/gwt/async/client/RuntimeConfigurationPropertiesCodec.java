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
package com.braintribe.gwt.async.client;

import java.util.HashMap;
import java.util.Map;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.codec.dom.client.DomCodecUtil;
import com.braintribe.gwt.codec.dom.client.ElementIterator;
import com.braintribe.gwt.logging.client.Logger;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

/**
 * Codec responsible for decoding the XML into a Map with the properties from the
 * runtime configuration file.
 * @author michel.docouto
 *
 */
public class RuntimeConfigurationPropertiesCodec implements Codec<Map<String, String>, String> {
	private Logger logger = new Logger(RuntimeConfigurationPropertiesCodec.class);
	@Override
	public String encode(Map<String, String> value) throws CodecException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Map<String, String> decode(String xml)
			throws CodecException {
		try {
			Map<String, String> properties = new HashMap<String, String>();
			// sanity check
			if (xml.trim().startsWith("<?xml")) {
				Document doc = XMLParser.parse(xml);
				ElementIterator it = new ElementIterator(doc.getDocumentElement(), "entry");
				for (Element element: it) {
					String key = element.getAttribute("key");
					String value = DomCodecUtil.getFirstTextAsString(element, "");
					properties.put(key, value);
				}
			}
			else {
				String prologCandidate = xml.trim();
				String prolog = prologCandidate.substring(0, Math.min(prologCandidate.length(), 55));
				logger.warn("runtime configuration starts with invalid " + prolog + " -> assuming configuration file is not existing");
			}
			return properties;
		}
		catch (Exception e) {
			throw new CodecException("error while parsing runtimeConfiguration.xml", e);
		}
		
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public Class<Map<String, String>> getValueClass() {
		return (Class) Map.class;
	}

}
