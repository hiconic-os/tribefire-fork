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
package com.braintribe.artifact.declared.marshaller.experts;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.braintribe.artifact.declared.marshaller.PomReadContext;
import com.braintribe.common.lcd.Pair;

public class PropertiesExpert extends AbstractPomExpert implements HasPomTokens {
	
	public static Map<String,String> read(PomReadContext context, XMLStreamReader reader) throws XMLStreamException {
		Map<String,String> properties = new HashMap<>();
		reader.next();
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						default:
							Pair<String,String> pair = PropertyExpert.read(context, tag, reader);
							properties.put( pair.first(), pair.second());
							break;
					}
					break;
				}
				case XMLStreamConstants.END_ELEMENT : {
					return properties;
				}
				default:
					break;
			}
			reader.next();
		}
		return null;
	}
}
