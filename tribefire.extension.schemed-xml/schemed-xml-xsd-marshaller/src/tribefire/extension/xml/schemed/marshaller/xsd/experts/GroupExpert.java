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

import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.All;
import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Choice;
import tribefire.extension.xml.schemed.model.xsd.Group;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.Sequence;

public class GroupExpert extends AbstractSchemaExpert {

	public static Group read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		
		Group group = Group.T.create();
		attach( group, declaringSchema);
		
		Map<QName, String> attributes = readAttributes(reader);
		
		String name = attributes.get( new QName( NAME));
		if (name != null) {
			group.setName( name);
		}
		else {
			String groupReference = attributes.get( new QName( REF));
			group.setRef(  QNameExpert.parse( groupReference));
		}
	
		String value = attributes.get( new QName(MIN_OCCURS));
		if (value != null)
			group.setMinOccurs(Integer.valueOf(value));
	
		value = attributes.get( new QName(MAX_OCCURS));
		if (value != null) {
			if (value.equalsIgnoreCase( UNBOUNDED)) {
				group.setMaxOccurs(-1);
			}
			else {
			group.setMaxOccurs(Integer.valueOf(value));
			}
		}
		
		readAnyAttributes( group.getAnyAttributes(), attributes, ID, NAME, REF, MIN_OCCURS, MAX_OCCURS);
		reader.next();
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :												
					String tag = reader.getName().getLocalPart();
					switch (tag) {					
						case ALL:
							All all = AllExpert.read( declaringSchema, reader);
							group.setAll( all);
							group.getNamedItemsSequence().add( all);
							break;						
						case CHOICE:							
							Choice choice = ChoiceExpert.read( declaringSchema, reader);
							group.setChoice( choice);
							group.getNamedItemsSequence().add( choice);
							break;
						case SEQUENCE:
							
							Sequence sequence = SequenceExpert.read( declaringSchema, reader);
							// no specialized reader, must attach declaring schema here expressively
							attach( sequence, declaringSchema);
							group.setSequence( sequence);
							group.getNamedItemsSequence().add( sequence);
							break;	
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
							group.setAnnotation(annotation);
							group.getNamedItemsSequence().add( annotation);
							break;
						default:
								skip(reader);
							break;
					}				
				break;
				
				case XMLStreamConstants.END_ELEMENT : {
					return group;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
		return group;
	}

	
	public static void write(XMLStreamWriter writer, Namespace namespace, Group group) throws XMLStreamException{
		if (group == null)
			return;
		
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + GROUP : GROUP);
		// write attributes
		int min = group.getMinOccurs();
		if (min != 1) {
			writer.writeAttribute( MIN_OCCURS, "" + min);
		}
		int max = group.getMaxOccurs();
		if (max < 0) {
			writer.writeAttribute( MAX_OCCURS, UNBOUNDED);
		}
		else if (max != 1) {
			writer.writeAttribute( MAX_OCCURS, "" + max);
		}
		
		writeAnyAttributes(writer, group.getAnyAttributes());
		
		for (GenericEntity ge : group.getNamedItemsSequence()) {
			if (ge instanceof All) {
				AllExpert.write(writer, namespace, (All) ge);				
			}
			else if (ge instanceof Choice) {
				ChoiceExpert.write(writer, namespace, (Choice) ge);				
			}
			else if (ge instanceof Sequence) {
				SequenceExpert.write(writer, namespace, (Sequence) ge);				
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
