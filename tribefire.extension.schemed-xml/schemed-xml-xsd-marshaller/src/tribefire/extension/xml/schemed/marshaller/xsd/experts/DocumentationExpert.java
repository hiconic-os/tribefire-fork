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
package tribefire.extension.xml.schemed.marshaller.xsd.experts;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import tribefire.extension.xml.schemed.model.xsd.Documentation;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class DocumentationExpert extends AbstractSchemaExpert {
	
	public static Documentation read( Schema declaratingSchema, XMLStreamReader reader) throws XMLStreamException {
		StringBuffer buffer = new StringBuffer();

		Documentation documentation = Documentation.T.create();
		attach(documentation, declaratingSchema);
		
		Map<QName, String> attributes = readAttributes(reader);
		documentation.setSource( attributes.get(new QName(SOURCE)));
		documentation.setLang( attributes.get(new QName( LANGUAGE)));				
		
		// wind to next event
		reader.next();
		int i = 0;
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
		
				case XMLStreamConstants.START_ELEMENT :
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						default:
							skip(reader);
							break;					
					}
					i++;
					break;
				case XMLStreamConstants.CHARACTERS: {
					if (i == 1) {
						buffer.append( reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
					}
					break;
				}
				case XMLStreamConstants.END_ELEMENT : {
					
					documentation.setDocumentation( buffer.toString());
					return documentation;										
				}				
				default:
					break;
			}			
			reader.next();
		}
		return documentation;
	}
	
	public static void write( XMLStreamWriter writer, Namespace namespace, Documentation documentation) throws XMLStreamException { 
		if (documentation == null)
			return;
		String prefix = namespace.getPrefix();		
		writer.writeStartElement( prefix != null ? prefix + ":" + DOCUMENTATION : DOCUMENTATION);
		
		String source = documentation.getSource();
		if (source != null)
			writer.writeAttribute( SOURCE, source);
		String lang = documentation.getLang();
		if (lang != null)
			writer.writeAttribute( LANGUAGE, lang);
		
		writer.writeCharacters( documentation.getDocumentation());
		
		writer.writeEndElement();
	}
	
	
}
