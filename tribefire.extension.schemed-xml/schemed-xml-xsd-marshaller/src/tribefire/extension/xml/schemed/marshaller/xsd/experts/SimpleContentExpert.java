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

import com.braintribe.model.generic.GenericEntity;

import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Extension;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SimpleContent;
import tribefire.extension.xml.schemed.model.xsd.SimpleContentRestriction;

public class SimpleContentExpert extends AbstractSchemaExpert {
	
	public static SimpleContent read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		SimpleContent simpleContent = SimpleContent.T.create();
		attach(simpleContent, declaringSchema);
		Map<QName, String> attributes = readAttributes( reader);
		
		simpleContent.setId( attributes.get( new QName(ID)));
		readAnyAttributes( simpleContent.getAnyAttributes(), attributes, ID);
						
		reader.next();
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case RESTRICTION:
							SimpleContentRestriction contentRestriction = SimpleContentRestrictionExpert.read(declaringSchema, reader);
							simpleContent.setRestriction( contentRestriction);
							simpleContent.getNamedItemsSequence().add( contentRestriction);
							break;							
						case EXTENSION:
							Extension contentExtension = ExtensionExpert.read(declaringSchema, reader);
							simpleContent.setExtension( contentExtension);
							simpleContent.getNamedItemsSequence().add( contentExtension);
							break;
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read(declaringSchema,reader);
							simpleContent.setAnnotation(annotation);
							simpleContent.getNamedItemsSequence().add(annotation);
						break;
						default:
							skip(reader);
					}
				}
				break;
				case XMLStreamConstants.END_ELEMENT : {
					return simpleContent;
				}
				default: 
					break;
				}
			reader.next();
		}
		return simpleContent;
	}

	public static void write( XMLStreamWriter writer, Namespace namespace, SimpleContent suspect) throws XMLStreamException {
		if (suspect == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + SIMPLE_CONTENT : SIMPLE_CONTENT);
		
		for (GenericEntity ge : suspect.getNamedItemsSequence()) {
			if (ge instanceof SimpleContentRestriction) {
				SimpleContentRestrictionExpert.write(writer, namespace, (SimpleContentRestriction) ge);				
			}
			else if (ge instanceof Extension) {
				ExtensionExpert.write(writer, namespace, (Extension) ge);				
			}
			else if (ge instanceof Annotation) {
				AnnotationExpert.write(writer, namespace, (Annotation) ge);
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}
		
		writer.writeEndElement();
	}
}
