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
package tribefire.extension.xml.schemed.marshaller.xsd.experts.restrictions;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.StringEscapeUtils;

import tribefire.extension.xml.schemed.marshaller.xsd.experts.AbstractSchemaExpert;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.restrictions.Pattern;

public class PatternExpert extends AbstractSchemaExpert {
	public static Pattern read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		
		Pattern pattern = Pattern.T.create();
		attach( pattern, declaringSchema);
		
		Map<QName,String> attributes = readAttributes(reader);		
		pattern.setValue( attributes.get( new QName( VALUE)));		
		reader.next();
		
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						default:
						skip(reader);									
					}										
					break;				
				}
				case XMLStreamConstants.END_ELEMENT : {
					return pattern;
				}
				default: 
					break;
				}
			reader.next();
		}
		return pattern;
	}
	
	public static void write( XMLStreamWriter writer, Namespace namespace, Pattern pattern) throws XMLStreamException {
		if (pattern == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + PATTERN : PATTERN);
		writer.writeAttribute( VALUE, "" + StringEscapeUtils.escapeXml(pattern.getValue()));
		writer.writeEndElement();
	}
}
