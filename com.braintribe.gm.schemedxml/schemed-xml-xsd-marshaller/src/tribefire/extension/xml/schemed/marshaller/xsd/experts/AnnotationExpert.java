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
import tribefire.extension.xml.schemed.model.xsd.AppInfo;
import tribefire.extension.xml.schemed.model.xsd.Documentation;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class AnnotationExpert extends AbstractSchemaExpert {

	public static Annotation read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		Annotation annotation = Annotation.T.create();
		attach(annotation, declaringSchema);
		// wind to next event
		Map<QName, String> attributes = readAttributes(reader);
		reader.next();
		
		annotation.setId( attributes.get( new QName(ID)));
		
		readAnyAttributes( annotation.getAnyAttributes(), attributes, ID);
		
		attach(annotation, declaringSchema);
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :
												
					String tag = reader.getName().getLocalPart(); 
				
					switch ( tag) {
						case APP_INFO:
							AppInfo appinfo = AppInfoExpert.read( declaringSchema, reader);
							annotation.getNamedItemsSequence().add(appinfo);
							annotation.getAppInfo().add(appinfo);
							break;
						case DOCUMENTATION:
							Documentation documentation = DocumentationExpert.read( declaringSchema, reader);
							annotation.getNamedItemsSequence().add( documentation);
							annotation.getDocumentation().add(documentation);
							break;
						default:
							skip(reader);
					}				
				
				break;
				case XMLStreamConstants.END_ELEMENT : {
					return annotation;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
		return annotation;
	}
	
	public static void write( XMLStreamWriter writer, Namespace namespace, Annotation annotation) throws XMLStreamException { 
		if (annotation == null)
			return;
		String prefix = namespace.getPrefix();		
		writer.writeStartElement( prefix != null ? prefix + ":" + ANNOTATION : ANNOTATION);
	
		writeAnyAttributes(writer, annotation.getAnyAttributes());
		
		for (GenericEntity ge : annotation.getNamedItemsSequence()) {
			if (ge instanceof AppInfo) {
				AppInfoExpert.write(writer, namespace, (AppInfo) ge);				
			}
			else if (ge instanceof Documentation) {
				DocumentationExpert.write( writer, namespace, (Documentation) ge);				
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}
		writer.writeEndElement();
	}
}
