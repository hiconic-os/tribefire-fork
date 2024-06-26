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

import tribefire.extension.xml.schemed.marshaller.xsd.HasSchemaTokens;
import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Import;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class ImportExpert extends AbstractSchemaExpert implements HasSchemaTokens {

	public static Import read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		Import importXsd = Import.T.create();
		attach( importXsd, declaringSchema);
		Map<QName, String> attributes = readAttributes( reader);
		importXsd.setSchemaLocation( attributes.get( new QName(SCHEMA_LOCATION)));
		importXsd.setId( attributes.get( new QName(ID)));
		importXsd.setNamespace( attributes.get( new QName(NAMESPACE)));
		
		readAnyAttributes( importXsd.getAnyAttributes(), attributes, ID, SCHEMA_LOCATION, NAMESPACE);
						
		reader.next();
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
					case ANNOTATION:
						Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
						importXsd.setAnnotation(annotation);					
						break;
					default:
							skip(reader);
					}
				}
				break;
				case XMLStreamConstants.END_ELEMENT : {
					return importXsd;
				}
				default: 
					break;
				}
			reader.next();
		}
		return importXsd;
	}

	public static void write( XMLStreamWriter writer, Namespace namespace, Import suspect) throws XMLStreamException {
		if (suspect == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + IMPORT : IMPORT);
		writer.writeAttribute( SCHEMA_LOCATION, suspect.getSchemaLocation());
		writer.writeAttribute( NAMESPACE, suspect.getNamespace());
		writeAnyAttributes(writer, suspect.getAnyAttributes());
		AnnotationExpert.write(writer, namespace, suspect.getAnnotation());
		writer.writeEndElement();
	}
}
